import sys
from pathlib import Path

import pytest

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import (
    AuthError, ChallengedError, LeetCodeClient, Submission, is_challenge,
)
from leetcode_sync.state import ProblemCache, SolutionIndex, SyncState
from leetcode_sync.sync import run_sync
from test_sync import QUESTIONS, FakeClient, sub

CHALLENGE_BODY = "<!DOCTYPE html><html><head><title>Just a moment...</title></head></html>"


def response(status=200, payload=None, headers=None, text=""):
    class R:
        status_code = status
        def json(self): return payload
        def raise_for_status(self): pass
    r = R()
    r.headers = headers or {}
    r.text = text
    return r


class ScriptedHTTP:
    """Hands back the given responses in order, one per request."""

    def __init__(self, *responses):
        self.responses = list(responses)
        self.requests = []
        self.headers = {}
        self.cookies = type("C", (), {"set": lambda *a, **k: None})()

    def get(self, url, **kwargs):
        self.requests.append(kwargs)
        return self.responses.pop(0)

    post = get


def client_with(http, monkeypatch):
    c = LeetCodeClient("session", "csrf", delay=0, max_retries=3)
    c.http = http
    monkeypatch.setattr("leetcode_sync.api.time.sleep", lambda *_: None)
    return c


def test_challenge_is_recognised_by_header_or_page():
    assert is_challenge(response(403, headers={"cf-mitigated": "challenge"}))
    assert is_challenge(response(403, text=CHALLENGE_BODY))
    assert not is_challenge(response(403, text="Forbidden"))
    assert not is_challenge(response(200, payload={}))


def test_a_challenged_history_request_is_not_called_an_auth_error(monkeypatch):
    http = ScriptedHTTP(response(403, headers={"cf-mitigated": "challenge"}, text=CHALLENGE_BODY))
    c = client_with(http, monkeypatch)
    with pytest.raises(ChallengedError) as err:
        list(c.iter_submissions())
    assert not isinstance(err.value, AuthError)
    assert "not a cookie problem" in str(err.value)


def test_a_plain_403_is_still_an_auth_error(monkeypatch):
    http = ScriptedHTTP(response(403, text="Forbidden"))
    c = client_with(http, monkeypatch)
    with pytest.raises(AuthError):
        list(c.iter_submissions())


def recent(*ids):
    return response(payload={"data": {"recentAcSubmissionList": [
        {"id": str(i), "title": f"P{i}", "titleSlug": f"p{i}", "timestamp": str(i), "lang": "java"}
        for i in ids]}})


def details(status=10, code="class Solution {}"):
    return response(payload={"data": {"submissionDetails": {
        "runtimeDisplay": "6 ms", "memoryDisplay": "46.8 MB", "timestamp": 0,
        "statusCode": status, "code": code,
        "lang": {"name": "java", "verboseName": "Java"}}}})


def test_recent_accepted_yields_only_newer_submissions_newest_first(monkeypatch):
    http = ScriptedHTTP(recent(100, 300, 200), details(code="b"), details(code="a"))
    c = client_with(http, monkeypatch)
    got = list(c.iter_recent_accepted("me", stop_at_id=100))
    assert [s.id for s in got] == [300, 200]
    assert [s.code for s in got] == ["b", "a"]
    assert got[0].lang_name == "Java" and got[0].runtime == "6 ms"
    assert got[0].accepted
    assert c.recent_window_full is False
    assert len(http.requests) == 3  # nothing fetched for the already-synced one


def test_recent_accepted_skips_a_submission_that_is_not_accepted(monkeypatch):
    http = ScriptedHTTP(recent(2, 1), details(status=11), details())
    c = client_with(http, monkeypatch)
    assert [s.id for s in c.iter_recent_accepted("me", stop_at_id=0)] == [1]


def test_recent_accepted_flags_a_window_with_nothing_already_synced(monkeypatch):
    http = ScriptedHTTP(recent(5, 4), details(), details())
    c = client_with(http, monkeypatch)
    list(c.iter_recent_accepted("me", stop_at_id=3))
    assert c.recent_window_full is True


def test_graphql_challenge_is_reported_as_such(monkeypatch):
    http = ScriptedHTTP(response(403, headers={"cf-mitigated": "challenge"}))
    c = client_with(http, monkeypatch)
    with pytest.raises(ChallengedError):
        list(c.iter_recent_accepted("me"))


class ChallengedClient(FakeClient):
    """History endpoint challenged; the recent-accepted list still answers."""

    def __init__(self, recent_subs, questions=None):
        super().__init__([], questions)
        self.recent_subs = recent_subs
        self.recent_calls = []
        self.recent_window_full = False

    def iter_submissions(self, **kwargs):
        raise ChallengedError("challenged")
        yield  # pragma: no cover

    def iter_recent_accepted(self, username, stop_at_id=None):
        self.recent_calls.append((username, stop_at_id))
        for s in self.recent_subs:
            if stop_at_id is None or s.id > stop_at_id:
                yield s


def stores(tmp_path):
    state = SyncState(path=tmp_path / "state.json")
    return state, ProblemCache(tmp_path / "cache.json"), SolutionIndex(tmp_path / "index.json")


def test_incremental_run_falls_back_to_the_recent_list(tmp_path):
    state, cache, index = stores(tmp_path)
    state.newest_submission_id = 10
    client = ChallengedClient([sub(12, "3sum", "java", 1200), sub(11, "two-sum", "java", 1100)])
    logs = []
    report = run_sync(client, state, repo_root=tmp_path, cache=cache, index=index, log=logs.append)
    assert client.recent_calls[0][1] == 10
    assert report.fetched == 2 and len(report.written) == 2
    assert state.newest_submission_id == 12
    assert any("GraphQL" in line for line in logs)


def test_full_run_does_not_fall_back(tmp_path):
    state, cache, index = stores(tmp_path)
    client = ChallengedClient([sub(1, "two-sum", "java", 100)])
    with pytest.raises(ChallengedError):
        run_sync(client, state, repo_root=tmp_path, full=True, cache=cache, index=index,
                 log=lambda *_: None)
    assert client.recent_calls == []
