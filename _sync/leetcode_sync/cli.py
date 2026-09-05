"""Command line entry point."""

from __future__ import annotations

import argparse
import sys
from pathlib import Path

from .api import AuthError, LeetCodeClient, LeetCodeError
from .config import NEETCODE_CLONE, NEETCODE_REPO, Credentials, MissingCredentials
from .neetcode import ensure_clone
from .setup import prompt_for_cookies
from .state import SyncState
from .sync import SyncReport, commit, run_import_neetcode, run_relayout, run_sync, summarize


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser(
        prog="leetcode-sync",
        description="Mirror accepted LeetCode submissions into this repository.",
    )
    parser.add_argument(
        "--set-cookies",
        action="store_true",
        help="Prompt for your LeetCode cookies and write them to _sync/.env.",
    )
    parser.add_argument(
        "--import-neetcode",
        metavar="PATH",
        default=None,
        help="Fold a NeetCode GitHub Sync repo into this tree (path to a local clone).",
    )
    parser.add_argument(
        "--with-neetcode",
        action="store_true",
        help="After syncing LeetCode, refresh the NeetCode clone and import from it.",
    )
    parser.add_argument(
        "--include-existing",
        action="store_true",
        help="With --import-neetcode, also import problems already solved on LeetCode.",
    )
    parser.add_argument(
        "--relayout",
        action="store_true",
        help="Re-file synced problems under the current topic ranking. No network.",
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
        default=2.0,
        metavar="SECONDS",
        help="Pause between history pages (default: 2.0). Raise it if you hit rate limits.",
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

    if args.set_cookies:
        return prompt_for_cookies()

    if args.import_neetcode:
        try:
            credentials = Credentials.load()
        except MissingCredentials as exc:
            print(f"error: {exc}", file=sys.stderr)
            return 2
        client = LeetCodeClient(credentials.session, credentials.csrf_token, delay=args.delay)
        report = run_import_neetcode(
            Path(args.import_neetcode), client,
            include_existing=args.include_existing, dry_run=args.dry_run,
        )
        print(f"Imported            : {len(report.imported)}")
        print(f"Already on LeetCode : {len(report.skipped_existing)}")
        if report.unresolved:
            print(f"Unresolved slugs    : {', '.join(report.unresolved)}")
        for line in report.imported:
            print(f"  + {line}")
        if not args.dry_run and report.changed and not args.no_commit:
            commit(report, push=args.push)
        return 0

    if args.relayout:
        moves = run_relayout(dry_run=args.dry_run)
        if moves and not args.dry_run and not args.no_commit:
            report = SyncReport(written=[m.destination for m in moves])
            commit(report, push=args.push)
        return 0

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

    if args.full and state.backfill_offset:
        print(f"Resuming an interrupted backfill (offset {state.backfill_offset}).")
    elif args.full:
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

    neetcode_report = None
    if args.with_neetcode and not args.dry_run:
        source = ensure_clone(NEETCODE_REPO, NEETCODE_CLONE)
        if source is None:
            print("Skipping the NeetCode import; no clone available.")
        else:
            neetcode_report = run_import_neetcode(source, client)
            print(f"NeetCode imported    : {len(neetcode_report.imported)}")
            for line in neetcode_report.imported:
                print(f"  + {line}")

    if args.dry_run:
        for path in report.written[:40]:
            print(f"  would write {path}")
        if len(report.written) > 40:
            print(f"  ... and {len(report.written) - 40} more")
        return 0

    if not args.no_commit:
        combined = SyncReport(written=list(report.written))
        subject = None
        if neetcode_report and neetcode_report.imported:
            combined.written += neetcode_report.imported
            parts = []
            if report.written:
                parts.append(f"{len(report.written)} from LeetCode")
            parts.append(f"{len(neetcode_report.imported)} from NeetCode")
            subject = "Sync " + " and ".join(parts)
        commit(combined, push=args.push, subject=subject)
    return 0
