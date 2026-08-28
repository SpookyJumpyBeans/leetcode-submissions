"""Where files go on disk, and what goes in them."""

from __future__ import annotations

import re
from dataclasses import dataclass
from datetime import datetime, timezone
from pathlib import Path

from .api import Question, Submission
from .config import comment_token, lang_display, lang_extension

_UNSAFE = re.compile(r"[^a-z0-9]+")


def slugify(value: str) -> str:
    return _UNSAFE.sub("-", value.lower()).strip("-") or "misc"


@dataclass(frozen=True)
class Placement:
    """The resolved on-disk location for one solution."""

    topic_dir: str
    problem_dir: str
    filename: str

    @property
    def relative_path(self) -> str:
        return f"{self.topic_dir}/{self.problem_dir}/{self.filename}"


def placement_for(question: Question, submission: Submission) -> Placement:
    topic_name, topic_slug = question.primary_topic
    extension = lang_extension(submission.lang)
    return Placement(
        topic_dir=slugify(topic_slug or topic_name),
        problem_dir=f"{int(question.frontend_id):04d}-{question.slug}"
        if question.frontend_id.isdigit()
        else question.slug,
        filename=f"solution.{extension}",
    )


def format_timestamp(epoch: int) -> str:
    return datetime.fromtimestamp(epoch, tz=timezone.utc).strftime("%Y-%m-%d")


def render_solution(question: Question, submission: Submission) -> str:
    """The submitted code with a short provenance header comment."""
    extension = lang_extension(submission.lang)
    token = comment_token(extension)
    header = [
        f"{token} {question.frontend_id}. {question.title}",
        f"{token} https://leetcode.com/problems/{question.slug}/",
        f"{token} {question.difficulty} | {lang_display(submission.lang, submission.lang_name)}"
        f" | Accepted {format_timestamp(submission.timestamp)}",
    ]
    stats = [part for part in (submission.runtime, submission.memory) if part and part != "N/A"]
    if stats:
        header.append(f"{token} Runtime {stats[0]}" + (f" | Memory {stats[1]}" if len(stats) > 1 else ""))
    body = submission.code.replace("\r\n", "\n").rstrip("\n")
    return "\n".join(header) + "\n\n" + body + "\n"


def render_problem_readme(question: Question, submissions: list[Submission]) -> str:
    """A small index for one problem folder."""
    topic_names = ", ".join(name for name, _ in question.topics) or "-"
    lines = [
        f"# {question.frontend_id}. {question.title}",
        "",
        f"[Problem on LeetCode](https://leetcode.com/problems/{question.slug}/)",
        "",
        f"- **Difficulty:** {question.difficulty}",
        f"- **Topics:** {topic_names}",
        "",
        "| Language | File | Runtime | Memory | Accepted |",
        "| --- | --- | --- | --- | --- |",
    ]
    for submission in sorted(submissions, key=lambda s: s.lang):
        filename = f"solution.{lang_extension(submission.lang)}"
        lines.append(
            f"| {lang_display(submission.lang, submission.lang_name)} "
            f"| [{filename}]({filename}) "
            f"| {submission.runtime or '-'} "
            f"| {submission.memory or '-'} "
            f"| {format_timestamp(submission.timestamp)} |"
        )
    lines.append("")
    return "\n".join(lines)


DIFFICULTY_ORDER = {"Easy": 0, "Medium": 1, "Hard": 2}


def render_root_readme(entries: list[tuple[Question, list[Submission]]],
                       generated_at: datetime | None = None) -> str:
    """The repo landing page: counts plus a full index grouped by topic."""
    generated_at = generated_at or datetime.now(timezone.utc)
    counts = {"Easy": 0, "Medium": 0, "Hard": 0}
    for question, _ in entries:
        if question.difficulty in counts:
            counts[question.difficulty] += 1

    languages: set[str] = set()
    for _, submissions in entries:
        for submission in submissions:
            languages.add(lang_display(submission.lang, submission.lang_name))

    lines = [
        "# LeetCode Submissions",
        "",
        "My accepted LeetCode solutions, synced automatically from my submission",
        "history. Problems are grouped by their primary topic tag.",
        "",
        f"**{len(entries)} problems solved** &nbsp;·&nbsp; "
        f"{counts['Easy']} Easy &nbsp;·&nbsp; {counts['Medium']} Medium &nbsp;·&nbsp; "
        f"{counts['Hard']} Hard",
        "",
        f"Languages: {', '.join(sorted(languages)) or '-'}",
        "",
        f"_Last synced {generated_at.strftime('%Y-%m-%d')} by [`_sync`](_sync/) "
        "([how it works](_sync/README.md))._",
        "",
    ]

    by_topic: dict[str, list[tuple[Question, list[Submission]]]] = {}
    for question, submissions in entries:
        topic_name, _ = question.primary_topic
        by_topic.setdefault(topic_name, []).append((question, submissions))

    for topic_name in sorted(by_topic):
        rows = sorted(
            by_topic[topic_name],
            key=lambda item: _sort_key(item[0]),
        )
        lines.append(f"## {topic_name} ({len(rows)})")
        lines.append("")
        lines.append("| # | Problem | Difficulty | Solutions |")
        lines.append("| --- | --- | --- | --- |")
        for question, submissions in rows:
            placement = placement_for(question, submissions[0])
            folder = f"{placement.topic_dir}/{placement.problem_dir}"
            langs = " ".join(
                f"[{lang_display(s.lang, s.lang_name)}]({_encode(folder)}/solution.{lang_extension(s.lang)})"
                for s in sorted(submissions, key=lambda s: s.lang)
            )
            lines.append(
                f"| {question.frontend_id} "
                f"| [{question.title}]({_encode(folder)}) "
                f"| {question.difficulty} "
                f"| {langs} |"
            )
        lines.append("")
    return "\n".join(lines)


def _sort_key(question: Question) -> tuple[int, str]:
    return (int(question.frontend_id) if question.frontend_id.isdigit() else 10**9,
            question.title)


def _encode(path: str) -> str:
    return path.replace(" ", "%20")


def write_text(path: Path, content: str) -> bool:
    """Write only when the content actually changed. Returns True if written."""
    if path.exists() and path.read_text(encoding="utf-8") == content:
        return False
    path.parent.mkdir(parents=True, exist_ok=True)
    path.write_text(content, encoding="utf-8", newline="\n")
    return True
