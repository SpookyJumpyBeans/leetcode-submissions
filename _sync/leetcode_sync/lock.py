"""One sync at a time.

A manual run and the scheduled task can start at the same moment. Both see the
same new submissions, the slower one commits the faster one's files under the
wrong message, and two `git pull`s in the NeetCode clone trip over each other's
fetch results. Holding a lock for the whole run makes the second one exit at once.

A run killed partway - closing its window, a crash - leaves the lock file behind,
so a lock is only honoured while the process that wrote it is still alive.
"""

from __future__ import annotations

import ctypes
import json
import os
import time
from pathlib import Path

from .config import SYNC_ROOT

LOCK_PATH = SYNC_ROOT / ".sync.lock"

# The scheduled task is capped at one hour, so a lock older than this is left
# over from a dead run even if its PID has since been reused by another process.
STALE_AFTER = 2 * 60 * 60


class SyncInProgress(RuntimeError):
    pass


def pid_alive(pid: int) -> bool:
    if pid <= 0:
        return False
    if os.name == "nt":
        # Not os.kill(pid, 0): on Windows signal 0 is CTRL_C_EVENT, so that call
        # interrupts the process rather than checking whether it exists.
        process_query_limited_information = 0x1000
        still_active = 259
        kernel32 = ctypes.windll.kernel32
        handle = kernel32.OpenProcess(process_query_limited_information, False, pid)
        if not handle:
            return False
        try:
            code = ctypes.c_ulong()
            if not kernel32.GetExitCodeProcess(handle, ctypes.byref(code)):
                return False
            return code.value == still_active
        finally:
            kernel32.CloseHandle(handle)
    try:
        os.kill(pid, 0)
    except ProcessLookupError:
        return False
    except PermissionError:
        return True
    return True


class SyncLock:
    def __init__(self, path: Path = LOCK_PATH, stale_after: float = STALE_AFTER,
                 clock=time.time):
        self.path = Path(path)
        self.stale_after = stale_after
        self.clock = clock
        self.held = False

    def _holder(self) -> dict | None:
        try:
            data = json.loads(self.path.read_text(encoding="utf-8"))
            return {"pid": int(data["pid"]), "started": float(data["started"])}
        except (OSError, ValueError, KeyError, TypeError):
            return None   # unreadable or half-written: nobody can be relying on it

    def _is_stale(self, holder: dict | None) -> bool:
        if holder is None:
            return True
        if self.clock() - holder["started"] > self.stale_after:
            return True
        return not pid_alive(holder["pid"])

    def acquire(self) -> "SyncLock":
        for _ in range(2):   # second pass only after clearing a stale lock
            try:
                fd = os.open(self.path, os.O_CREAT | os.O_EXCL | os.O_WRONLY)
            except FileExistsError:
                holder = self._holder()
                if self._is_stale(holder):
                    self.path.unlink(missing_ok=True)
                    continue
                started = time.strftime("%H:%M", time.localtime(holder["started"]))
                raise SyncInProgress(
                    f"another sync is already running (pid {holder['pid']}, started {started})"
                ) from None
            with os.fdopen(fd, "w", encoding="utf-8") as f:
                json.dump({"pid": os.getpid(), "started": self.clock()}, f)
            self.held = True
            return self
        raise SyncInProgress("could not take the sync lock")

    def release(self) -> None:
        if self.held:
            self.path.unlink(missing_ok=True)
            self.held = False

    def __enter__(self) -> "SyncLock":
        return self.acquire()

    def __exit__(self, *exc) -> None:
        self.release()
