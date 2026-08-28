"""Orchestration: pull submissions, lay them out, commit the result.

Submissions are written as they arrive rather than after the whole history is
collected, so a run cut short by throttling still leaves useful work behind and
records where to pick up.
"""

from __future__ import annotations

import subprocess
from dataclasses import dataclass, field
from datetime import datetime, timezone
from pathlib import Path

from .api import LeetCodeClient, Submission, ThrottledError
from .config import REPO_ROOT
from .layout import (
    placement_for,
    render_problem_readme,
    render_root_readme,
    render_solution,
    write_text,
)
from .relayout import apply_moves, plan_moves, prune_empty_topic_dirs
from .state import ProblemCache, SolutionIndex, SyncState

INDEX_PATH = REPO_ROOT / "_sync" / ".solution_index.json"


@dataclass
class SyncReport:
    fetched: int = 0
    accepted: int = 0
    written: list[str] = field(default_factory=list)
    skipped_unknown: list[str] = field(default_factory=list)
    newest_id: int | None = None
    newest_timestamp: int | None = None
    partial: bool = False
    interrupted_reason: str = ""
    resumed_from: int | None = None

    @property
    def changed(self) -> bool:
        return bool(self.written)


def select_latest_accepted(submissions) -> dict[tuple[str, str], Submission]:
    """Newest-first input; keep the first Accepted hit per (problem, language)."""
    best: dict[tuple[str, str], Submission] = {}
    for submission in submissions:
        if not submission.accepted or not submission.slug:
            continue
        key = (submission.slug, submission.lang)
        if key not in best:
            best[key] = submission
    return best


def run_sync(
    client: LeetCodeClient,
    state: SyncState,
    *,
    repo_root: Path = REPO_ROOT,
    full: bool = False,
    dry_run: bool = False,
    max_submissions: int | None = None,
    cache: ProblemCache | None = None,
    index: SolutionIndex | None = None,
    log=print,
) -> SyncReport:
    report = SyncReport()
    cache = cache if cache is not None else ProblemCache()
    index = index if index is not None else SolutionIndex(INDEX_PATH)

    stop_id = None if full else state.newest_submission_id
    stop_ts = None if full else state.newest_timestamp

    resumed = bool(full and state.backfill_offset)
    start_offset = state.backfill_offset if resumed else 0
    start_last_key = state.backfill_last_key if resumed else None
    if resumed:
        report.resumed_from = start_offset
        log(f"Resuming the backfill from offset {start_offset}.")

    # Cursor for the *next* unfetched page; (None, None) means history exhausted.
    cursor: dict[str, object] = {"offset": start_offset, "last_key": start_last_key}

    def on_page(offset, last_key):
        cursor["offset"], cursor["last_key"] = offset, last_key

    seen: set[tuple[str, str]] = set()
    touched: set[str] = set()

    def handle(submission: Submission) -> None:
        if not submission.accepted or not submission.slug:
            return
        key = (submission.slug, submission.lang)
        if key in seen:
            return
        seen.add(key)
        # A resumed walk moves backwards in time; never regress a newer solution.
        if index.has_newer(submission.slug, submission.lang, submission.timestamp):
            return

        question = cache.get(submission.slug)
        if question is None:
            question = client.get_question(submission.slug)
            if question is None:
                report.skipped_unknown.append(submission.slug)
                return
            cache.put(question)

        report.accepted += 1
        placement = placement_for(question, submission)
        index.record(question, submission)
        touched.add(question.slug)
        if dry_run:
            report.written.append(placement.relative_path)
            return
        path = repo_root / placement.topic_dir / placement.problem_dir / placement.filename
        if write_text(path, render_solution(question, submission)):
            report.written.append(placement.relative_path)

    try:
        for submission in client.iter_submissions(
            stop_at_id=stop_id,
            stop_before_timestamp=stop_ts,
            start_offset=start_offset,
            start_last_key=start_last_key,
            on_page=on_page,
        ):
            report.fetched += 1
            if report.fetched == 1 and not resumed:
                report.newest_id = submission.id
                report.newest_timestamp = submission.timestamp
            handle(submission)
            if report.fetched % 100 == 0:
                log(f"  ...{report.fetched} submissions, {report.accepted} solutions kept")
            if max_submissions is not None and report.fetched >= max_submissions:
                break
    except ThrottledError as exc:
        report.partial = True
        report.interrupted_reason = str(exc)
        log(f"Throttled after {report.fetched} submissions - saving progress.")

    if report.fetched == 0 and not report.partial:
        log("No new submissions.")
        return report

    if dry_run:
        cache.save()
        return report

    _write_readmes(repo_root, index, touched)
    cache.save()
    index.save()

    if report.newest_id is not None:
        state.newest_submission_id = report.newest_id
        state.newest_timestamp = report.newest_timestamp
    if full:
        finished = cursor["offset"] is None and not report.partial
        state.backfill_offset = None if finished else cursor["offset"]
        state.backfill_last_key = None if finished else cursor["last_key"]
        state.backfill_complete = finished
    state.last_run = datetime.now(timezone.utc).isoformat(timespec="seconds")
    state.problems_synced = len(index)
    state.save()
    return report


def run_relayout(repo_root: Path = REPO_ROOT, index: SolutionIndex | None = None,
                 dry_run: bool = False, log=print) -> list:
    """Re-file already-synced problems under the current topic ranking."""
    index = index if index is not None else SolutionIndex(INDEX_PATH)
    moves = plan_moves(repo_root, index)
    if not moves:
        log("Every problem is already in the right folder.")
        return moves
    log(f"{len(moves)} problems move:")
    for move in moves[:15]:
        log(f"  {move.source}  ->  {move.destination}")
    if len(moves) > 15:
        log(f"  ... and {len(moves) - 15} more")
    if dry_run:
        return moves

    apply_moves(repo_root, moves)
    removed = prune_empty_topic_dirs(repo_root)
    if removed:
        log(f"Removed empty topic folders: {', '.join(removed)}")
    all_slugs = {question.slug for question, _ in index.entries()}
    _write_readmes(repo_root, index, all_slugs)
    return moves


def _write_readmes(repo_root: Path, index: SolutionIndex, touched: set[str]) -> None:
    entries = index.entries()
    for question, submissions in entries:
        if question.slug not in touched:
            continue
        placement = placement_for(question, submissions[0])
        readme = repo_root / placement.topic_dir / placement.problem_dir / "README.md"
        write_text(readme, render_problem_readme(question, submissions))
    write_text(repo_root / "README.md", render_root_readme(entries))


def git(*args: str, repo_root: Path = REPO_ROOT) -> subprocess.CompletedProcess:
    return subprocess.run(
        ["git", "-C", str(repo_root), *args],
        capture_output=True,
        text=True,
        check=False,
    )


def commit(report: SyncReport, repo_root: Path = REPO_ROOT, push: bool = False,
           log=print) -> bool:
    """Stage and commit whatever the sync produced. Returns True if a commit was made."""
    if not report.changed:
        log("Nothing to commit.")
        return False
    git("add", "-A", repo_root=repo_root)
    status = git("status", "--porcelain", repo_root=repo_root)
    if not status.stdout.strip():
        log("Working tree clean; nothing to commit.")
        return False

    count = len(report.written)
    if count == 1:
        subject = f"Add solution: {report.written[0]}"
    else:
        subject = f"Sync {count} LeetCode solutions"
    if report.partial:
        subject += " (partial)"
    result = git("commit", "-m", subject, repo_root=repo_root)
    if result.returncode != 0:
        log(result.stderr.strip() or result.stdout.strip())
        return False
    log(f"Committed: {subject}")

    if push:
        pushed = git("push", repo_root=repo_root)
        if pushed.returncode != 0:
            log("Push failed:\n" + (pushed.stderr.strip() or pushed.stdout.strip()))
        else:
            log("Pushed to remote.")
    return True


def summarize(report: SyncReport) -> str:
    lines = [
        f"Submissions examined : {report.fetched}",
        f"Solutions kept       : {report.accepted}",
        f"Files written        : {len(report.written)}",
    ]
    if report.skipped_unknown:
        shown = ", ".join(report.skipped_unknown[:5])
        more = ", ..." if len(report.skipped_unknown) > 5 else ""
        lines.append(f"Skipped (no metadata): {len(report.skipped_unknown)} ({shown}{more})")
    if report.partial:
        lines.append("")
        lines.append("PARTIAL RUN - everything above is saved.")
        lines.append("Rerun with --full to continue from where it stopped.")
    return "\n".join(lines)
