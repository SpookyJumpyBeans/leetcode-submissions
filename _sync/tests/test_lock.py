import json
import os
import subprocess
import sys
import time
from pathlib import Path

import pytest

sys.path.insert(0, str(Path(__file__).resolve().parents[1]))

from leetcode_sync.lock import SyncInProgress, SyncLock, pid_alive


def dead_pid() -> int:
    """The PID of a process that has already exited."""
    proc = subprocess.Popen([sys.executable, "-c", "pass"])
    proc.wait()
    return proc.pid


def write_lock(path: Path, pid: int, started: float) -> None:
    path.write_text(json.dumps({"pid": pid, "started": started}), encoding="utf-8")


def test_pid_alive_tells_live_from_dead():
    assert pid_alive(os.getpid())
    assert not pid_alive(dead_pid())
    assert not pid_alive(0)


def test_acquire_records_this_process_and_release_removes_it(tmp_path):
    path = tmp_path / ".sync.lock"
    lock = SyncLock(path).acquire()
    assert json.loads(path.read_text())["pid"] == os.getpid()
    lock.release()
    assert not path.exists()


def test_a_second_run_is_refused_while_the_first_holds_the_lock(tmp_path):
    path = tmp_path / ".sync.lock"
    with SyncLock(path):
        with pytest.raises(SyncInProgress, match="already running"):
            SyncLock(path).acquire()
    SyncLock(path).acquire().release()   # free again afterwards


def test_a_lock_left_by_a_killed_run_is_taken_over(tmp_path):
    # Closing the task's window kills the run before it can release the lock.
    path = tmp_path / ".sync.lock"
    write_lock(path, dead_pid(), time.time())
    lock = SyncLock(path).acquire()
    assert json.loads(path.read_text())["pid"] == os.getpid()
    lock.release()


def test_a_lock_older_than_any_run_is_stale_even_if_its_pid_is_reused(tmp_path):
    path = tmp_path / ".sync.lock"
    write_lock(path, os.getpid(), time.time() - 3 * 60 * 60)
    SyncLock(path).acquire().release()


def test_an_unreadable_lock_file_is_taken_over(tmp_path):
    path = tmp_path / ".sync.lock"
    path.write_text("{half-written", encoding="utf-8")
    SyncLock(path).acquire().release()


def test_the_context_manager_releases_even_when_the_run_fails(tmp_path):
    path = tmp_path / ".sync.lock"
    with pytest.raises(RuntimeError):
        with SyncLock(path):
            raise RuntimeError("sync blew up")
    assert not path.exists()


def test_release_never_deletes_a_lock_it_does_not_hold(tmp_path):
    path = tmp_path / ".sync.lock"
    with SyncLock(path):
        SyncLock(path).release()   # a refused run tidying up must not free it
        assert path.exists()


def test_cli_exits_cleanly_when_another_sync_is_running(monkeypatch, capsys, tmp_path):
    from leetcode_sync import cli
    path = tmp_path / ".sync.lock"
    monkeypatch.setattr(cli, "SyncLock", lambda: SyncLock(path))
    write_lock(path, os.getpid(), time.time())   # a live holder: this test process
    assert cli.main([]) == 5
    assert "another sync is already running" in capsys.readouterr().err
    assert path.exists()   # the refused run left the holder's lock alone
