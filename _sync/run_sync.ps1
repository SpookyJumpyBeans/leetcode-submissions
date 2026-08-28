<#
    Wrapper for the scheduled task: runs an incremental sync and logs the result.
    Usage:  powershell -ExecutionPolicy Bypass -File "run_sync.ps1" [-Push]
#>
param([switch]$Push, [switch]$Full)

$ErrorActionPreference = "Stop"
$syncDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$logDir = Join-Path $syncDir "logs"
if (-not (Test-Path $logDir)) { New-Item -ItemType Directory -Path $logDir | Out-Null }
$log = Join-Path $logDir ("sync-{0}.log" -f (Get-Date -Format "yyyy-MM"))

$syncArgs = @("-m", "leetcode_sync")
if ($Push) { $syncArgs += "--push" }
if ($Full) { $syncArgs += "--full" }

Set-Location $syncDir
"=== $(Get-Date -Format 'yyyy-MM-dd HH:mm:ss') ===" | Add-Content -Path $log -Encoding utf8
& python $syncArgs 2>&1 | Tee-Object -FilePath $log -Append
