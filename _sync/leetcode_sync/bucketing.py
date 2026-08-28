"""Deciding which topic folder each problem belongs to.

Ranking alone produces precise labels but a cluttered root: a problem tagged
``eulerian-circuit`` earns its own folder even when it is the only one. So after
ranking, topics that stayed below a minimum size are dissolved and their
problems fall through to their next-best tag.
"""

from __future__ import annotations

from collections import Counter

from .api import Question
from .config import topic_rank

MISCELLANEOUS = ("Miscellaneous", "miscellaneous")


def candidates_for(question: Question) -> list[tuple[str, str]]:
    """This problem's tags, best first. Ties keep LeetCode's own ordering."""
    if not question.topics:
        return [MISCELLANEOUS]
    ranked = sorted(
        enumerate(question.topics),
        key=lambda pair: (topic_rank(pair[1][1]), pair[0]),
    )
    return [topic for _, topic in ranked]


def assign_topics(questions: list[Question], min_size: int = 2) -> dict[str, tuple[str, str]]:
    """Map each problem slug to the topic folder it should live in.

    Problems only ever fall *down* their candidate list, so this terminates: a
    problem whose tags are exhausted keeps its last one, which is why a genuinely
    unique topic can still end up below ``min_size``.
    """
    candidates = {q.slug: candidates_for(q) for q in questions}
    position = {slug: 0 for slug in candidates}

    while True:
        assignment = {slug: candidates[slug][position[slug]] for slug in candidates}
        counts = Counter(topic for _, topic in assignment.values())

        dissolved = False
        # Smallest topics first, alphabetical within a size, so runs are stable.
        for topic in sorted(counts, key=lambda t: (counts[t], t)):
            if counts[topic] >= min_size:
                break
            movable = [
                slug
                for slug, (_, assigned) in assignment.items()
                if assigned == topic and position[slug] + 1 < len(candidates[slug])
            ]
            if not movable:
                continue  # nothing else to call these problems; the folder stays
            for slug in movable:
                position[slug] += 1
            dissolved = True
            break

        if not dissolved:
            return assignment
