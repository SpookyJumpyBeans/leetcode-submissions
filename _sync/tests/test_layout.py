import sys
from datetime import datetime, timezone
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import Question, Submission
from leetcode_sync.layout import (
    placement_for,
    render_problem_readme,
    render_root_readme,
    render_solution,
    slugify,
    write_text,
)


def make_question(**overrides) -> Question:
    defaults = dict(
        frontend_id="1",
        title="Two Sum",
        slug="two-sum",
        difficulty="Easy",
        paid_only=False,
        topics=(("Array", "array"), ("Hash Table", "hash-table")),
    )
    defaults.update(overrides)
    return Question(**defaults)


def make_submission(**overrides) -> Submission:
    defaults = dict(
        id=100,
        title="Two Sum",
        slug="two-sum",
        lang="python3",
        lang_name="Python3",
        status="Accepted",
        timestamp=1700000000,
        runtime="52 ms",
        memory="16.9 MB",
        code="class Solution:\r\n    pass\r\n",
    )
    defaults.update(overrides)
    return Submission(**defaults)


def test_slugify_collapses_punctuation():
    assert slugify("Hash Table") == "hash-table"
    assert slugify("Divide & Conquer") == "divide-conquer"
    assert slugify("!!!") == "misc"


def test_placement_uses_primary_topic_and_padded_number():
    # Tagged [Array, Hash Table]: the specific tag wins over the catch-all.
    placement = placement_for(make_question(), make_submission())
    assert placement.relative_path == "hash-table/0001-two-sum/solution.py"


def test_placement_falls_back_when_frontend_id_is_not_numeric():
    question = make_question(frontend_id="LCP 01", slug="guess-numbers")
    placement = placement_for(question, make_submission())
    assert placement.problem_dir == "guess-numbers"


def test_placement_without_topics_lands_in_miscellaneous():
    placement = placement_for(make_question(topics=()), make_submission())
    assert placement.topic_dir == "miscellaneous"


def test_language_extension_follows_the_submission():
    placement = placement_for(make_question(), make_submission(lang="cpp"))
    assert placement.filename == "solution.cpp"
    placement = placement_for(make_question(), make_submission(lang="mysql"))
    assert placement.filename == "solution.sql"


def test_render_solution_adds_header_and_normalises_newlines():
    rendered = render_solution(make_question(), make_submission())
    assert rendered.startswith("# 1. Two Sum\n")
    assert "https://leetcode.com/problems/two-sum/" in rendered
    assert "Easy | Python3 | Accepted 2023-11-14" in rendered
    assert "Runtime 52 ms | Memory 16.9 MB" in rendered
    assert "\r" not in rendered
    assert rendered.endswith("class Solution:\n    pass\n")


def test_render_solution_uses_the_right_comment_token():
    assert render_solution(make_question(), make_submission(lang="java")).startswith("//")
    assert render_solution(make_question(), make_submission(lang="mysql")).startswith("--")


def test_problem_readme_lists_every_language():
    submissions = [make_submission(), make_submission(lang="java", runtime="3 ms")]
    readme = render_problem_readme(make_question(), submissions)
    assert "# 1. Two Sum" in readme
    assert "[solution.py](solution.py)" in readme
    assert "[solution.java](solution.java)" in readme
    assert "Array, Hash Table" in readme


def test_root_readme_groups_by_topic_and_counts_difficulty():
    entries = [
        (make_question(), [make_submission()]),
        (
            make_question(frontend_id="15", title="3Sum", slug="3sum", difficulty="Medium",
                          topics=(("Two Pointers", "two-pointers"),)),
            [make_submission(slug="3sum", lang="cpp")],
        ),
    ]
    readme = render_root_readme(entries, generated_at=datetime(2026, 1, 2, tzinfo=timezone.utc))
    assert "**2 problems solved**" in readme
    assert "1 Easy" in readme and "1 Medium" in readme
    assert "## Hash Table (1)" in readme
    assert "## Two Pointers (1)" in readme
    assert "(hash-table/0001-two-sum)" in readme
    assert "Last synced 2026-01-02" in readme


def test_write_text_skips_identical_content(tmp_path):
    target = tmp_path / "nested" / "solution.py"
    assert write_text(target, "print(1)\n") is True
    assert write_text(target, "print(1)\n") is False
    assert write_text(target, "print(2)\n") is True
    assert target.read_text(encoding="utf-8") == "print(2)\n"
