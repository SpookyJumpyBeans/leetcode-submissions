import subprocess
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import Question, Submission
from leetcode_sync.neetcode import (
    ALIASES, discover, find_problems_root, latest_per_language, to_submission,
)
from leetcode_sync.state import SolutionIndex
from leetcode_sync.sync import run_import_neetcode

QUESTIONS = {
    "unique-paths": Question("62", "Unique Paths", "unique-paths", "Medium", False,
                             (("Math", "math"), ("Dynamic Programming", "dynamic-programming"))),
    "coin-change": Question("322", "Coin Change", "coin-change", "Medium", False,
                            (("Array", "array"), ("Dynamic Programming", "dynamic-programming"))),
    "two-sum": Question("1", "Two Sum", "two-sum", "Easy", False,
                        (("Array", "array"), ("Hash Table", "hash-table"))),
}


class FakeClient:
    def __init__(self):
        self.calls = 0

    def get_question(self, slug):
        self.calls += 1
        return QUESTIONS.get(slug)


def build_neetcode_repo(tmp_path, layout):
    """layout: {folder: {filename: contents}}"""
    root = tmp_path / "nc" / "Data Structures & Algorithms"
    for folder, files in layout.items():
        d = root / folder
        d.mkdir(parents=True)
        for name, contents in files.items():
            (d / name).write_text(contents, encoding="utf-8")
    repo = tmp_path / "nc"
    subprocess.run(["git", "-C", str(repo), "init", "-q"], check=False)
    return repo


def test_find_problems_root_uses_the_category_folder(tmp_path):
    repo = build_neetcode_repo(tmp_path, {"coin-change": {"submission-0.java": "x"}})
    assert find_problems_root(repo).name == "Data Structures & Algorithms"


def test_latest_per_language_keeps_the_highest_numbered_attempt(tmp_path):
    repo = build_neetcode_repo(tmp_path, {"coin-change": {
        "submission-0.java": "old", "submission-3.java": "new",
        "submission-1.cpp": "cpp-old", "submission-2.cpp": "cpp-new",
        "README.md": "ignore me",
    }})
    best = latest_per_language(repo / "Data Structures & Algorithms" / "coin-change")
    assert set(best) == {"java", "cpp"}
    assert best["java"].read_text(encoding="utf-8") == "new"
    assert best["cpp"].read_text(encoding="utf-8") == "cpp-new"


def test_discover_applies_the_alias_table(tmp_path):
    repo = build_neetcode_repo(tmp_path, {
        "count-paths": {"submission-0.java": "a"},
        "coin-change": {"submission-0.java": "b"},
    })
    found = {s.neetcode_slug: s.leetcode_slug for s in discover(repo)}
    assert found == {"count-paths": "unique-paths", "coin-change": "coin-change"}


def test_every_alias_target_differs_from_its_key():
    assert all(k != v for k, v in ALIASES.items())


def test_to_submission_marks_the_origin(tmp_path):
    repo = build_neetcode_repo(tmp_path, {"coin-change": {"submission-0.java": "class S {}"}})
    solution = discover(repo)[0]
    submission = to_submission(solution, QUESTIONS["coin-change"])
    assert submission.source == "neetcode"
    assert submission.accepted and submission.code == "class S {}"
    assert submission.runtime == "" and submission.memory == ""


def test_import_writes_solutions_and_skips_problems_already_on_leetcode(tmp_path):
    repo_root = tmp_path / "repo"
    index = SolutionIndex(tmp_path / "index.json")
    # Two Sum is already in the LeetCode tree.
    index.record(QUESTIONS["two-sum"], Submission(
        1, "Two Sum", "two-sum", "java", "Java", "Accepted", 100, "1 ms", "1 MB", "lc code"))

    nc = build_neetcode_repo(tmp_path, {
        "coin-change": {"submission-0.java": "nc coin"},
        "two-sum": {"submission-0.java": "nc two sum"},
    })
    report = run_import_neetcode(nc, FakeClient(), repo_root=repo_root, index=index,
                                 min_size=1, log=lambda *a: None)

    assert len(report.imported) == 1
    assert report.skipped_existing == ["two-sum"]
    written = repo_root / "dynamic-programming" / "0322-coin-change" / "solution.java"
    assert "nc coin" in written.read_text(encoding="utf-8")
    assert "Accepted on NeetCode" in written.read_text(encoding="utf-8")
    # Its README is still regenerated, but no NeetCode solution file was written.
    assert not (repo_root / "hash-table" / "0001-two-sum" / "solution.java").exists()


def test_include_existing_imports_them_too(tmp_path):
    repo_root = tmp_path / "repo"
    index = SolutionIndex(tmp_path / "index.json")
    index.record(QUESTIONS["two-sum"], Submission(
        1, "Two Sum", "two-sum", "cpp", "C++", "Accepted", 100, "1 ms", "1 MB", "lc code"))
    nc = build_neetcode_repo(tmp_path, {"two-sum": {"submission-0.java": "nc two sum"}})
    report = run_import_neetcode(nc, FakeClient(), repo_root=repo_root, index=index,
                                 include_existing=True, min_size=1, log=lambda *a: None)
    assert len(report.imported) == 1 and report.skipped_existing == []


def test_import_reports_slugs_it_cannot_resolve(tmp_path):
    repo_root = tmp_path / "repo"
    nc = build_neetcode_repo(tmp_path, {"a-neetcode-only-problem": {"submission-0.java": "x"}})
    report = run_import_neetcode(nc, FakeClient(), repo_root=repo_root,
                                 index=SolutionIndex(tmp_path / "i.json"),
                                 min_size=1, log=lambda *a: None)
    assert report.unresolved == ["a-neetcode-only-problem"]
    assert report.imported == []


def test_dry_run_writes_nothing(tmp_path):
    repo_root = tmp_path / "repo"
    nc = build_neetcode_repo(tmp_path, {"coin-change": {"submission-0.java": "x"}})
    report = run_import_neetcode(nc, FakeClient(), repo_root=repo_root,
                                 index=SolutionIndex(tmp_path / "i.json"),
                                 dry_run=True, min_size=1, log=lambda *a: None)
    assert len(report.imported) == 1
    assert not repo_root.exists()


def test_a_leetcode_submission_supersedes_an_imported_one(tmp_path):
    index = SolutionIndex(tmp_path / "index.json")
    nc_sub = Submission(0, "Coin Change", "coin-change", "java", "java", "Accepted",
                        9_000_000_000, "", "", "nc", source="neetcode")
    index.record(QUESTIONS["coin-change"], nc_sub)
    # Even with an older timestamp, a real LeetCode submission wins.
    assert index.has_newer("coin-change", "java", 100) is False


def make_origin(tmp_path, contents="v1"):
    """A local git repo standing in for the NeetCode remote."""
    origin = tmp_path / "origin"
    (origin / "Data Structures & Algorithms" / "coin-change").mkdir(parents=True)
    (origin / "Data Structures & Algorithms" / "coin-change" / "submission-0.java").write_text(
        contents, encoding="utf-8")
    for args in (["init", "-q", "-b", "main"], ["add", "-A"],
                 ["-c", "user.name=t", "-c", "user.email=t@t", "commit", "-qm", "seed"]):
        subprocess.run(["git", "-C", str(origin), *args], check=True,
                       capture_output=True)
    return origin


def test_clone_url_forms():
    from leetcode_sync.neetcode import clone_url
    assert clone_url("owner/name") == "https://github.com/owner/name.git"
    assert clone_url("https://example.com/x.git") == "https://example.com/x.git"
    assert clone_url("git@github.com:owner/name.git") == "git@github.com:owner/name.git"


def test_ensure_clone_clones_then_fast_forwards(tmp_path):
    from leetcode_sync.neetcode import ensure_clone
    origin = make_origin(tmp_path)
    dest = tmp_path / "clone"

    assert ensure_clone(str(origin), dest, log=lambda *a: None) == dest
    solution = dest / "Data Structures & Algorithms" / "coin-change" / "submission-0.java"
    assert solution.read_text(encoding="utf-8") == "v1"

    # A new commit upstream is picked up by the second call.
    solution_upstream = origin / "Data Structures & Algorithms" / "coin-change" / "submission-0.java"
    solution_upstream.write_text("v2", encoding="utf-8")
    subprocess.run(["git", "-C", str(origin), "add", "-A"], check=True, capture_output=True)
    subprocess.run(["git", "-C", str(origin), "-c", "user.name=t", "-c", "user.email=t@t",
                    "commit", "-qm", "update"], check=True, capture_output=True)

    assert ensure_clone(str(origin), dest, log=lambda *a: None) == dest
    assert solution.read_text(encoding="utf-8") == "v2"


def test_ensure_clone_returns_none_when_the_remote_is_unreachable(tmp_path):
    from leetcode_sync.neetcode import ensure_clone
    messages = []
    assert ensure_clone(str(tmp_path / "nope"), tmp_path / "dest", log=messages.append) is None
    assert any("Could not clone" in m for m in messages)


def test_ensure_clone_keeps_a_stale_clone_when_the_pull_fails(tmp_path):
    from leetcode_sync.neetcode import ensure_clone
    origin = make_origin(tmp_path)
    dest = tmp_path / "clone"
    ensure_clone(str(origin), dest, log=lambda *a: None)
    origin.rename(tmp_path / "origin-moved-away")  # remote vanishes

    messages = []
    assert ensure_clone(str(origin), dest, log=messages.append) == dest
    assert any("using the copy on disk" in m for m in messages)
    assert (dest / "Data Structures & Algorithms" / "coin-change" / "submission-0.java").exists()
