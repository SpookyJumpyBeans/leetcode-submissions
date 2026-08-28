"""Persisted sync state and the problem-metadata cache."""

from __future__ import annotations

import json
from dataclasses import dataclass, field
from pathlib import Path

from .api import Question
from .config import CACHE_PATH, STATE_PATH


@dataclass
class SyncState:
    """What we already pulled, so the next run only fetches new submissions."""

    newest_submission_id: int | None = None
    newest_timestamp: int | None = None
    last_run: str | None = None
    problems_synced: int = 0
    # Where a partial backfill stopped, so the next --full run resumes there.
    backfill_offset: int | None = None
    backfill_last_key: str | None = None
    backfill_complete: bool = False
    path: Path = field(default=STATE_PATH, compare=False)

    @classmethod
    def load(cls, path: Path = STATE_PATH) -> "SyncState":
        if not path.exists():
            return cls(path=path)
        data = json.loads(path.read_text(encoding="utf-8"))
        return cls(
            newest_submission_id=data.get("newest_submission_id"),
            newest_timestamp=data.get("newest_timestamp"),
            last_run=data.get("last_run"),
            problems_synced=data.get("problems_synced", 0),
            backfill_offset=data.get("backfill_offset"),
            backfill_last_key=data.get("backfill_last_key"),
            backfill_complete=data.get("backfill_complete", False),
            path=path,
        )

    def save(self) -> None:
        self.path.parent.mkdir(parents=True, exist_ok=True)
        self.path.write_text(
            json.dumps(
                {
                    "newest_submission_id": self.newest_submission_id,
                    "newest_timestamp": self.newest_timestamp,
                    "last_run": self.last_run,
                    "problems_synced": self.problems_synced,
                    "backfill_offset": self.backfill_offset,
                    "backfill_last_key": self.backfill_last_key,
                    "backfill_complete": self.backfill_complete,
                },
                indent=2,
            )
            + "\n",
            encoding="utf-8",
            newline="\n",
        )


class ProblemCache:
    """Problem metadata barely changes, so fetch each slug at most once."""

    def __init__(self, path: Path = CACHE_PATH):
        self.path = path
        self._data: dict[str, Question] = {}
        if path.exists():
            raw = json.loads(path.read_text(encoding="utf-8"))
            self._data = {slug: Question.from_json(value) for slug, value in raw.items()}
        self._dirty = False

    def get(self, slug: str) -> Question | None:
        return self._data.get(slug)

    def put(self, question: Question) -> None:
        self._data[question.slug] = question
        self._dirty = True

    def save(self) -> None:
        if not self._dirty:
            return
        self.path.parent.mkdir(parents=True, exist_ok=True)
        payload = {slug: question.to_json() for slug, question in sorted(self._data.items())}
        self.path.write_text(
            json.dumps(payload, indent=2, sort_keys=True) + "\n",
            encoding="utf-8",
            newline="\n",
        )
        self._dirty = False


class SolutionIndex:
    """Everything currently in the repo, so the READMEs can be regenerated.

    Incremental runs only see new submissions, so the full picture has to be
    remembered somewhere; this is that memory.
    """

    def __init__(self, path: Path):
        self.path = path
        self._questions: dict[str, Question] = {}
        self._submissions: dict[str, dict[str, dict]] = {}  # slug -> lang -> record
        if path.exists():
            raw = json.loads(path.read_text(encoding="utf-8"))
            for slug, entry in raw.items():
                self._questions[slug] = Question.from_json(entry["question"])
                self._submissions[slug] = {
                    record["lang"]: record for record in entry.get("submissions", [])
                }

    def record(self, question: Question, submission) -> None:
        self._questions[question.slug] = question
        self._submissions.setdefault(question.slug, {})[submission.lang] = {
            "id": submission.id,
            "lang": submission.lang,
            "lang_name": submission.lang_name,
            "runtime": submission.runtime,
            "memory": submission.memory,
            "timestamp": submission.timestamp,
            "title": submission.title,
            "slug": submission.slug,
            "status": submission.status,
        }

    def has_newer(self, slug: str, lang: str, timestamp: int) -> bool:
        """True if we already stored an equal-or-newer solution for this pair.

        A resumed backfill walks backwards in time, so without this an older
        accepted submission would overwrite the newer one already on disk.
        """
        record = self._submissions.get(slug, {}).get(lang)
        return record is not None and record.get("timestamp", 0) >= timestamp

    def entries(self) -> list[tuple[Question, list]]:
        from .api import Submission

        result = []
        for slug, question in self._questions.items():
            submissions = [
                Submission(
                    id=r["id"], title=r["title"], slug=r["slug"], lang=r["lang"],
                    lang_name=r.get("lang_name", ""), status=r.get("status", "Accepted"),
                    timestamp=r["timestamp"], runtime=r.get("runtime", ""),
                    memory=r.get("memory", ""), code="",
                )
                for r in self._submissions.get(slug, {}).values()
            ]
            if submissions:
                result.append((question, submissions))
        return result

    def __len__(self) -> int:
        return len(self._questions)

    def save(self) -> None:
        self.path.parent.mkdir(parents=True, exist_ok=True)
        payload = {
            slug: {
                "question": question.to_json(),
                "submissions": sorted(
                    self._submissions.get(slug, {}).values(), key=lambda r: r["lang"]
                ),
            }
            for slug, question in sorted(self._questions.items())
        }
        self.path.write_text(
            json.dumps(payload, indent=2, sort_keys=True) + "\n",
            encoding="utf-8",
            newline="\n",
        )
