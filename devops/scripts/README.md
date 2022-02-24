# CPDSS Scripts

## Files

```
scripts
├───azagent
│       DeploymentGroupAgentSetup.ps1
├───db
│       DbRestore.ps1
├───loadicator
│       LoadicatorServiceSetup.ps1
│       NginxFileServerSetup.ps1
│       restart-loadicator.ps1
└───resilio
        cpdss-resilio-init.ps1
```

## Azure Agent Scripts

Powershell script to setup the Azure Agent in production vessel windows instance.

### Prequisites
- vsts-agent.zip

### Inputs
- DeploymentGroupName: Name of the deployment group
- Token: Personal access token for the deployment group
- Project: Project name

### How to run
1. Download the vsts-agent.zip from the [VSTS Agent Windows x64 2.200.0](https://vstsagentpackage.azureedge.net/agent/2.200.0/vsts-agent-win-x64-2.200.0.zip).
2. Rename the vsts-agent.zip to agent.zip.
2. Extract the agent.zip to C:\azagent\A1
3. Run the script as administrator.
4. Enter the deployment group name, personal access token and project name.
5. Enter tags for the deployment group.

## Db Scripts

PowerShell script to automate database restore.

### Inputs

- Username: DB Username
- Password: DB Password
- BackupScriptLocation: Location of the backup script

### How to use

1. Edit BackupScriptLocation to point to the location of the backup script
2. Run the script
3. Enter the username and password

## Loadicator Scripts

### Loadicator Service Setup

PowerShell Script to automate loadicator service setup and scheduled restart of the service.

#### Prerequisites

- Add nssm.exe to the path

#### Inputs
- Password: Password for the Windows user
- loadicatorPath: Path to the LoadcomCpDSS exe
- loadicatorFolder: Folder where the LoadcomCpDSS exe is located
- restartLoadicatorScriptPath: Path to the restart-loadicator.ps1 script

#### How to use
1. Edit the script to point to the correct paths
2. Run the script
3. Enter the password

### Nginx File Server Setup

PowerShell script to automate Nginx file server setup.

#### Prerequisites

- Add nssm.exe to the path

#### Inputs
- FileServerInstallPath: Path to the Nginx file server installation folder
- FileServerPath: Path to the Nginx file server

#### How to use
1. Edit the script to point to the correct paths
2. Run the script

## Resilio Scripts

PowerShell script to automate WireGuard Client and Resilio Connect installation.

### Prerequisites
- Get IMO and Token from Rundeck job

#### Inputs
- IMO: IMO of the vessel
- Token: Token of the vessel
- RESILIO_MSI_PATH: Path to the Resilio msi
- WIREGUARD_MSI_PATH: Path to the WIREGUARD msi
- WG_CONF_FILE_PATH: Path to the WIREGUARD configuration file
- RESILIO_CONF_SRC_PATH: Path to the Resilio configuration file

#### How to use
1. Run Rundeck job to get IMO and Token for the vessel
2. Edit the script to point to the correct paths if needed
3. Run the script

#### Test WireGuard VPN

Run the following command in terminal after the script has finished:

```
telnet 34.198.166.252 8444
```