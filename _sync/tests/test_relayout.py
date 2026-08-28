import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import Question, Submission
from leetcode_sync.config import topic_rank
from leetcode_sync.relayout import find_problem_dir, plan_moves, prune_empty_topic_dirs
from leetcode_sync.state import SolutionIndex
from leetcode_sync.sync import run_relayout


def q(frontend_id, slug, *tag_slugs, difficulty="Medium"):
    topics = tuple((s.replace("-", " ").title(), s) for s in tag_slugs)
    return Question(frontend_id, slug.title(), slug, difficulty, False, topics)


def s(slug, lang="python3", ts=100):
    return Submission(1, slug, slug, lang, lang, "Accepted", ts, "1 ms", "1 MB", "code")


def test_generic_tags_rank_worse_than_specific_ones():
    assert topic_rank("array") > topic_rank("dynamic-programming")
    assert topic_rank("string") > topic_rank("trie")
    assert topic_rank("hash-table") > topic_rank("linked-list")
    assert topic_rank("unheard-of-tag") == 50


def test_primary_topic_prefers_the_specific_tag():
    assert q("1", "x", "array", "dynamic-programming").primary_topic[1] == "dynamic-programming"
    assert q("1", "x", "array", "hash-table").primary_topic[1] == "hash-table"
    assert q("1", "x", "string", "trie", "design").primary_topic[1] == "trie"


def test_primary_topic_keeps_leetcode_order_on_ties():
    # linked-list and design share a rank; the earlier tag wins.
    assert q("1", "x", "hash-table", "linked-list", "design").primary_topic[1] == "linked-list"


def test_array_still_wins_when_it_is_the_only_tag():
    assert q("1", "x", "array").primary_topic[1] == "array"


def test_no_tags_falls_back_to_miscellaneous():
    assert q("1", "x").primary_topic[1] == "miscellaneous"


def build_repo(tmp_path, placements):
    """Create a repo tree at the given topic/problem locations."""
    for topic, problem in placements:
        folder = tmp_path / topic / problem
        folder.mkdir(parents=True)
        (folder / "solution.py").write_text("code\n", encoding="utf-8")
    return tmp_path


def test_find_problem_dir_looks_across_topics(tmp_path):
    build_repo(tmp_path, [("array", "0015-3sum")])
    assert find_problem_dir(tmp_path, "0015-3sum").name == "0015-3sum"
    assert find_problem_dir(tmp_path, "0001-two-sum") is None


def test_plan_moves_only_lists_problems_in_the_wrong_place(tmp_path):
    repo = build_repo(tmp_path, [("array", "0053-maximum-subarray"), ("trie", "0208-trie")])
    index = SolutionIndex(tmp_path / "index.json")
    index.record(q("53", "maximum-subarray", "array", "dynamic-programming"),
                 s("maximum-subarray"))
    index.record(q("208", "trie", "trie", "string"), s("trie"))

    moves = plan_moves(repo, index, min_size=1)
    assert len(moves) == 1
    assert moves[0].source == "array/0053-maximum-subarray"
    assert moves[0].destination == "dynamic-programming/0053-maximum-subarray"


def test_run_relayout_moves_files_and_prunes_the_empty_folder(tmp_path):
    repo = build_repo(tmp_path, [("array", "0053-maximum-subarray")])
    index = SolutionIndex(tmp_path / "index.json")
    index.record(q("53", "maximum-subarray", "array", "dynamic-programming"),
                 s("maximum-subarray"))

    run_relayout(repo_root=repo, index=index, min_size=1, log=lambda *a: None)

    moved = repo / "dynamic-programming" / "0053-maximum-subarray"
    assert (moved / "solution.py").read_text(encoding="utf-8") == "code\n"
    assert (moved / "README.md").exists()
    assert not (repo / "array").exists()
    assert "dynamic-programming/0053-maximum-subarray" in (repo / "README.md").read_text(encoding="utf-8")


def test_run_relayout_dry_run_changes_nothing(tmp_path):
    repo = build_repo(tmp_path, [("array", "0053-maximum-subarray")])
    index = SolutionIndex(tmp_path / "index.json")
    index.record(q("53", "maximum-subarray", "array", "dynamic-programming"),
                 s("maximum-subarray"))

    moves = run_relayout(repo_root=repo, index=index, dry_run=True, min_size=1, log=lambda *a: None)
    assert len(moves) == 1
    assert (repo / "array" / "0053-maximum-subarray" / "solution.py").exists()
    assert not (repo / "dynamic-programming").exists()


def test_run_relayout_is_idempotent(tmp_path):
    repo = build_repo(tmp_path, [("array", "0053-maximum-subarray")])
    index = SolutionIndex(tmp_path / "index.json")
    index.record(q("53", "maximum-subarray", "array", "dynamic-programming"),
                 s("maximum-subarray"))
    run_relayout(repo_root=repo, index=index, min_size=1, log=lambda *a: None)
    assert run_relayout(repo_root=repo, index=index, min_size=1, log=lambda *a: None) == []


def test_prune_leaves_the_sync_folder_alone(tmp_path):
    (tmp_path / "_sync").mkdir()
    (tmp_path / "empty-topic").mkdir()
    removed = prune_empty_topic_dirs(tmp_path)
    assert removed == ["empty-topic"]
    assert (tmp_path / "_sync").exists()
