"""Move already-synced problems into the folders the current ranking implies.

The solution files hold the code; the index holds everything else. So changing
how topics are chosen is a rename job, not a reason to re-download history.
"""

from __future__ import annotations

import shutil
from dataclasses import dataclass
from pathlib import Path

from .bucketing import assign_topics
from .config import REPO_ROOT, TOPIC_MIN_SIZE
from .layout import placement_for
from .state import SolutionIndex


@dataclass(frozen=True)
class Move:
    source: str
    destination: str


def find_problem_dir(repo_root: Path, problem_dir: str) -> Path | None:
    """Locate a problem folder under whichever topic it currently sits in."""
    for candidate in repo_root.glob(f"*/{problem_dir}"):
        if candidate.is_dir():
            return candidate
    return None


def plan_moves(repo_root: Path, index: SolutionIndex,
               min_size: int = TOPIC_MIN_SIZE) -> list[Move]:
    entries = index.entries()
    assignment = assign_topics([question for question, _ in entries], min_size)
    moves: list[Move] = []
    for question, submissions in entries:
        placement = placement_for(question, submissions[0], assignment.get(question.slug))
        destination = repo_root / placement.topic_dir / placement.problem_dir
        current = find_problem_dir(repo_root, placement.problem_dir)
        if current is None or current.resolve() == destination.resolve():
            continue
        moves.append(
            Move(
                source=current.relative_to(repo_root).as_posix(),
                destination=destination.relative_to(repo_root).as_posix(),
            )
        )
    return moves


def prune_empty_topic_dirs(repo_root: Path) -> list[str]:
    """A topic folder that lost its last problem should not linger."""
    removed = []
    for child in sorted(repo_root.iterdir()):
        if not child.is_dir() or child.name.startswith(".") or child.name == "_sync":
            continue
        if not any(child.iterdir()):
            child.rmdir()
            removed.append(child.name)
    return removed


def apply_moves(repo_root: Path, moves: list[Move]) -> None:
    for move in moves:
        source = repo_root / move.source
        destination = repo_root / move.destination
        destination.parent.mkdir(parents=True, exist_ok=True)
        shutil.move(str(source), str(destination))
