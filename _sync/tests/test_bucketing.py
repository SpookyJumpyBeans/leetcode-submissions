import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.api import Question
from leetcode_sync.bucketing import assign_topics, candidates_for


def q(slug, *tag_slugs):
    topics = tuple((s.replace("-", " ").title(), s) for s in tag_slugs)
    return Question("1", slug.title(), slug, "Medium", False, topics)


def slugs(assignment):
    return {slug: topic[1] for slug, topic in assignment.items()}


def test_candidates_are_ordered_best_first():
    assert [t[1] for t in candidates_for(q("x", "array", "trie", "hash-table"))] == [
        "trie", "hash-table", "array"]


def test_candidates_of_an_untagged_problem():
    assert candidates_for(q("x")) == [("Miscellaneous", "miscellaneous")]


def test_min_size_one_keeps_every_specific_label():
    problems = [q("a", "eulerian-circuit", "graph"), q("b", "graph", "array")]
    assert slugs(assign_topics(problems, min_size=1)) == {"a": "eulerian-circuit", "b": "graph"}


def test_a_lone_specific_topic_dissolves_into_its_next_tag():
    problems = [q("a", "eulerian-circuit", "graph"), q("b", "graph", "array"),
                q("c", "graph", "array")]
    # eulerian-circuit has one member, so 'a' falls through to graph, which has three.
    assert slugs(assign_topics(problems, min_size=2))["a"] == "graph"


def test_a_topic_that_meets_the_threshold_survives():
    problems = [q("a", "trie", "string"), q("b", "trie", "string")]
    assert slugs(assign_topics(problems, min_size=2)) == {"a": "trie", "b": "trie"}


def test_a_problem_with_nowhere_to_fall_keeps_its_only_tag():
    problems = [q("a", "eulerian-circuit"), q("b", "graph", "array"), q("c", "graph", "array")]
    assert slugs(assign_topics(problems, min_size=2))["a"] == "eulerian-circuit"


def test_dissolving_can_cascade():
    # 'a' prefers segment-tree (alone), falls to trie (alone with 'a' gone), then string.
    problems = [q("a", "segment-tree", "trie", "string"), q("b", "string", "array"),
                q("c", "string", "array")]
    assert slugs(assign_topics(problems, min_size=2))["a"] == "string"


def test_assignment_is_deterministic():
    problems = [q("a", "trie", "array"), q("b", "union-find", "array"),
                q("c", "array"), q("d", "array")]
    first = slugs(assign_topics(problems, min_size=2))
    assert first == slugs(assign_topics(list(reversed(problems)), min_size=2))
    assert first == {"a": "array", "b": "array", "c": "array", "d": "array"}


def test_no_problems_is_not_an_error():
    assert assign_topics([], min_size=2) == {}
