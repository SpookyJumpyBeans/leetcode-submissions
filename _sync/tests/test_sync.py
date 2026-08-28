import json
import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import Question, Submission, _slug_from_url
from leetcode_sync.state import ProblemCache, SolutionIndex, SyncState
from leetcode_sync.sync import run_sync, select_latest_accepted

QUESTIONS = {
    "two-sum": Question("1", "Two Sum", "two-sum", "Easy", False,
                        (("Array", "array"), ("Hash Table", "hash-table"))),
    "3sum": Question("15", "3Sum", "3sum", "Medium", False,
                     (("Two Pointers", "two-pointers"),)),
}


def sub(id_, slug, lang, ts, status="Accepted", code="x = 1"):
    return Submission(id=id_, title=slug, slug=slug, lang=lang, lang_name=lang,
                      status=status, timestamp=ts, runtime="1 ms", memory="1 MB", code=code)


class FakeClient:
    """Serves a canned history newest-first, the way LeetCode does."""

    def __init__(self, submissions, questions=None):
        self.submissions = submissions
        self.questions = questions if questions is not None else QUESTIONS
        self.question_calls = 0

    def iter_submissions(self, stop_at_id=None, stop_before_timestamp=None):
        for submission in self.submissions:
            if stop_at_id is not None and submission.id == stop_at_id:
                return
            if stop_before_timestamp is not None and submission.timestamp <= stop_before_timestamp:
                return
            yield submission

    def get_question(self, slug):
        self.question_calls += 1
        return self.questions.get(slug)


def test_slug_from_url():
    assert _slug_from_url("/problems/two-sum/submissions/123/") == "two-sum"
    assert _slug_from_url("") == ""


def test_submission_from_payload_falls_back_to_url_slug():
    payload = {"id": "7", "url": "/problems/3sum/submissions/7/", "status_display": "Accepted",
               "timestamp": "10", "lang": "python3", "code": "pass"}
    submission = Submission.from_payload(payload)
    assert submission.slug == "3sum"
    assert submission.id == 7 and submission.timestamp == 10
    assert submission.accepted


def test_select_keeps_newest_accepted_per_language():
    history = [
        sub(5, "two-sum", "python3", 500),
        sub(4, "two-sum", "python3", 400),
        sub(3, "two-sum", "java", 300),
        sub(2, "3sum", "python3", 200, status="Wrong Answer"),
    ]
    keepers = select_latest_accepted(history)
    assert set(keepers) == {("two-sum", "python3"), ("two-sum", "java")}
    assert keepers[("two-sum", "python3")].id == 5


def test_select_ignores_submissions_without_a_slug():
    assert select_latest_accepted([sub(1, "", "python3", 100)]) == {}


def _fixture_paths(tmp_path):
    return (
        SyncState(path=tmp_path / "state.json"),
        ProblemCache(tmp_path / "cache.json"),
        SolutionIndex(tmp_path / "index.json"),
    )


def test_run_sync_writes_tree_and_readmes(tmp_path):
    repo = tmp_path / "repo"
    state, cache, index = _fixture_paths(tmp_path)
    client = FakeClient([
        sub(9, "two-sum", "python3", 900, code="class Solution: pass"),
        sub(8, "two-sum", "java", 800),
        sub(7, "3sum", "python3", 700, status="Time Limit Exceeded"),
    ])

    report = run_sync(client, state, repo_root=repo, cache=cache, index=index, log=lambda *a: None)

    assert report.fetched == 3
    assert report.accepted == 2
    assert (repo / "array" / "0001-two-sum" / "solution.py").exists()
    assert (repo / "array" / "0001-two-sum" / "solution.java").exists()
    assert not (repo / "two-pointers").exists()

    problem_readme = (repo / "array" / "0001-two-sum" / "README.md").read_text(encoding="utf-8")
    assert "solution.java" in problem_readme
    root_readme = (repo / "README.md").read_text(encoding="utf-8")
    assert "**1 problems solved**" in root_readme

    assert state.newest_submission_id == 9
    assert state.newest_timestamp == 900
    assert json.loads((tmp_path / "state.json").read_text())["problems_synced"] == 1


def test_run_sync_is_incremental_on_the_second_pass(tmp_path):
    repo = tmp_path / "repo"
    state, cache, index = _fixture_paths(tmp_path)
    history = [sub(9, "two-sum", "python3", 900)]
    run_sync(FakeClient(history), state, repo_root=repo, cache=cache, index=index,
             log=lambda *a: None)

    # A newer submission for a different problem arrives.
    history.insert(0, sub(11, "3sum", "cpp", 1100, code="int main(){}"))
    state2 = SyncState.load(tmp_path / "state.json")
    client = FakeClient(history)
    report = run_sync(client, state2, repo_root=repo,
                      cache=ProblemCache(tmp_path / "cache.json"),
                      index=SolutionIndex(tmp_path / "index.json"), log=lambda *a: None)

    assert report.fetched == 1  # stopped at the already-seen submission
    assert (repo / "two-pointers" / "0015-3sum" / "solution.cpp").exists()
    # The earlier problem survives in the regenerated root README.
    root_readme = (repo / "README.md").read_text(encoding="utf-8")
    assert "Two Sum" in root_readme and "3Sum" in root_readme
    assert "**2 problems solved**" in root_readme
    assert client.question_calls == 1  # two-sum came from the cache


def test_run_sync_overwrites_with_a_newer_accepted_solution(tmp_path):
    repo = tmp_path / "repo"
    state, cache, index = _fixture_paths(tmp_path)
    run_sync(FakeClient([sub(1, "two-sum", "python3", 100, code="old")]), state,
             repo_root=repo, cache=cache, index=index, log=lambda *a: None)
    solution = repo / "array" / "0001-two-sum" / "solution.py"
    assert "old" in solution.read_text(encoding="utf-8")

    state2 = SyncState.load(tmp_path / "state.json")
    run_sync(FakeClient([sub(2, "two-sum", "python3", 200, code="new")]), state2,
             repo_root=repo, cache=ProblemCache(tmp_path / "cache.json"),
             index=SolutionIndex(tmp_path / "index.json"), log=lambda *a: None)
    assert "new" in solution.read_text(encoding="utf-8")


def test_run_sync_dry_run_writes_nothing(tmp_path):
    repo = tmp_path / "repo"
    state, cache, index = _fixture_paths(tmp_path)
    report = run_sync(FakeClient([sub(1, "two-sum", "python3", 100)]), state, repo_root=repo,
                      cache=cache, index=index, dry_run=True, log=lambda *a: None)
    assert report.written == ["array/0001-two-sum/solution.py"]
    assert not repo.exists()
    assert state.newest_submission_id is None


def test_run_sync_skips_problems_without_metadata(tmp_path):
    repo = tmp_path / "repo"
    state, cache, index = _fixture_paths(tmp_path)
    client = FakeClient([sub(1, "ghost-problem", "python3", 100)], questions={})
    report = run_sync(client, state, repo_root=repo, cache=cache, index=index,
                      log=lambda *a: None)
    assert report.skipped_unknown == ["ghost-problem"]
    assert report.written == []


def test_run_sync_reports_no_new_submissions(tmp_path):
    state, cache, index = _fixture_paths(tmp_path)
    report = run_sync(FakeClient([]), state, repo_root=tmp_path / "repo", cache=cache,
                      index=index, log=lambda *a: None)
    assert report.fetched == 0 and not report.changed
