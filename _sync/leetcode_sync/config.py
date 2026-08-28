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
