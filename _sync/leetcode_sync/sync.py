"""Orchestration: pull submissions, lay them out, commit the result."""

from __future__ import annotations

import subprocess
from dataclasses import dataclass, field
from datetime import datetime, timezone
from pathlib import Path

from .api import LeetCodeClient, Question, Submission
from .config import REPO_ROOT
from .layout import (
    placement_for,
    render_problem_readme,
    render_root_readme,
    render_solution,
    write_text,
)
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

    collected: list[Submission] = []
    for submission in client.iter_submissions(stop_at_id=stop_id, stop_before_timestamp=stop_ts):
        collected.append(submission)
        report.fetched += 1
        if report.fetched == 1:
            report.newest_id = submission.id
            report.newest_timestamp = submission.timestamp
        if report.fetched % 100 == 0:
            log(f"  ...fetched {report.fetched} submissions")
        if max_submissions is not None and report.fetched >= max_submissions:
            break

    if not collected:
        log("No new submissions.")
        return report

    keepers = select_latest_accepted(collected)
    report.accepted = len(keepers)
    log(f"Fetched {report.fetched} submissions; {report.accepted} solutions to write.")

    touched_slugs: set[str] = set()
    for (slug, _lang), submission in sorted(keepers.items()):
        question = cache.get(slug)
        if question is None:
            question = client.get_question(slug)
            if question is None:
                report.skipped_unknown.append(slug)
                continue
            cache.put(question)
        placement = placement_for(question, submission)
        index.record(question, submission)
        touched_slugs.add(slug)
        if dry_run:
            report.written.append(placement.relative_path)
            continue
        path = repo_root / placement.topic_dir / placement.problem_dir / placement.filename
        if write_text(path, render_solution(question, submission)):
            report.written.append(placement.relative_path)

    if dry_run:
        cache.save()
        return report

    # Per-problem READMEs, but only for problems this run touched.
    entries = index.entries()
    for question, submissions in entries:
        if question.slug not in touched_slugs:
            continue
        placement = placement_for(question, submissions[0])
        readme = repo_root / placement.topic_dir / placement.problem_dir / "README.md"
        write_text(readme, render_problem_readme(question, submissions))

    write_text(repo_root / "README.md", render_root_readme(entries))

    cache.save()
    index.save()
    state.newest_submission_id = report.newest_id or state.newest_submission_id
    state.newest_timestamp = report.newest_timestamp or state.newest_timestamp
    state.last_run = datetime.now(timezone.utc).isoformat(timespec="seconds")
    state.problems_synced = len(index)
    state.save()
    return report


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
        lines.append(
            f"Skipped (no metadata): {len(report.skipped_unknown)} "
            f"({', '.join(report.skipped_unknown[:5])}"
            + (", ..." if len(report.skipped_unknown) > 5 else "")
            + ")"
        )
    return "\n".join(lines)
