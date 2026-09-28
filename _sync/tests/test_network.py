import sys
from pathlib import Path

import pytest
import requests

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import AuthError, LeetCodeClient, NetworkError


class FlakyHTTP:
    """Fails with a connection error `failures` times, then succeeds."""

    def __init__(self, failures, status=200, payload=None, exc=None):
        self.failures = failures
        self.calls = 0
        self.status = status
        self.payload = payload if payload is not None else {"submissions_dump": [], "has_next": False}
        self.exc = exc or requests.exceptions.ConnectionError("dns go boom")
        self.headers = {}
        self.cookies = type("C", (), {"set": lambda *a, **k: None})()

    def get(self, url, **kwargs):
        self.calls += 1
        if self.calls <= self.failures:
            raise self.exc
        return self._response()

    post = get

    def _response(self):
        payload, status = self.payload, self.status

        class R:
            status_code = status
            def json(self): return payload
            def raise_for_status(self): pass
        return R()


def client_with(http, monkeypatch, max_retries=4):
    c = LeetCodeClient("session", "csrf", delay=0, max_retries=max_retries)
    c.http = http
    monkeypatch.setattr("leetcode_sync.api.time.sleep", lambda *_: None)
    return c


def test_transient_network_failure_is_retried(monkeypatch):
    http = FlakyHTTP(failures=2)
    c = client_with(http, monkeypatch)
    assert list(c.iter_submissions()) == []
    assert http.calls == 3  # two failures, then the successful call


def test_persistent_network_failure_raises_network_error(monkeypatch):
    http = FlakyHTTP(failures=99)
    c = client_with(http, monkeypatch, max_retries=3)
    with pytest.raises(NetworkError) as err:
        list(c.iter_submissions())
    assert "Nothing was synced" in str(err.value)
    assert http.calls == 3


def test_timeouts_are_treated_as_network_failures(monkeypatch):
    http = FlakyHTTP(failures=1, exc=requests.exceptions.Timeout("slow"))
    c = client_with(http, monkeypatch)
    assert list(c.iter_submissions()) == []
    assert http.calls == 2


def test_network_error_is_not_mistaken_for_a_bad_cookie(monkeypatch):
    c = client_with(FlakyHTTP(failures=99), monkeypatch, max_retries=2)
    with pytest.raises(NetworkError):
        list(c.iter_submissions())
    # An expired cookie must still surface as AuthError, not NetworkError.
    c2 = client_with(FlakyHTTP(failures=0, status=401), monkeypatch)
    with pytest.raises(AuthError):
        list(c2.iter_submissions())


def test_get_question_also_rides_out_a_blip(monkeypatch):
    payload = {"data": {"question": {
        "questionFrontendId": "1", "title": "Two Sum", "titleSlug": "two-sum",
        "difficulty": "Easy", "isPaidOnly": False,
        "topicTags": [{"name": "Array", "slug": "array"}]}}}
    http = FlakyHTTP(failures=2, payload=payload)
    c = client_with(http, monkeypatch)
    q = c.get_question("two-sum")
    assert q is not None and q.title == "Two Sum"
    assert http.calls == 3


def test_cli_reports_a_network_outage_without_a_traceback(monkeypatch, capsys):
    from leetcode_sync import cli
    monkeypatch.setattr(cli, "Credentials", type("C", (), {
        "load": staticmethod(lambda: type("X", (), {"session": "s", "csrf_token": "c"})())}))
    monkeypatch.setattr(cli, "LeetCodeClient", lambda **kw: object())
    monkeypatch.setattr(cli, "SyncState", type("S", (), {
        "load": staticmethod(lambda: type("St", (), {"newest_submission_id": 1})())}))

    def boom(*a, **k):
        raise NetworkError("no dns. Nothing was synced")
    monkeypatch.setattr(cli, "run_sync", boom)

    assert cli.main([]) == 4
    err = capsys.readouterr().err
    assert "network unavailable" in err
    assert "Traceback" not in err
