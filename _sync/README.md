# `_sync` — the LeetCode sync tool

Pulls your LeetCode submission history and writes each accepted solution into this
repository, grouped by the problem's primary topic tag:

```
two-pointers/
  0015-3sum/
    README.md        <- problem link, difficulty, tags, per-language stats
    solution.py
    solution.cpp
```

One file per problem per language, always the most recent accepted version.

## How it works

LeetCode has no public API, but the site's own history endpoint
(`GET /api/submissions/`) returns your submissions newest-first **including the full
source code**. The sync pages through it with your session cookie, keeps the first
accepted hit for each (problem, language) pair, and asks the GraphQL endpoint for
each problem's number, difficulty, and topic tags.

State lives in three files so repeat runs stay cheap:

| File | Purpose |
| --- | --- |
| `.sync_state.json` | Newest submission already seen — later runs stop there instead of re-walking history. |
| `.problem_cache.json` | Problem metadata, fetched once per problem, ever. |
| `.solution_index.json` | Everything in the repo, so the READMEs can be regenerated from an incremental run. |

## Setup

1. Install the one dependency:

   ```
   pip install -r requirements.txt
   ```

2. Copy `.env.example` to `.env` and paste in your cookies (see the comments in
   that file for where to find them in devtools). `.env` is gitignored.

3. Smoke test — reads 40 submissions, writes nothing:

   ```
   python -m leetcode_sync --dry-run --max-submissions 40
   ```

4. Backfill everything:

   ```
   python -m leetcode_sync --full
   ```

   A few thousand submissions takes a few minutes; the endpoint returns 20 per
   request and the sync pauses between pages to stay polite.

## Everyday use

```
python -m leetcode_sync
```

Picks up where the last run stopped and commits. Add `--push` if you've set a remote.

| Flag | Effect |
| --- | --- |
| `--full` | Ignore saved state and walk the entire history. |
| `--dry-run` | Print what would be written, change nothing. |
| `--max-submissions N` | Stop after N submissions. |
| `--delay SECONDS` | Pause between pages (default 1.0). Raise if rate-limited. |
| `--no-commit` | Write files but don't commit. |
| `--push` | Push after committing. |

## Running it automatically

`run_sync.ps1` wraps an incremental sync and appends to `logs/`. Register it as a
daily Windows scheduled task:

```
schtasks /create /tn "LeetCode Sync" /tr "powershell -ExecutionPolicy Bypass -File \"%USERPROFILE%\LeetCode Submissions\_sync\run_sync.ps1\"" /sc daily /st 21:00
```

Add `-Push` inside the `/tr` string once a remote is configured.

## Notes

- The session cookie expires every week or two. When that happens the sync exits
  with `auth error` and you paste a fresh cookie into `.env` — nothing else breaks,
  and the next run resumes from where it stopped.
- Premium-only problems sync like any other; if LeetCode won't return metadata for
  a slug, the sync reports it under "Skipped" and moves on rather than failing.
- Tests: `python -m pytest tests` (no network — the client is faked).

## The credential guard

`githooks/pre-commit` refuses to commit anything that looks like a live LeetCode
cookie, and refuses to stage `_sync/.env` at all. It is already active in this
clone; a fresh clone has to opt in, because git does not run hooks it was handed
by a remote:

```
git config core.hooksPath _sync/githooks
```

To bypass it deliberately: `git commit --no-verify`.
