import datetime as dt
import json
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.streak import (
    apply_block, compute, fetch_calendar, glyph, render_block, render_calendar,
)

START = "<!-- leetcode-streak:start -->"
END = "<!-- leetcode-streak:end -->"
TODAY = dt.date(2026, 9, 29)


def days_from(*specs):
    return {dt.date(2026, 9, d): c for d, c in specs}


def test_glyph_levels_are_ordered():
    assert glyph(0) == "·"
    assert glyph(1) == "░"
    assert glyph(4) == "░"
    assert glyph(5) == "▒"
    assert glyph(10) == "▓"
    assert glyph(20) == "█"
    assert glyph(500) == "█"


def test_no_activity_is_not_a_streak():
    s = compute({}, TODAY)
    assert (s.current, s.longest, s.total_active) == (0, 0, 0)
    assert s.current_start is None and s.last_active is None


def test_current_streak_counts_back_from_today():
    s = compute(days_from((27, 1), (28, 1), (29, 1)), TODAY)
    assert s.current == 3
    assert s.current_start == dt.date(2026, 9, 27)


def test_yesterday_still_counts_because_today_is_not_over():
    s = compute(days_from((26, 1), (27, 1), (28, 1)), TODAY)
    assert s.current == 3
    assert s.last_active == dt.date(2026, 9, 28)


def test_a_two_day_gap_breaks_the_current_streak():
    s = compute(days_from((25, 1), (26, 1), (27, 1)), TODAY)
    assert s.current == 0
    assert s.current_start is None
    assert s.longest == 3  # the run still counts toward the record


def test_longest_streak_is_not_the_current_one():
    # This is the bug LeetCode's own `streak` field has: it reports the record.
    s = compute(days_from(*[(d, 1) for d in range(1, 11)], (28, 1), (29, 1)), TODAY)
    assert s.longest == 10
    assert s.current == 2


def test_a_gap_inside_the_history_splits_the_runs():
    s = compute(days_from((20, 1), (21, 1), (23, 1), (24, 1), (25, 1)), TODAY)
    assert s.longest == 3
    assert s.current == 0
    assert s.total_active == 5


def test_streak_can_span_a_year_boundary():
    days = {dt.date(2025, 12, 30): 1, dt.date(2025, 12, 31): 1,
            dt.date(2026, 1, 1): 1, dt.date(2026, 1, 2): 1}
    s = compute(days, dt.date(2026, 1, 2))
    assert s.current == 4
    assert s.current_start == dt.date(2025, 12, 30)


def test_calendar_rows_all_have_the_same_width():
    grid = render_calendar(days_from((28, 3), (29, 7)), TODAY, weeks=4)
    rows = grid.splitlines()
    assert len(rows) == 5  # header plus four weeks
    assert len({len(r) for r in rows}) == 1


def test_calendar_leaves_future_days_blank():
    grid = render_calendar(days_from((29, 9)), TODAY, weeks=1)
    week = grid.splitlines()[1]
    # Sep 29 2026 is a Tuesday; Wednesday onward has not happened yet.
    assert "▒" in week
    assert week.rstrip() != week  # trailing cells are spaces


def test_block_mentions_the_streak_and_the_legend():
    s = compute(days_from((27, 1), (28, 1), (29, 1)), TODAY)
    block = render_block(s, TODAY, weeks=4, profile="https://example.com/u/x/")
    assert "**3 day streak**" in block
    assert "https://example.com/u/x/" in block
    assert "20+ submissions" in block
    assert block.count("```") == 2


def test_block_says_so_when_the_streak_is_broken():
    s = compute(days_from((20, 1)), TODAY)
    block = render_block(s, TODAY, weeks=4)
    assert "No active streak" in block
    assert "Sep 20" in block


def test_apply_block_appends_when_there_are_no_markers():
    out = apply_block("# Hi\n", "STREAK", START, END)
    assert out.startswith("# Hi\n")
    assert START in out and END in out and "STREAK" in out


def test_apply_block_replaces_between_markers_and_keeps_the_rest():
    original = f"top\n\n{START}\nOLD\n{END}\n\nbottom\n"
    out = apply_block(original, "NEW", START, END)
    assert "OLD" not in out
    assert "NEW" in out
    assert out.startswith("top\n") and out.endswith("bottom\n")


def test_apply_block_is_idempotent():
    once = apply_block("# Hi\n", "STREAK", START, END)
    twice = apply_block(once, "STREAK", START, END)
    assert once == twice


class FakeClient:
    """Returns a calendar per year, the way the GraphQL endpoint does."""

    def __init__(self, per_year):
        self.per_year = per_year
        self.years_asked = []

    def _send(self, method, url, **kwargs):
        year = kwargs["json"]["variables"]["year"]
        self.years_asked.append(year)
        calendar = {
            str(int(dt.datetime(d.year, d.month, d.day, tzinfo=dt.UTC).timestamp())): c
            for d, c in self.per_year.get(year, {}).items()
        }
        payload = {"data": {"matchedUser": {"userCalendar": {
            "totalActiveDays": len(calendar),
            "submissionCalendar": json.dumps(calendar)}}}}

        class R:
            def json(self): return payload
        return R()


def test_fetch_calendar_merges_the_years_it_is_given():
    client = FakeClient({
        2025: {dt.date(2025, 12, 31): 2},
        2026: {dt.date(2026, 1, 1): 5},
    })
    days = fetch_calendar(client, "someone", [2025, 2026])
    assert days == {dt.date(2025, 12, 31): 2, dt.date(2026, 1, 1): 5}
    assert client.years_asked == [2025, 2026]


def test_fetch_calendar_tolerates_a_year_with_no_data():
    client = FakeClient({2026: {dt.date(2026, 1, 1): 1}})
    assert fetch_calendar(client, "someone", [2025, 2026]) == {dt.date(2026, 1, 1): 1}
