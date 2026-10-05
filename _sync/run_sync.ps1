<#
    Wrapper for the scheduled task: runs an incremental sync and logs the result.
    Usage:  powershell -ExecutionPolicy Bypass -File "run_sync.ps1" [-Push] [-Full] [-NoNeetCode] [-NoWait]

    By default this also refreshes the NeetCode clone and imports anything new
    from it. Pass -NoNeetCode to sync LeetCode only.

    The task launches this through `conhost.exe --headless`, so no window opens.
    A visible console window can be closed, and closing it kills the run
    mid-sync (exit 0xC000013A) - which is how two catch-up runs died right as the
    machine woke. conhost does not pass the exit code back to Task Scheduler, so
    the last line of every log entry records it instead.
#>
param([switch]$Push, [switch]$Full, [switch]$NoNeetCode, [switch]$NoWait)

$ErrorActionPreference = "Continue"
$syncDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$logDir = Join-Path $syncDir "logs"
if (-not (Test-Path $logDir)) { New-Item -ItemType Directory -Path $logDir | Out-Null }
$log = Join-Path $logDir ("sync-{0}.log" -f (Get-Date -Format "yyyy-MM"))

# Add-Content with an explicit encoding, not Tee-Object: in PowerShell 5.1
# Tee-Object writes UTF-16, which makes the log unreadable to everything else.
function Write-Log([string]$line) {
    Add-Content -Path $log -Value $line -Encoding utf8
    Write-Output $line
}

Write-Log ("=== {0} ===" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss"))

# The trigger fires at 21:00-21:05. A run that starts outside that window is a
# catch-up after the machine was asleep or off, starting the instant it wakes.
# Let the system settle first instead of racing sign-in and the network.
$now = Get-Date
$scheduledWindow = ($now.Hour -eq 21) -and ($now.Minute -lt 20)
if (-not $scheduledWindow -and -not $NoWait) {
    Write-Log "Catch-up run: waiting 2 minutes for the system to settle."
    Start-Sleep -Seconds 120
}

# Then wait, up to two minutes, for DNS to answer. The sync retries on its own
# too, but there is no point starting it while the network is still coming up.
$deadline = (Get-Date).AddMinutes(2)
$online = $false
while ((Get-Date) -lt $deadline) {
    try { [System.Net.Dns]::GetHostAddresses("leetcode.com") | Out-Null; $online = $true; break }
    catch { Start-Sleep -Seconds 10 }
}
if (-not $online) { Write-Log "Network still unavailable after 2 minutes; trying anyway." }

# -u: unbuffered, so each line reaches the log the moment it is printed. Output
# used to be written only once Python exited, so a run killed partway left just
# a timestamp behind and no sign of how far it had got.
$syncArgs = @("-u", "-m", "leetcode_sync")
if ($Push) { $syncArgs += "--push" }
if ($Full) { $syncArgs += "--full" }
if (-not $NoNeetCode) { $syncArgs += "--with-neetcode" }

Set-Location $syncDir
& python $syncArgs 2>&1 | ForEach-Object { Write-Log $_.ToString() }
$code = $LASTEXITCODE

# 0 ok, 3 cookie expired, 4 no network, 5 another sync was already running.
Write-Log "exit code $code"
exit $code
