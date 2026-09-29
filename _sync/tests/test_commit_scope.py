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
