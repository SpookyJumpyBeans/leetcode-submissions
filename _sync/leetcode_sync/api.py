"""Thin client over the LeetCode endpoints we need.

Two endpoints do all the work:

* ``GET /api/submissions/`` - paginated submission history, newest first, and
  crucially it includes the full source code of each submission.
* ``POST /graphql/`` - problem metadata (frontend number, difficulty, topic tags).
"""

from __future__ import annotations

import time
from dataclasses import dataclass
from typing import Any, Iterator

import requests

from .config import topic_rank

BASE_URL = "https://leetcode.com"
SUBMISSIONS_URL = f"{BASE_URL}/api/submissions/"
GRAPHQL_URL = f"{BASE_URL}/graphql/"
PAGE_SIZE = 20  # LeetCode caps this endpoint at 20 per page.

USER_AGENT = (
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 "
    "(KHTML, like Gecko) Chrome/122.0.0.0 Safari/537.36"
)

QUESTION_QUERY = """
query questionData($titleSlug: String!) {
  question(titleSlug: $titleSlug) {
    questionId
    questionFrontendId
    title
    titleSlug
    difficulty
    isPaidOnly
    topicTags { name slug }
  }
}
"""


class LeetCodeError(RuntimeError):
    pass


class AuthError(LeetCodeError):
    pass


class ThrottledError(LeetCodeError):
    """Rate limiting we could not wait out. Progress is still resumable."""

    pass


@dataclass(frozen=True)
class Submission:
    """One row of the submission history."""

    id: int
    title: str
    slug: str
    lang: str
    lang_name: str
    status: str
    timestamp: int
    runtime: str
    memory: str
    code: str
    source: str = "leetcode"

    @property
    def accepted(self) -> bool:
        return self.status == "Accepted"

    @classmethod
    def from_payload(cls, payload: dict[str, Any]) -> "Submission":
        slug = payload.get("title_slug") or _slug_from_url(payload.get("url", ""))
        return cls(
            id=int(payload["id"]),
            title=payload.get("title", slug),
            slug=slug,
            lang=payload.get("lang", ""),
            lang_name=payload.get("lang_name", ""),
            status=payload.get("status_display", ""),
            timestamp=int(payload.get("timestamp", 0)),
            runtime=payload.get("runtime", ""),
            memory=payload.get("memory", ""),
            code=payload.get("code", ""),
        )


def _slug_from_url(url: str) -> str:
    """``/problems/two-sum/submissions/1234/`` -> ``two-sum``."""
    parts = [p for p in url.split("/") if p]
    if "problems" in parts:
        index = parts.index("problems")
        if index + 1 < len(parts):
            return parts[index + 1]
    return ""


@dataclass(frozen=True)
class Question:
    frontend_id: str
    title: str
    slug: str
    difficulty: str
    paid_only: bool
    topics: tuple[tuple[str, str], ...]  # (name, slug) pairs

    @property
    def primary_topic(self) -> tuple[str, str]:
        """The most specific tag, not the first one LeetCode happens to list.

        Ties keep LeetCode's own ordering, so equally-ranked tags stay stable.
        """
        if not self.topics:
            return ("Miscellaneous", "miscellaneous")
        ranked = sorted(
            enumerate(self.topics),
            key=lambda pair: (topic_rank(pair[1][1]), pair[0]),
        )
        return ranked[0][1]

    def to_json(self) -> dict[str, Any]:
        return {
            "frontend_id": self.frontend_id,
            "title": self.title,
            "slug": self.slug,
            "difficulty": self.difficulty,
            "paid_only": self.paid_only,
            "topics": [list(t) for t in self.topics],
        }

    @classmethod
    def from_json(cls, data: dict[str, Any]) -> "Question":
        return cls(
            frontend_id=data["frontend_id"],
            title=data["title"],
            slug=data["slug"],
            difficulty=data["difficulty"],
            paid_only=data.get("paid_only", False),
            topics=tuple((t[0], t[1]) for t in data.get("topics", [])),
        )


class LeetCodeClient:
    def __init__(self, session_cookie: str, csrf_token: str = "", delay: float = 1.0,
                 max_retries: int = 6):
        self.delay = delay
        self.max_retries = max_retries
        self.http = requests.Session()
        self.http.cookies.set("LEETCODE_SESSION", session_cookie, domain="leetcode.com")
        if csrf_token:
            self.http.cookies.set("csrftoken", csrf_token, domain="leetcode.com")
        self._succeeded_once = False
        self.http.headers.update({
            "User-Agent": USER_AGENT,
            "Referer": BASE_URL,
            "Origin": BASE_URL,
            "Accept": "application/json",
        })
        if csrf_token:
            self.http.headers["x-csrftoken"] = csrf_token

    def _get(self, url: str, params: dict[str, Any]) -> dict[str, Any]:
        for attempt in range(self.max_retries):
            response = self.http.get(url, params=params, timeout=30)

            # 403 means one of two very different things. Before any request has
            # succeeded it is a bad cookie; after several good pages it is
            # LeetCode throttling us, and the fix is to wait, not to re-auth.
            if response.status_code in (429, 403) and self._succeeded_once:
                _backoff(attempt)
                continue
            if response.status_code == 429:
                _backoff(attempt)
                continue
            if response.status_code in (401, 403):
                raise AuthError(
                    "LeetCode rejected the request (HTTP %s). Your LEETCODE_SESSION "
                    "cookie is probably expired - grab a fresh one." % response.status_code
                )
            response.raise_for_status()
            try:
                payload = response.json()
            except ValueError as exc:
                # An HTML body here means we were bounced to the login page.
                raise AuthError(
                    "Expected JSON but got HTML - the session cookie is not valid."
                ) from exc
            self._succeeded_once = True
            return payload
        raise ThrottledError(
            f"LeetCode kept throttling us after {self.max_retries} attempts. "
            "Progress so far is saved - rerun to continue, ideally with a larger --delay."
        )

    def iter_submissions(self, stop_at_id: int | None = None,
                         stop_before_timestamp: int | None = None,
                         start_offset: int = 0, start_last_key: str | None = None,
                         on_page=None) -> Iterator[Submission]:
        """Yield submissions newest-first, stopping once we reach known ground.

        ``on_page(offset, last_key)`` is called after each page with the cursor
        for the *next* one, so a caller can checkpoint and resume a long walk.
        """
        offset = start_offset
        last_key: str | None = start_last_key
        while True:
            params: dict[str, Any] = {"offset": offset, "limit": PAGE_SIZE}
            if last_key:
                params["lastkey"] = last_key
            payload = self._get(SUBMISSIONS_URL, params)
            rows = payload.get("submissions_dump") or []
            if not rows:
                if on_page:
                    on_page(None, None)  # history exhausted
                return
            for row in rows:
                submission = Submission.from_payload(row)
                if stop_at_id is not None and submission.id == stop_at_id:
                    return
                if (stop_before_timestamp is not None
                        and submission.timestamp <= stop_before_timestamp):
                    return
                yield submission
            if not payload.get("has_next"):
                if on_page:
                    on_page(None, None)  # history exhausted
                return
            last_key = payload.get("last_key")
            offset += PAGE_SIZE
            if on_page:
                on_page(offset, last_key)
            time.sleep(self.delay)

    def get_question(self, slug: str) -> Question | None:
        for attempt in range(self.max_retries):
            response = self.http.post(
                GRAPHQL_URL,
                json={"query": QUESTION_QUERY, "variables": {"titleSlug": slug}},
                timeout=30,
            )
            if response.status_code == 429:
                _backoff(attempt)
                continue
            response.raise_for_status()
            data = (response.json() or {}).get("data") or {}
            question = data.get("question")
            if not question:
                return None
            return Question(
                frontend_id=str(question.get("questionFrontendId") or question.get("questionId") or "0"),
                title=question.get("title", slug),
                slug=question.get("titleSlug", slug),
                difficulty=question.get("difficulty", "Unknown"),
                paid_only=bool(question.get("isPaidOnly")),
                topics=tuple((t["name"], t["slug"]) for t in question.get("topicTags") or []),
            )
        return None


def _backoff(attempt: int) -> None:
    time.sleep(min(60.0, 5.0 * (2 ** attempt)))
