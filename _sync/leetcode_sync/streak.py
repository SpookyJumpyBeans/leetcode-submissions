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

# Submissions needed to reach each colour level above zero: 1-4, 5-9, 10-19, 20+.
THRESHOLDS = (1, 5, 10, 20)

# GitHub's own contribution greens, light theme then dark. The SVG picks one
# with prefers-color-scheme, so the heatmap matches whichever theme the viewer
# has GitHub in.
LIGHT_LEVELS = ("#ebedf0", "#9be9a8", "#40c463", "#30a14e", "#216e39")
DARK_LEVELS = ("#161b22", "#0e4429", "#006d32", "#26a641", "#39d353")

CELL, GAP = 11, 3
STEP = CELL + GAP
LEFT, TOP = 34, 22


def level(count: int) -> int:
    """0 for no submissions, up to 4 for the busiest days."""
    return sum(1 for threshold in THRESHOLDS if count >= threshold)


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


def heatmap_start(today: dt.date, weeks: int) -> dt.date:
    """The Sunday that opens the first column, so the last column holds today."""
    this_sunday = today - dt.timedelta(days=(today.weekday() + 1) % 7)
    return this_sunday - dt.timedelta(weeks=weeks - 1)


def submissions_in_window(days: dict[dt.date, int], today: dt.date, weeks: int) -> int:
    start = heatmap_start(today, weeks)
    return sum(count for day, count in days.items() if start <= day <= today)


def render_heatmap(days: dict[dt.date, int], today: dt.date, weeks: int = 53) -> str:
    """A contribution-style calendar as SVG: one column per week, Sunday on top.

    Laid out the way GitHub draws contributions: months across the top, Mon,
    Wed and Fri down the side, a Less-to-More legend underneath, and nothing
    drawn for days that have not happened yet.
    """
    start = heatmap_start(today, weeks)
    # Wide enough for the grid, and for the legend on a calendar of only a few weeks.
    width = max(LEFT + weeks * STEP + 2, LEFT + 5 * STEP + 60)
    height = TOP + 7 * STEP + 26
    font = "-apple-system, Segoe UI, Helvetica, Arial, sans-serif"

    styles = "".join(f".l{i}{{fill:{c}}}" for i, c in enumerate(LIGHT_LEVELS))
    dark = "".join(f".l{i}{{fill:{c}}}" for i, c in enumerate(DARK_LEVELS))
    parts = [
        f'<svg xmlns="http://www.w3.org/2000/svg" width="{width}" height="{height}" '
        f'viewBox="0 0 {width} {height}" role="img" '
        f'aria-label="LeetCode submissions per day, last {weeks} weeks">',
        f"<style>text{{font:10px {font};fill:#57606a}}rect{{rx:2px;ry:2px;"
        f"stroke:rgba(27,31,35,.06);stroke-width:1px}}{styles}"
        f"@media (prefers-color-scheme:dark){{text{{fill:#8b949e}}"
        f"rect{{stroke:rgba(255,255,255,.05)}}{dark}}}</style>",
    ]

    last_label = -3
    for week in range(weeks):
        sunday = start + dt.timedelta(weeks=week)
        x = LEFT + week * STEP
        # A month is labelled over the week its 1st falls in, if there is room.
        firsts = [sunday + dt.timedelta(days=d) for d in range(7)
                  if (sunday + dt.timedelta(days=d)).day == 1]
        if (firsts or week == 0) and week - last_label >= 3 and week < weeks - 1:
            month = firsts[0] if firsts else sunday
            parts.append(f'<text x="{x}" y="{TOP - 8}">{month:%b}</text>')
            last_label = week
        for d in range(7):
            day = sunday + dt.timedelta(days=d)
            if day > today:
                break
            parts.append(f'<rect class="l{level(days.get(day, 0))}" x="{x}" '
                         f'y="{TOP + d * STEP}" width="{CELL}" height="{CELL}"/>')

    for d, name in ((1, "Mon"), (3, "Wed"), (5, "Fri")):
        parts.append(f'<text x="0" y="{TOP + d * STEP + 9}">{name}</text>')

    legend_y = TOP + 7 * STEP + 8
    x = width - 2 - 5 * STEP - 30
    parts.append(f'<text x="{x - 28}" y="{legend_y + 9}">Less</text>')
    for i in range(5):
        parts.append(f'<rect class="l{i}" x="{x + i * STEP}" y="{legend_y}" '
                     f'width="{CELL}" height="{CELL}"/>')
    parts.append(f'<text x="{x + 5 * STEP + 3}" y="{legend_y + 9}">More</text>')
    parts.append("</svg>")
    return "\n".join(parts) + "\n"


def short_date(day: dt.date) -> str:
    """"Sep 3" - written out rather than with %-d, which Windows rejects."""
    return f"{day:%b} {day.day}"


def render_block(stats: Streak, today: dt.date, weeks: int = 53,
                 profile: str = "", heatmap_url: str = "") -> str:
    """The markdown that goes between the streak markers."""
    if stats.current:
        headline = (f"🔥 **{stats.current} day streak** "
                    f"(since {short_date(stats.current_start)})" if stats.current > 1
                    else "🔥 **1 day streak**")
    else:
        last = f", last solved {short_date(stats.last_active)}" if stats.last_active else ""
        headline = f"**No active streak**{last}"

    link = f"[LeetCode]({profile})" if profile else "LeetCode"
    total = submissions_in_window(stats.days, today, weeks)
    lines = [
        f"### {link} streak",
        "",
        f"{headline} &nbsp;·&nbsp; longest **{stats.longest} days** "
        f"&nbsp;·&nbsp; **{stats.total_active}** active days",
        "",
    ]
    if heatmap_url:
        # The date in the query string changes the URL daily, so GitHub's image
        # cache cannot keep serving yesterday's calendar.
        lines += [f"![{total} submissions in the last {weeks} weeks]"
                  f"({heatmap_url}?v={today:%Y-%m-%d})", ""]
    lines.append(f"<sub>**{total}** submissions in the last {weeks} weeks, "
                 f"by UTC day. Updated {today:%Y-%m-%d}.</sub>")
    return "\n".join(lines)


def build(client, username: str, today: dt.date | None = None,
          weeks: int = 53, profile: str = "",
          heatmap_url: str = "") -> tuple[Streak, str, str]:
    """Fetch, compute and render in one step: stats, README block, heatmap SVG."""
    today = today or dt.datetime.now(dt.UTC).date()
    # The previous year too, so a streak crossing New Year is not cut in half,
    # and so a 53-week heatmap always has a full year behind it.
    days = fetch_calendar(client, username, [today.year - 1, today.year])
    stats = compute(days, today)
    return (stats, render_block(stats, today, weeks, profile, heatmap_url),
            render_heatmap(days, today, weeks))


def apply_block(text: str, block: str, start: str, end: str) -> str:
    """Replace the marked region of a README, or append it if unmarked."""
    marked = f"{start}\n{block}\n{end}"
    if start in text and end in text:
        head = text[: text.index(start)]
        tail = text[text.index(end) + len(end):]
        return head + marked + tail
    separator = "" if text.endswith("\n\n") else ("\n" if text.endswith("\n") else "\n\n")
    return text + separator + marked + "\n"


def remove_block(text: str, start: str, end: str) -> str:
    """Strip the marked region and its markers, leaving the rest untouched."""
    if start not in text or end not in text:
        return text
    head = text[: text.index(start)]
    tail = text[text.index(end) + len(end):]
    return (head.rstrip("\n") + "\n") if head.strip() else tail.lstrip("\n")
