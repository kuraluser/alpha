$ErrorActionPreference="Stop";
If(-NOT ([Security.Principal.WindowsPrincipal][Security.Principal.WindowsIdentity]::GetCurrent() ).IsInRole( [Security.Principal.WindowsBuiltInRole] "Administrator")){ throw "Run command in an administrator PowerShell prompt"};

$WireguardCheck = (Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*).DisplayName -Contains "WireGuard"

If(-Not $WireguardCheck) {
	Write-Output "Wireguard is NOT installed.";
    $WIREGUARD_INSTALLED="n"
} else {
	Write-Output "Wireguard is installed."
    $WIREGUARD_INSTALLED="y"
}

$ResilioCheck = (Get-ItemProperty HKLM:\SOFTWARE\Microsoft\Windows\CurrentVersion\Uninstall\*).DisplayName -Contains "Resilio Connect Agent"

If(-Not $ResilioCheck) {
	Write-Output "Resilio is NOT installed.";
    $RESILIO_INSTALLED="n"
} else {
	Write-Output "Resilio is installed."
    $RESILIO_INSTALLED="y"
}

Write-Output "Checking Wireguard existing configuarion"

$FILE="C:\Program Files\WireGuard\Data\Configurations\tun6.conf.dpapi"

If(Test-Path -Path $FILE -PathType Leaf) {
    Write-Output "$FILE config file exists"
    while($true) {
        $WIREGUARD_CONF = Read-Host -Prompt "Do you want to overwrite wireguard config file? (y/n)"
        If($WIREGUARD_CONF -eq "y") {
            $set_wg_conf="y"
            Write-Output "Creating Config file"
            break
        } elseif ($WIREGUARD_CONF -eq "n") {
            $set_wg_conf="n"
            Write-Output "Config file will not be overwrited"
            break
        } else {
            Write-Output "Please enter y or n"
        }
    }
} else {
    $set_wg_conf="y"
    Write-Output "Creating Config file"
}

Write-Output "Checking Resilio existing configuarion"
$RESILIO_FILE="C:\Program Files\Resilio Connect Agent\sync.conf"

If(Test-Path -Path $RESILIO_FILE -PathType Leaf) {
    Write-Output "$RESILIO_FILE config file exists"
    while($true) {
        $RESILIO_CONF = Read-Host -Prompt "Do you want to overwrite Resilio config file? (y/n)"
        If($RESILIO_CONF -eq "y") {
            $set_resilio_conf="y"
            Write-Output "Creating Config file"
            break
        } elseif ($RESILIO_CONF -eq "n") {
            $set_resilio_conf="n"
            Write-Output "Config file will not be overwrited"
            break
        } else {
            Write-Output "Please enter y or n"
        }
    }
} else {
    $set_resilio_conf="y"
    Write-Output "Creating Config file"
}

If($set_wg_conf -eq "y" -Or $set_wg_conf -eq "n") {
    Write-Output "You have chosen - $WIREGUARD_CONF to overwrite wireguard config file"
}

$IMO = Read-Host -Prompt "Enter vessel IMO number"
$TOKEN = Read-Host -Prompt "Enter Installation token"

$KEY=$(Invoke-WebRequest -Headers @{'Accept' = 'application/json';} -URI "https://g0yhpyibz3.execute-api.us-east-2.amazonaws.com/CPDSS-WG_TokenVerify?vessel=$IMO&token=$TOKEN").content

If($KEY -eq "false") {
    Write-Output "Invalid IMO or Token! Installation failed"
    exit
}

$ARRAY=$KEY.Split("|")

$NODE_IP=$ARRAY[0]
$NODE_NAME=$ARRAY[1]
$CLIENT_KEY=$ARRAY[2]
$WG_PUB_KEY=$ARRAY[3]
$WG_IP=$ARRAY[4]
$WG_ENDPOINT=$ARRAY[5]+":443"
$RESILIO_CONF=$ARRAY[6]

Write-Output "NODE_IP: $NODE_IP `nNODE_NAME: $NODE_NAME `nCLIENT_KEY: $CLIENT_KEY `nWG_PUB_KEY: $WG_PUB_KEY `nWG_IP: $WG_IP `nWG_ENDPOINT: $WG_ENDPOINT `nRESILIO_CONF: $RESILIO_CONF"

If($WIREGUARD_INSTALLED -eq "n") {   
    Write-Output "+---------------------------------------------------------------+"
    Write-Output "|\tWireGuard package is Not installed.\t\t\t|"
    Write-Output "|\tInstalling Wireguard\t\t|"
    Write-Output "+---------------------------------------------------------------+"
    $WIREGUARD_MSI_PATH="C:\CPDSS\Softwares\WireGuard\wireguard-amd64-0.5.3.msi"
    Start-Process msiexec.exe -ArgumentList '/q', '/I', $WIREGUARD_MSI_PATH -Wait -NoNewWindow -PassThru | Out-Null
}
If($set_wg_conf -eq "y") {
    $WG_CONF_FILE_PATH="C:\CPDSS\Softwares\WireGuard\tun6.conf"
    $WG_CONF_DATA=@"
[Interface]
Address = wg_client_ip/32
PrivateKey = client_private_key

[Peer]
PublicKey = server_public_key
AllowedIPs = wg_ip
Endpoint = wg_endpoint
PersistentKeepalive = 15
"@
#     [IO.File]::WriteAllLines($WG_CONF_FILE_PATH, $WG_CONF_DATA)
    $WG_CONF_DATA | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII

    ((Get-Content -Path $WG_CONF_FILE_PATH -Raw) -replace "wg_client_ip", $NODE_IP) | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII
    ((Get-Content -Path $WG_CONF_FILE_PATH -Raw) -replace "client_private_key", $CLIENT_KEY) | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII
    ((Get-Content -Path $WG_CONF_FILE_PATH -Raw) -replace "server_public_key", $WG_PUB_KEY) | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII
    ((Get-Content -Path $WG_CONF_FILE_PATH -Raw) -replace "wg_ip", $WG_IP) | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII
    ((Get-Content -Path $WG_CONF_FILE_PATH -Raw) -replace "wg_endpoint", $WG_ENDPOINT) | Out-File -FilePath $WG_CONF_FILE_PATH -Encoding ASCII

    
    # Get-Content -Path $WG_CONF_FILE_PATH -Raw | out-file -encoding ASCII $WG_CONF_FILE_PATH

    Start-Process 'C:\Program Files\WireGuard\wireguard.exe' -ArgumentList '/installtunnelservice', "$WG_CONF_FILE_PATH" -Wait -NoNewWindow -PassThru | Out-Null
    If($null -eq (Get-Service 'WireGuardTunnel$tun6' | Where-Object {$_.Status -eq "Running"})) {
        Start-Process sc.exe -ArgumentList 'config', 'WireGuardTunnel$tun6', 'start= delayed-auto' -Wait -NoNewWindow -PassThru | Out-Null
        Start-Service -Name 'WireGuardTunnel$tun6' -ErrorAction SilentlyContinue
    }
    
    #Resilio Start
    If($set_resilio_conf -eq "y") {
        If($RESILIO_INSTALLED -eq "n") {
            Write-Output "+---------------------------------------------------------------+"
            Write-Output "|\tResilio Connect package is Not installed.\t\t\t|"
            Write-Output "|\tInstalling Resilio Connect\t\t|"
            Write-Output "+---------------------------------------------------------------+"
            $RESILIO_MSI_PATH="C:\CPDSS\Softwares\Resilio\Resilio-Connect-Agent_x64.msi"
            $RESILIO_CONF_SRC_PATH="C:\CPDSS\Softwares\Resilio\"

            $RESILIO_CONF_DATA=$RESILIO_CONF
            $RESILIO_CONF_DATA | Out-File -FilePath $RESILIO_CONF_SRC_PATH'\sync.conf' -Encoding ASCII
            Start-Process msiexec.exe -ArgumentList '/I', $RESILIO_MSI_PATH, 'ADDSOURCE=ALL', '/qn', 'SERVICE_USER="Local System"', "CONFIG_SRC_PATH=$RESILIO_CONF_SRC_PATH" -Wait -NoNewWindow -PassThru | Out-Null
        }
    }
}