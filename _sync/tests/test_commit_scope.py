import subprocess
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.sync import SyncReport, commit, stage_owned


def git(repo, *args):
    return subprocess.run(["git", "-C", str(repo), *args],
                          capture_output=True, text=True, check=False)


def make_repo(tmp_path):
    repo = tmp_path / "repo"
    (repo / "_sync" / "leetcode_sync").mkdir(parents=True)
    (repo / "array" / "0001-two-sum").mkdir(parents=True)
    (repo / "README.md").write_text("root\n", encoding="utf-8")
    (repo / "array" / "0001-two-sum" / "solution.py").write_text("v1\n", encoding="utf-8")
    (repo / "_sync" / "leetcode_sync" / "sync.py").write_text("# tool\n", encoding="utf-8")
    (repo / "_sync" / ".sync_state.json").write_text("{}\n", encoding="utf-8")
    git(repo, "init", "-q", "-b", "main")
    git(repo, "add", "-A")
    git(repo, "-c", "user.name=t", "-c", "user.email=t@t", "commit", "-qm", "seed")
    return repo


def staged(repo):
    return set(git(repo, "diff", "--cached", "--name-only").stdout.split())


def unstaged(repo):
    return set(git(repo, "diff", "--name-only").stdout.split())


def test_tool_edits_are_never_staged(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "array" / "0001-two-sum" / "solution.py").write_text("v2\n", encoding="utf-8")
    (repo / "_sync" / "leetcode_sync" / "sync.py").write_text("# WORK IN PROGRESS\n",
                                                              encoding="utf-8")
    stage_owned(repo)
    assert "array/0001-two-sum/solution.py" in staged(repo)
    assert "_sync/leetcode_sync/sync.py" not in staged(repo)
    assert "_sync/leetcode_sync/sync.py" in unstaged(repo)


def test_state_files_are_staged_despite_living_under_sync(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "_sync" / ".sync_state.json").write_text('{"n": 1}\n', encoding="utf-8")
    stage_owned(repo)
    assert "_sync/.sync_state.json" in staged(repo)


def test_new_and_deleted_topic_folders_are_both_picked_up(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "tree" / "0104-max-depth").mkdir(parents=True)
    (repo / "tree" / "0104-max-depth" / "solution.py").write_text("new\n", encoding="utf-8")
    (repo / "array" / "0001-two-sum" / "solution.py").unlink()
    stage_owned(repo)
    names = staged(repo)
    assert "tree/0104-max-depth/solution.py" in names
    assert "array/0001-two-sum/solution.py" in names


def test_gitignore_edits_are_left_alone(tmp_path):
    repo = make_repo(tmp_path)
    (repo / ".gitignore").write_text("*.tmp\n", encoding="utf-8")
    stage_owned(repo)
    assert ".gitignore" not in staged(repo)


def test_commit_leaves_a_dirty_tool_tree_uncommitted(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "array" / "0001-two-sum" / "solution.py").write_text("v2\n", encoding="utf-8")
    (repo / "_sync" / "leetcode_sync" / "sync.py").write_text("# WIP\n", encoding="utf-8")

    report = SyncReport(written=["array/0001-two-sum/solution.py"])
    assert commit(report, repo_root=repo, push=False, log=lambda *a: None) is True

    files = set(git(repo, "show", "--stat", "--name-only", "--format=", "HEAD").stdout.split())
    assert "array/0001-two-sum/solution.py" in files
    assert "_sync/leetcode_sync/sync.py" not in files
    assert "_sync/leetcode_sync/sync.py" in unstaged(repo)  # still yours to commit


def test_commit_is_skipped_when_only_unowned_files_changed(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "_sync" / "leetcode_sync" / "sync.py").write_text("# WIP\n", encoding="utf-8")
    messages = []
    # force=True is what a streak-only run passes; there is still nothing of ours.
    assert commit(SyncReport(), repo_root=repo, push=False, force=True,
                  log=messages.append) is False
    assert any("nothing to commit" in m.lower() for m in messages)


# --- commit subjects come from what is staged, not from the run's report -------

from leetcode_sync.sync import describe_staged


def add_solution(repo, topic, problem, body="x\n"):
    folder = repo / topic / problem
    folder.mkdir(parents=True, exist_ok=True)
    (folder / "solution.py").write_text(body, encoding="utf-8")


def test_subject_counts_new_solutions(tmp_path):
    repo = make_repo(tmp_path)
    add_solution(repo, "tree", "0104-max-depth")
    add_solution(repo, "graph", "0200-islands")
    stage_owned(repo)
    assert describe_staged(repo) == "Sync 2 LeetCode solutions"


def test_subject_names_a_single_solution(tmp_path):
    repo = make_repo(tmp_path)
    add_solution(repo, "tree", "0104-max-depth")
    stage_owned(repo)
    assert describe_staged(repo) == "Add solution: tree/0104-max-depth/solution.py"


def test_subject_for_a_readme_only_change_is_the_streak(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "README.md").write_text("streak moved\n", encoding="utf-8")
    stage_owned(repo)
    assert describe_staged(repo) == "Update LeetCode streak"


def test_moving_a_problem_between_topics_is_a_refile_not_new_work(tmp_path):
    repo = make_repo(tmp_path)
    (repo / "hash-table").mkdir()
    (repo / "array" / "0001-two-sum").rename(repo / "hash-table" / "0001-two-sum")
    stage_owned(repo)
    assert describe_staged(repo) == "Re-file 1 problem under new topics"


def test_work_stranded_by_a_killed_run_is_committed_under_its_real_name(tmp_path):
    # The run that wrote these files died before committing; the next run's own
    # report is empty, but the commit must still say what it contains.
    repo = make_repo(tmp_path)
    add_solution(repo, "tree", "0104-max-depth")
    add_solution(repo, "graph", "0200-islands")
    add_solution(repo, "stack", "0020-valid-parens")
    assert commit(SyncReport(), repo_root=repo, push=False, force=True,
                  log=lambda *a: None) is True
    subject = git(repo, "log", "-1", "--format=%s").stdout.strip()
    assert subject == "Sync 3 LeetCode solutions"
