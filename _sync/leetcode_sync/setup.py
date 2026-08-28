"""Interactive credential capture, so the cookies never touch shell history."""

from __future__ import annotations

from getpass import getpass
from pathlib import Path

from .config import ENV_PATH

HEADER = """# Written by `python -m leetcode_sync --set-cookies`.
# LEETCODE_SESSION is a login token: treat it like a password.
# This file is gitignored. Cookies expire every week or two - rerun that
# command when the sync reports an auth error.
"""


def _clean(value: str) -> str:
    return value.strip().strip('"').strip("'").strip()


def write_env(session: str, csrf: str, path: Path = ENV_PATH) -> None:
    body = "{}\nLEETCODE_SESSION={}\nLEETCODE_CSRF_TOKEN={}\n".format(HEADER, session, csrf)
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(body, encoding="utf-8", newline="\n")


def prompt_for_cookies(path: Path = ENV_PATH, log=print) -> int:
    log("Paste the cookie values from LeetCode (DevTools -> Application -> Cookies).")
    log("The session value stays hidden as you paste - that is expected, not a hang.")
    log("")
    session = _clean(getpass("LEETCODE_SESSION (hidden): "))
    if not session:
        log("Nothing entered; leaving the file alone.")
        return 1
    csrf = _clean(input("csrftoken (visible): "))

    write_env(session, csrf, path)
    log("")
    log("Wrote {}".format(path))
    log("  LEETCODE_SESSION    : {} characters".format(len(session)))
    log("  LEETCODE_CSRF_TOKEN : {} characters".format(len(csrf)))
    if len(session) < 100:
        log("")
        log("Heads up: the session cookie is normally several hundred characters.")
        log("That looks truncated - recopy it with double-click, Ctrl+A, Ctrl+C.")
    return 0
