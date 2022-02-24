$ErrorActionPreference="Stop";
If(-NOT ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent() ).IsInRole( [Security.Principal.WindowsBuiltInRole] "Administrator"))
    { 
        throw "Run command in an administrator PowerShell prompt"
    };
If($PSVersionTable.PSVersion -lt (New-Object System.Version("3.0")))
    { 
        throw "The minimum version of Windows PowerShell that is required by the script (3.0) does not match the currently running version of Windows PowerShell." 
    };
$DEPLOYMENT_GROUP_NAME = Read-Host -Prompt "Enter Deployment Pool Name"
$TOKEN = Read-Host -Prompt "Enter Personal Access Token"
If(-NOT (Test-Path $env:SystemDrive\'azagent'))
    {
        mkdir $env:SystemDrive\'azagent'
    }; 
cd $env:SystemDrive\'azagent'; 
for($i=1; $i -lt 100; $i++)
    {
        $destFolder="A1";
        cd $destFolder;
        break;
    }; 
$agentZip="$PWD\agent.zip";
Add-Type -AssemblyName System.IO.Compression.FileSystem;
.\config.cmd --deploymentgroup --deploymentgroupname "$DEPLOYMENT_GROUP_NAME" --agent $env:COMPUTERNAME --runasservice --work '_work' --url 'https://dev.azure.com/alpha-ori/' --auth PAT --token $TOKEN;
Remove-Item $agentZip;
