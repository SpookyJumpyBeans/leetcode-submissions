import sys
from pathlib import Path

import pytest
import requests

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import AuthError, LeetCodeClient, ThrottledError


_DEFAULT = object()


class FakeResponse:
    def __init__(self, status_code, payload=_DEFAULT):
        self.status_code = status_code
        # payload=None specifically means "body is not JSON" (the login page).
        self._payload = {"submissions_dump": []} if payload is _DEFAULT else payload

    def json(self):
        if self._payload is None:
            raise ValueError("not json")
        return self._payload

    def raise_for_status(self):
        if self.status_code >= 400:
            raise requests.HTTPError(str(self.status_code))


def make_client(responses, monkeypatch):
    client = LeetCodeClient("session", "csrf", delay=0, max_retries=3)
    monkeypatch.setattr("leetcode_sync.api._backoff", lambda attempt: None)
    calls = iter(responses)
    monkeypatch.setattr(client.http, "get", lambda *a, **k: next(calls))
    return client


def test_403_before_any_success_is_an_auth_error(monkeypatch):
    client = make_client([FakeResponse(403)], monkeypatch)
    with pytest.raises(AuthError):
        list(client.iter_submissions())


def test_403_after_a_good_page_is_treated_as_throttling(monkeypatch):
    good = {"submissions_dump": [
        {"id": "1", "url": "/problems/two-sum/submissions/1/", "status_display": "Accepted",
         "timestamp": "100", "lang": "python3", "code": "pass"}],
        "has_next": True, "last_key": "k"}
    client = make_client(
        [FakeResponse(200, good)] + [FakeResponse(403) for _ in range(3)], monkeypatch
    )
    with pytest.raises(ThrottledError):
        list(client.iter_submissions())


def test_html_body_is_reported_as_a_bad_cookie(monkeypatch):
    client = make_client([FakeResponse(200, None)], monkeypatch)
    with pytest.raises(AuthError):
        list(client.iter_submissions())
