<# 
This script was created to automate the restore of DB
#>

Set-StrictMode -Version "2.0"
$ErrorActionPreference="Stop"
Clear-Host

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
}

##### Read password for DBhost login #####
# Read password from file
$SecureString = Get-Content $AccountFile | ConvertTo-SecureString

# Create credential object programmatically
$NewCred = New-Object System.Management.Automation.PSCredential("Account",$SecureString)

# Variable for postgres password in clear text
$env:PGPASSWORD = $NewCred.GetNetworkCredential().Password

# Get DB Host
$DbMachineIp = $(Get-NetIPAddress | Where-Object { $_.PrefixOrigin -ne "WellKnown" -and $_.AddressFamily -eq "IPv4" }).IPAddress | Select -First 1
$DbMachineIpStr = $DbMachineIp.ToString()

##### Job configuration #####
$DbHost = $DbMachineIpStr
$Port = "5432"
# Username used for restore task
$Username = "postgres"
# Parameters for restore job
$BackupScriptLocation = "E:\CPDSS\Production\UAT6.0.15\Database\Backup-Scripts\"
$Date = get-date -format MMMM-dd-yyyy-HH-mm
$BackupScriptArray = Get-ChildItem -Path $BackupScriptLocation -recurse | Select-Object -expand fullname

$DbNames = @("cpdss_cargo", "cpdss_company", "cpdss_discharging", "cpdss_envoy_client", "cpdss_envoy_writer", "cpdss_loadablestudy", "cpdss_loading",  "cpdss_port",  "cpdss_taskmanager", "cpdss_user", "cpdss_vessel", "i2r", "loadicator_communication_vlcc")

# pg_restore path
Set-Location "C:\Program Files\PostgreSQL\12\bin"

#Drop all Databases
foreach ($DbName in $DbNames) {
  .\dropdb -h $DbHost -p $Port -U $Username $DbName  
}

#Create all Databases
foreach ($DbName in $DbNames) {
  .\createdb -h $DbHost -p $Port -U $Username $DbName  
}

##### Run Restore task #####

Start-Transcript $env:HOMEPATH\$Date".log"
Write-Host "Running job for Restore DB"
foreach ($BackupScript in $BackupScriptArray) {
    foreach ($DbName in $DbNames) {
        if ($BackupScript -Match $DbName) {
            Write-Host "Restoring $DbName with $BackupScript"
            .\pg_restore --host $DbHost --port $Port --username $Username -d $DbName -v "$BackupScript"
        }
    }
}
Stop-Transcript
