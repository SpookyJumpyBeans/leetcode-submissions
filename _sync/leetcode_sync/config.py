"""Configuration, credential loading, and language metadata."""

from __future__ import annotations

import os
from dataclasses import dataclass
from pathlib import Path

# Where the solution tree lives: the repo root, one level above _sync/.
REPO_ROOT = Path(__file__).resolve().parents[2]
SYNC_ROOT = REPO_ROOT / "_sync"
STATE_PATH = SYNC_ROOT / ".sync_state.json"
CACHE_PATH = SYNC_ROOT / ".problem_cache.json"
ENV_PATH = SYNC_ROOT / ".env"

# LeetCode language slug -> source file extension.
LANG_EXTENSIONS = {
    "bash": "sh",
    "c": "c",
    "cpp": "cpp",
    "csharp": "cs",
    "dart": "dart",
    "elixir": "ex",
    "erlang": "erl",
    "golang": "go",
    "java": "java",
    "javascript": "js",
    "kotlin": "kt",
    "mssql": "sql",
    "mysql": "sql",
    "oraclesql": "sql",
    "php": "php",
    "postgresql": "sql",
    "python": "py",
    "python3": "py",
    "pythondata": "py",
    "racket": "rkt",
    "react": "jsx",
    "ruby": "rb",
    "rust": "rs",
    "scala": "scala",
    "swift": "swift",
    "typescript": "ts",
}

# Human-facing names for the ones whose slug is not obvious.
LANG_DISPLAY = {
    "bash": "Bash",
    "c": "C",
    "cpp": "C++",
    "csharp": "C#",
    "golang": "Go",
    "javascript": "JavaScript",
    "mssql": "MS SQL",
    "mysql": "MySQL",
    "oraclesql": "Oracle SQL",
    "postgresql": "PostgreSQL",
    "python": "Python",
    "python3": "Python3",
    "pythondata": "Pandas",
    "typescript": "TypeScript",
    "dart": "Dart",
    "elixir": "Elixir",
    "erlang": "Erlang",
    "java": "Java",
    "kotlin": "Kotlin",
    "php": "PHP",
    "racket": "Racket",
    "ruby": "Ruby",
    "rust": "Rust",
    "scala": "Scala",
    "swift": "Swift",
}

# Line-comment token per extension, used for the provenance header.
COMMENT_TOKENS = {
    "c": "//", "cpp": "//", "cs": "//", "dart": "//", "go": "//", "java": "//",
    "js": "//", "jsx": "//", "kt": "//", "php": "//", "rs": "//", "scala": "//",
    "swift": "//", "ts": "//",
    "ex": "#", "py": "#", "rb": "#", "sh": "#",
    "erl": "%", "rkt": ";;", "sql": "--",
}


def lang_extension(lang_slug: str) -> str:
    return LANG_EXTENSIONS.get(lang_slug, "txt")


def lang_display(lang_slug: str, fallback: str = "") -> str:
    if lang_slug in LANG_DISPLAY:
        return LANG_DISPLAY[lang_slug]
    return fallback or lang_slug


def comment_token(extension: str) -> str:
    return COMMENT_TOKENS.get(extension, "#")


def load_env_file(path: Path = ENV_PATH) -> dict[str, str]:
    """Parse a minimal KEY=VALUE .env file. Missing file is not an error."""
    values: dict[str, str] = {}
    if not path.exists():
        return values
    for raw in path.read_text(encoding="utf-8").splitlines():
        line = raw.strip()
        if not line or line.startswith("#") or "=" not in line:
            continue
        key, _, value = line.partition("=")
        value = value.strip().strip('"').strip("'")
        values[key.strip()] = value
    return values


class MissingCredentials(RuntimeError):
    pass


@dataclass(frozen=True)
class Credentials:
    session: str
    csrf_token: str

    @classmethod
    def load(cls) -> "Credentials":
        """Read cookies from the environment, falling back to _sync/.env."""
        file_values = load_env_file()
        session = os.environ.get("LEETCODE_SESSION") or file_values.get("LEETCODE_SESSION", "")
        csrf = os.environ.get("LEETCODE_CSRF_TOKEN") or file_values.get("LEETCODE_CSRF_TOKEN", "")
        if not session:
            raise MissingCredentials(
                "LEETCODE_SESSION is not set. Copy it from your browser cookies into "
                f"{ENV_PATH} (see .env.example)."
            )
        return cls(session=session, csrf_token=csrf)


# Topic-folder ranking. LeetCode returns tags in its own order, and that order
# puts the vaguest tag first far too often - "array" leads on roughly half of
# all problems, which collapses the whole repo into one folder. Lower rank wins,
# so a problem tagged [array, dynamic-programming] files under the DP folder.
#
# The tiers, loosely: distinctive structures and niche techniques beat the big
# algorithm families, which beat generic traversal techniques, which beat the
# catch-all container tags.
TOPIC_RANK = {
    # Distinctive structures and niche techniques - always the best label.
    "segment-tree": 10, "binary-indexed-tree": 10, "trie": 10, "union-find": 10,
    "suffix-array": 10, "minimum-spanning-tree": 10, "strongly-connected-component": 10,
    "biconnected-component": 10, "eulerian-circuit": 10, "rolling-hash": 10,
    "string-matching": 10, "shortest-path": 10, "monotonic-stack": 10,
    "monotonic-queue": 10, "line-sweep": 10, "reservoir-sampling": 10,
    "rejection-sampling": 10, "randomized": 10, "quickselect": 10, "radix-sort": 10,
    "bucket-sort": 10, "counting-sort": 10, "merge-sort": 10, "game-theory": 10,
    "geometry": 10, "number-theory": 10, "combinatorics": 10, "bitmask": 10,
    "probability-and-statistics": 10, "concurrency": 10, "interactive": 10,
    "shell": 10, "iterator": 10, "data-stream": 10, "doubly-linked-list": 10,
    "ordered-set": 10, "hash-function": 10, "brainteaser": 10,

    # The big algorithm families people actually browse for.
    "dynamic-programming": 15, "backtracking": 15,

    # Data structures: a better shelf than the technique used to walk them.
    "tree": 18, "binary-tree": 18, "binary-search-tree": 18, "graph": 18,
    "linked-list": 18, "heap-priority-queue": 18, "stack": 18, "queue": 18,
    "design": 18, "database": 18,

    # Techniques.
    "sliding-window": 22, "binary-search": 22, "two-pointers": 22,
    "divide-and-conquer": 22, "greedy": 22, "recursion": 22, "memoization": 22,
    "depth-first-search": 22, "breadth-first-search": 22, "topological-sort": 22,

    # Weak labels: true of a great many problems.
    "prefix-sum": 30, "bit-manipulation": 30, "simulation": 30, "matrix": 30,
    "sorting": 40, "counting": 40, "enumeration": 40,

    # Catch-alls. These should only ever win when nothing else applies.
    "hash-table": 90, "math": 92, "string": 95, "array": 99,
}

DEFAULT_TOPIC_RANK = 50  # an unrecognised tag is probably reasonably specific


def topic_rank(slug: str) -> int:
    return TOPIC_RANK.get(slug, DEFAULT_TOPIC_RANK)


# A topic folder holding fewer than this many problems is dissolved and its
# problems fall through to their next-best tag. 1 disables the consolidation.
TOPIC_MIN_SIZE = 2
