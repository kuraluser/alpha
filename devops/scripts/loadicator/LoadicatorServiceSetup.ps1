<# 
This script was created to Configure loadicator as a service.
#>

Set-StrictMode -Version "2.0"
$ErrorActionPreference="Stop"
Clear-Host

$Date = get-date -format MMMM-dd-yyyy-HH-mm

##### Create file to store password into profile of account that will execute the db restore
# Path for password file
$AccountFile = "$env:HOMEPATH\Account.User.pwd"

# Check for password file
if ((Test-Path $AccountFile) -eq "True") {
Write-Host "The file $AccountFile exist. Skipping credential request"
}
else {
Write-Host ("The value $AccountFile not found," +
" creating credentials file.")

# Create credential object by prompting user for data.
$Credential = Get-Credential

# Encrypt the password to disk
$Credential.Password | ConvertFrom-SecureString | Out-File $env:HOMEPATH\Account.User.pwd
$Credential.UserName | Out-File $env:HOMEPATH\Account.User.pwd -Append
}

##### Read password for DBhost login #####
# Read password from file
$SecureString = Get-Content $AccountFile -First 1 | ConvertTo-SecureString
$Username = Get-Content $AccountFile -Last 1

# Create credential object programmatically
$NewCred = New-Object System.Management.Automation.PSCredential("Account",$SecureString)

# Parameters for configure nssm
# $nssmLocation = "C:\Users\Administrator\Desktop\ALGO\essentials\nssm-2.24\nssm-2.24\win64\"
$loadicatorPath = "C:\Users\Administrator\Desktop\Loadicator\LoadcomCpDSS_runfiles\LoadcomCpDSS.exe"
$loadicatorFolder = "C:\Users\Administrator\Desktop\Loadicator\LoadcomCpDSS_runfiles\"

# nssm path
# Set-Location $nssmLocation

##### Configure nssm for loadicator #####

Start-Transcript $env:HOMEPATH\loadicatorSetupLogs\$Date".log"
Write-Host "Running job for Configure nssm for loadicator"
nssm install loadicator-run $loadicatorPath
nssm set loadicator-run AppDirectory $loadicatorFolder
nssm set loadicator-run DisplayName loadicator-run
nssm set loadicator-run Start SERVICE_DELAYED_AUTO_START

##### Start loadicator Service #####
Start-Service loadicator-run
Get-Service loadicator-run

# Parameters for configure scheduled job
$restartLoadicatorScriptPath = "C:\Users\Administrator\Desktop\Loadicator\essentials\restart-loadicator.ps1"
$Action = New-ScheduledTaskAction -Execute 'powershell.exe' -Argument @"
-NonInteractive -NoLogo -NoProfile -File "$restartLoadicatorScriptPath"
"@
$Trigger = New-ScheduledTaskTrigger -Daily -At 6:30pm
$Settings = New-ScheduledTaskSettingsSet
$Task = New-ScheduledTask -Action $Action -Trigger $Trigger -Settings $Settings

##### Configure scheduled job for loadicator #####

Register-ScheduledTask -TaskName 'Loadicator Restart' -InputObject $Task -User $Username -Password $NewCred.GetNetworkCredential().Password

Stop-Transcript
