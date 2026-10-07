import datetime as dt
import json
import re
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.streak import (
    apply_block, build, compute, fetch_calendar, heatmap_start, level, render_block,
    render_heatmap,
)

START = "<!-- leetcode-streak:start -->"
END = "<!-- leetcode-streak:end -->"
TODAY = dt.date(2026, 9, 29)


def days_from(*specs):
    return {dt.date(2026, 9, d): c for d, c in specs}


def test_levels_follow_the_thresholds():
    assert [level(n) for n in (0, 1, 4, 5, 9, 10, 19, 20, 500)] == [0, 1, 1, 2, 2, 3, 3, 4, 4]


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


def test_heatmap_starts_on_a_sunday_and_ends_in_the_week_of_today():
    start = heatmap_start(TODAY, 53)
    assert start.weekday() == 6  # Sunday
    assert start + dt.timedelta(weeks=53) > TODAY >= start + dt.timedelta(weeks=52)


def test_heatmap_draws_one_cell_per_day_up_to_today_plus_the_legend():
    svg = render_heatmap(days_from((28, 3), (29, 25)), TODAY, weeks=4)
    cells = re.findall(r'<rect class="l(\d)" x="(\d+)" y="(\d+)"', svg)
    # Sep 29 2026 is a Tuesday: three full weeks plus Sun, Mon, Tue, then 5 legend swatches.
    assert len(cells) == 3 * 7 + 3 + 5
    grid = cells[:-5]
    assert sorted(set(lvl for lvl, _, _ in grid)) == ["0", "1", "4"]
    assert grid[-1][0] == "4"   # today, 25 submissions
    assert grid[-2][0] == "1"   # yesterday, 3


def test_heatmap_carries_both_github_palettes():
    svg = render_heatmap({}, TODAY, weeks=2)
    assert "#216e39" in svg and "#39d353" in svg
    assert "prefers-color-scheme:dark" in svg
    assert ">Less<" in svg and ">More<" in svg


def test_heatmap_labels_months_and_weekdays():
    svg = render_heatmap({}, TODAY, weeks=53)
    for label in ("Oct", "Jan", "Sep", "Mon", "Wed", "Fri"):
        assert f">{label}<" in svg


def test_block_embeds_the_heatmap_with_a_dated_url():
    s = compute(days_from((27, 1), (28, 2), (29, 4)), TODAY)
    block = render_block(s, TODAY, weeks=4, profile="https://example.com/u/x/",
                         heatmap_url="https://example.com/h.svg")
    assert "**3 day streak**" in block
    assert "https://example.com/u/x/" in block
    assert "(https://example.com/h.svg?v=2026-09-29)" in block
    assert "**7** submissions in the last 4 weeks" in block
    assert "```" not in block


def test_build_returns_the_heatmap_alongside_the_block():
    client = FakeClient({2026: {dt.date(2026, 9, 29): 2}})
    stats, block, svg = build(client, "x", today=TODAY, weeks=2, heatmap_url="u.svg")
    assert stats.current == 1
    assert "u.svg?v=2026-09-29" in block
    assert svg.startswith("<svg") and 'class="l1"' in svg


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


def test_active_streak_gets_a_fire():
    s = compute(days_from((27, 1), (28, 1), (29, 1)), TODAY)
    assert "\U0001F525" in render_block(s, TODAY, weeks=2)


def test_a_broken_streak_gets_no_fire():
    s = compute(days_from((20, 1)), TODAY)
    assert "\U0001F525" not in render_block(s, TODAY, weeks=2)


def test_remove_block_strips_the_region_and_its_markers():
    from leetcode_sync.streak import remove_block
    original = f"banner\n\n{START}\nSTREAK\n{END}\n"
    out = remove_block(original, START, END)
    assert out == "banner\n"
    assert START not in out and END not in out


def test_remove_block_is_a_no_op_without_markers():
    from leetcode_sync.streak import remove_block
    assert remove_block("banner\n", START, END) == "banner\n"


def test_apply_then_remove_round_trips():
    from leetcode_sync.streak import remove_block
    original = "banner\n"
    assert remove_block(apply_block(original, "X", START, END), START, END) == original
