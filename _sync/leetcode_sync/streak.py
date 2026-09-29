"""Daily-solve streak, computed from LeetCode's own submission calendar.

LeetCode exposes a `userCalendar` with a `streak` field, but that field is the
*longest* streak of the year, not the current one - it keeps reporting a number
long after the run it describes has ended. So we take the raw calendar and work
both figures out here.

The calendar is keyed to UTC days, which is how leetcode.com counts as well, so
the numbers here match what the profile page shows.
"""

from __future__ import annotations

import datetime as dt
import json
from dataclasses import dataclass, field

CALENDAR_QUERY = """
query userProfileCalendar($username: String!, $year: Int) {
  matchedUser(username: $username) {
    userCalendar(year: $year) {
      totalActiveDays
      submissionCalendar
    }
  }
}
"""

# Submission counts mapped to a block character, densest last.
LEVELS = [(1, "·"), (5, "░"), (10, "▒"), (20, "▓")]
FULL = "█"
WEEKDAYS = ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"]


def glyph(count: int) -> str:
    if count <= 0:
        return LEVELS[0][1]
    for threshold, char in LEVELS[1:]:
        if count < threshold:
            return char
    return FULL


@dataclass
class Streak:
    current: int = 0
    longest: int = 0
    total_active: int = 0
    current_start: dt.date | None = None
    longest_end: dt.date | None = None
    last_active: dt.date | None = None
    days: dict = field(default_factory=dict, repr=False)


def fetch_calendar(client, username: str, years: list[int]) -> dict[dt.date, int]:
    """Merge the submission calendars for the given years into one mapping."""
    from .api import GRAPHQL_URL

    days: dict[dt.date, int] = {}
    for year in years:
        response = client._send(
            "post", GRAPHQL_URL,
            json={"query": CALENDAR_QUERY,
                  "variables": {"username": username, "year": year}},
        )
        payload = (response.json() or {}).get("data") or {}
        matched = payload.get("matchedUser") or {}
        calendar = matched.get("userCalendar") or {}
        raw = calendar.get("submissionCalendar")
        if not raw:
            continue
        for stamp, count in json.loads(raw).items():
            day = dt.datetime.fromtimestamp(int(stamp), dt.UTC).date()
            days[day] = days.get(day, 0) + int(count)
    return days


def compute(days: dict[dt.date, int], today: dt.date) -> Streak:
    """Current and longest runs of consecutive active days.

    A streak is only *current* if it reaches today or yesterday; today may still
    have hours left in it, so yesterday still counts as unbroken.
    """
    if not days:
        return Streak()

    active = sorted(days)
    longest = run = 1
    longest_end = active[0]
    for earlier, later in zip(active, active[1:]):
        run = run + 1 if (later - earlier).days == 1 else 1
        if run > longest:
            longest, longest_end = run, later

    anchor = None
    if today in days:
        anchor = today
    elif (today - dt.timedelta(days=1)) in days:
        anchor = today - dt.timedelta(days=1)

    current = 0
    current_start = None
    if anchor is not None:
        current, day = 1, anchor
        while (day - dt.timedelta(days=1)) in days:
            current += 1
            day -= dt.timedelta(days=1)
        current_start = day

    return Streak(
        current=current,
        longest=longest,
        total_active=len(active),
        current_start=current_start,
        longest_end=longest_end,
        last_active=active[-1],
        days=days,
    )


def render_calendar(days: dict[dt.date, int], today: dt.date, weeks: int = 8) -> str:
    """A monospace heatmap, one row per week, Monday first.

    Label and cells share a fixed column width so the grid lines up in the
    fixed-width font GitHub renders a fenced block in.
    """
    end = today + dt.timedelta(days=(6 - today.weekday()))
    start = end - dt.timedelta(days=weeks * 7 - 1)

    label_w = 9
    cell = lambda text: f"{text:^4}"

    lines = [" " * label_w + "".join(cell(d) for d in WEEKDAYS)]
    for week in range(weeks):
        monday = start + dt.timedelta(days=week * 7)
        cells = []
        for offset in range(7):
            day = monday + dt.timedelta(days=offset)
            cells.append(cell(" ") if day > today else cell(glyph(days.get(day, 0))))
        lines.append(f"{monday:%b %d}".ljust(label_w) + "".join(cells))
    return "\n".join(lines)


def short_date(day: dt.date) -> str:
    """"Sep 3" - written out rather than with %-d, which Windows rejects."""
    return f"{day:%b} {day.day}"


def render_block(stats: Streak, today: dt.date, weeks: int = 8,
                 profile: str = "") -> str:
    """The markdown that goes between the streak markers."""
    if stats.current:
        headline = (f"**{stats.current} day streak** "
                    f"(since {short_date(stats.current_start)})" if stats.current > 1
                    else "**1 day streak**")
    else:
        last = f", last solved {short_date(stats.last_active)}" if stats.last_active else ""
        headline = f"**No active streak**{last}"

    link = f"[LeetCode]({profile})" if profile else "LeetCode"
    lines = [
        f"### {link} streak",
        "",
        f"{headline} &nbsp;·&nbsp; longest **{stats.longest} days** "
        f"&nbsp;·&nbsp; **{stats.total_active}** active days",
        "",
        "```",
        render_calendar(stats.days, today, weeks),
        "```",
        "",
        f"<sub>`·` none &nbsp; `░` 1-4 &nbsp; `▒` 5-9 &nbsp; "
        f"`▓` 10-19 &nbsp; `█` 20+ submissions, by UTC day. "
        f"Updated {today:%Y-%m-%d}.</sub>",
    ]
    return "\n".join(lines)


def build(client, username: str, today: dt.date | None = None,
          weeks: int = 8, profile: str = "") -> tuple[Streak, str]:
    """Fetch, compute and render in one step."""
    today = today or dt.datetime.now(dt.UTC).date()
    # The previous year too, so a streak crossing New Year is not cut in half.
    days = fetch_calendar(client, username, [today.year - 1, today.year])
    stats = compute(days, today)
    return stats, render_block(stats, today, weeks, profile)


def apply_block(text: str, block: str, start: str, end: str) -> str:
    """Replace the marked region of a README, or append it if unmarked."""
    marked = f"{start}\n{block}\n{end}"
    if start in text and end in text:
        head = text[: text.index(start)]
        tail = text[text.index(end) + len(end):]
        return head + marked + tail
    separator = "" if text.endswith("\n\n") else ("\n" if text.endswith("\n") else "\n\n")
    return text + separator + marked + "\n"
