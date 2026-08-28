<#
    Wrapper for the scheduled task: runs an incremental sync and logs the result.
    Usage:  powershell -ExecutionPolicy Bypass -File "run_sync.ps1" [-Push] [-Full]
#>
param([switch]$Push, [switch]$Full)

$ErrorActionPreference = "Continue"
$syncDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$logDir = Join-Path $syncDir "logs"
if (-not (Test-Path $logDir)) { New-Item -ItemType Directory -Path $logDir | Out-Null }
$log = Join-Path $logDir ("sync-{0}.log" -f (Get-Date -Format "yyyy-MM"))

$syncArgs = @("-m", "leetcode_sync")
if ($Push) { $syncArgs += "--push" }
if ($Full) { $syncArgs += "--full" }

Set-Location $syncDir

# Add-Content with an explicit encoding, not Tee-Object: in PowerShell 5.1
# Tee-Object writes UTF-16, which makes the log unreadable to everything else.
$header = "=== {0} ===" -f (Get-Date -Format "yyyy-MM-dd HH:mm:ss")
Add-Content -Path $log -Value $header -Encoding utf8
Write-Output $header

$output = & python $syncArgs 2>&1 | ForEach-Object { $_.ToString() }
if ($output) {
    Add-Content -Path $log -Value $output -Encoding utf8
    $output | ForEach-Object { Write-Output $_ }
}
exit $LASTEXITCODE
