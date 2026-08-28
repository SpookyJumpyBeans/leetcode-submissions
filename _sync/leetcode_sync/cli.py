"""Command line entry point."""

from __future__ import annotations

import argparse
import sys

from .api import AuthError, LeetCodeClient, LeetCodeError
from .config import Credentials, MissingCredentials
from .state import SyncState
from .sync import commit, run_sync, summarize


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        prog="leetcode-sync",
        description="Mirror accepted LeetCode submissions into this repository.",
    )
    parser.add_argument(
        "--full",
        action="store_true",
        help="Ignore saved state and walk the entire submission history (the backfill).",
    )
    parser.add_argument(
        "--dry-run",
        action="store_true",
        help="Show what would be written without touching the repo.",
    )
    parser.add_argument(
        "--max-submissions",
        type=int,
        default=None,
        metavar="N",
        help="Stop after examining N submissions. Useful for a first smoke test.",
    )
    parser.add_argument(
        "--delay",
        type=float,
        default=1.0,
        metavar="SECONDS",
        help="Pause between history pages (default: 1.0). Raise it if you hit rate limits.",
    )
    parser.add_argument(
        "--no-commit",
        action="store_true",
        help="Write files but leave them unstaged instead of committing.",
    )
    parser.add_argument(
        "--push",
        action="store_true",
        help="Push after committing (requires a configured remote).",
    )
    return parser


def main(argv: list[str] | None = None) -> int:
    args = build_parser().parse_args(argv)

    try:
        credentials = Credentials.load()
    except MissingCredentials as exc:
        print(f"error: {exc}", file=sys.stderr)
        return 2

    client = LeetCodeClient(
        session_cookie=credentials.session,
        csrf_token=credentials.csrf_token,
        delay=args.delay,
    )
    state = SyncState.load()

    if args.full:
        print("Full backfill: walking the entire submission history.")
    elif state.newest_submission_id:
        print(f"Incremental sync since submission {state.newest_submission_id}.")
    else:
        print("No previous state found; this run will walk the full history.")

    try:
        report = run_sync(
            client,
            state,
            full=args.full,
            dry_run=args.dry_run,
            max_submissions=args.max_submissions,
        )
    except AuthError as exc:
        print(f"auth error: {exc}", file=sys.stderr)
        return 3
    except LeetCodeError as exc:
        print(f"error: {exc}", file=sys.stderr)
        return 1

    print(summarize(report))

    if args.dry_run:
        for path in report.written[:40]:
            print(f"  would write {path}")
        if len(report.written) > 40:
            print(f"  ... and {len(report.written) - 40} more")
        return 0

    if not args.no_commit:
        commit(report, push=args.push)
    return 0
