import sys
from pathlib import Path

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.config import load_env_file
from leetcode_sync.setup import prompt_for_cookies, write_env


def test_write_env_round_trips_through_the_loader(tmp_path):
    env = tmp_path / ".env"
    write_env("session-value", "csrf-value", env)
    values = load_env_file(env)
    assert values["LEETCODE_SESSION"] == "session-value"
    assert values["LEETCODE_CSRF_TOKEN"] == "csrf-value"


def test_prompt_strips_quotes_and_whitespace(tmp_path, monkeypatch):
    env = tmp_path / ".env"
    monkeypatch.setattr("leetcode_sync.setup.getpass", lambda *_: '  "abc123"  ')
    monkeypatch.setattr("builtins.input", lambda *_: "  tok  ")
    assert prompt_for_cookies(env, log=lambda *a: None) == 0
    assert load_env_file(env)["LEETCODE_SESSION"] == "abc123"
    assert load_env_file(env)["LEETCODE_CSRF_TOKEN"] == "tok"


def test_prompt_leaves_file_untouched_when_nothing_entered(tmp_path, monkeypatch):
    env = tmp_path / ".env"
    env.write_text("LEETCODE_SESSION=keep-me\n", encoding="utf-8")
    monkeypatch.setattr("leetcode_sync.setup.getpass", lambda *_: "   ")
    assert prompt_for_cookies(env, log=lambda *a: None) == 1
    assert load_env_file(env)["LEETCODE_SESSION"] == "keep-me"


def test_short_session_triggers_a_truncation_warning(tmp_path, monkeypatch):
    messages = []
    monkeypatch.setattr("leetcode_sync.setup.getpass", lambda *_: "tooshort")
    monkeypatch.setattr("builtins.input", lambda *_: "x")
    prompt_for_cookies(tmp_path / ".env", log=messages.append)
    assert any("truncated" in m for m in messages)
