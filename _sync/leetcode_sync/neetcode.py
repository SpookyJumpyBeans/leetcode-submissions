"""Import solutions from a NeetCode GitHub Sync repository.

NeetCode's judge is separate from LeetCode's, so problems solved there leave no
trace in the LeetCode submission history. This folds them into the same tree.

The wrinkle is naming: NeetCode kept its original slugs even after switching its
display names to LeetCode's, so `count-paths` is Unique Paths and `pow-x-n` is
`powx-n`. Most slugs match LeetCode's outright; the rest are listed below.
"""

from __future__ import annotations

import re
import subprocess
from dataclasses import dataclass
from pathlib import Path

from .api import Question, Submission
from .config import LANG_EXTENSIONS

# NeetCode slug -> LeetCode slug, for the ones that differ.
ALIASES = {
    "buy-and-sell-crypto-with-cooldown": "best-time-to-buy-and-sell-stock-with-cooldown",
    "count-paths": "unique-paths",
    "count-squares": "detect-squares",
    "count-subsequences": "distinct-subsequences",
    "insert-new-interval": "insert-interval",
    "longest-increasing-path-in-matrix": "longest-increasing-path-in-a-matrix",
    "meeting-schedule-ii": "meeting-rooms-ii",
    "merge-triplets-to-form-target": "merge-triplets-to-form-target-triplet",
    "minimum-interval-including-query": "minimum-interval-to-include-each-query",
    "pow-x-n": "powx-n",
    "set-zeroes-in-matrix": "set-matrix-zeroes",
}

# NeetCode names files submission-<n>.<ext>; higher n is the later attempt.
SUBMISSION_RE = re.compile(r"^submission-(\d+)\.([A-Za-z0-9]+)$")

# Extension back to the language slug the rest of the tool speaks.
EXTENSION_LANGS = {
    "py": "python3", "java": "java", "cpp": "cpp", "c": "c", "cs": "csharp",
    "js": "javascript", "ts": "typescript", "go": "golang", "rb": "ruby",
    "rs": "rust", "kt": "kotlin", "swift": "swift", "scala": "scala",
    "php": "php", "dart": "dart", "sql": "mysql",
}


@dataclass(frozen=True)
class NeetCodeSolution:
    neetcode_slug: str
    leetcode_slug: str
    lang: str
    path: Path
    timestamp: int

    @property
    def problem_url(self) -> str:
        return f"https://neetcode.io/problems/{self.neetcode_slug}"


def find_problems_root(repo_path: Path) -> Path:
    """NeetCode nests everything under one category folder."""
    if (repo_path / "Data Structures & Algorithms").is_dir():
        return repo_path / "Data Structures & Algorithms"
    candidates = [
        d for d in repo_path.iterdir()
        if d.is_dir() and not d.name.startswith(".")
        and any(child.is_dir() for child in d.iterdir())
    ]
    if len(candidates) == 1:
        return candidates[0]
    return repo_path


def git_timestamp(repo_path: Path, file_path: Path) -> int:
    """When NeetCode synced this file. Falls back to the file's mtime."""
    result = subprocess.run(
        ["git", "-C", str(repo_path), "log", "-1", "--format=%ct", "--", str(file_path)],
        capture_output=True, text=True, check=False,
    )
    value = result.stdout.strip()
    if value.isdigit():
        return int(value)
    return int(file_path.stat().st_mtime)


def latest_per_language(problem_dir: Path) -> dict[str, Path]:
    """Keep the highest-numbered submission for each language."""
    best: dict[str, tuple[int, Path]] = {}
    for child in problem_dir.iterdir():
        match = SUBMISSION_RE.match(child.name)
        if not match:
            continue
        number, extension = int(match.group(1)), match.group(2).lower()
        lang = EXTENSION_LANGS.get(extension)
        if lang is None or extension not in set(LANG_EXTENSIONS.values()):
            continue
        current = best.get(lang)
        if current is None or number > current[0]:
            best[lang] = (number, child)
    return {lang: path for lang, (_, path) in best.items()}


def discover(repo_path: Path) -> list[NeetCodeSolution]:
    """Every solution in the synced repo, one per problem per language."""
    root = find_problems_root(repo_path)
    found: list[NeetCodeSolution] = []
    for problem_dir in sorted(d for d in root.iterdir() if d.is_dir()):
        neetcode_slug = problem_dir.name
        leetcode_slug = ALIASES.get(neetcode_slug, neetcode_slug)
        for lang, path in sorted(latest_per_language(problem_dir).items()):
            found.append(
                NeetCodeSolution(
                    neetcode_slug=neetcode_slug,
                    leetcode_slug=leetcode_slug,
                    lang=lang,
                    path=path,
                    timestamp=git_timestamp(repo_path, path),
                )
            )
    return found


def to_submission(solution: NeetCodeSolution, question: Question) -> Submission:
    code = solution.path.read_text(encoding="utf-8", errors="replace")
    return Submission(
        id=0,
        title=question.title,
        slug=question.slug,
        lang=solution.lang,
        lang_name=solution.lang,
        status="Accepted",
        timestamp=solution.timestamp,
        runtime="",
        memory="",
        code=code,
        source="neetcode",
    )
