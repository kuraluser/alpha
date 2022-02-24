$DatFileDirPath = "C:\cpdss_dat_file_download"
$FileServerInstallPath = "C:\CPDSS\Loadicator\FileServer\Installations"
$FileServerPath = "C:\CPDSS\Loadicator\FileServer"

$Date = get-date -format MMMM-dd-yyyy-HH-mm
Start-Transcript ${FileServerPath}\loadicatorNginxFsLogs\$Date".log"
Write-Host "Running job for Configure nssm for loadicator"
nssm install nginx-fs-svc ${FileServerInstallPath}\nginx-1.21.4\nginx.exe
nssm set nginx-fs-svc AppDirectory ${FileServerInstallPath}\nginx-1.21.4
nssm set nginx-fs-svc DisplayName nginx-fs-svc
nssm set nginx-fs-svc Start SERVICE_DELAYED_AUTO_START
Stop-Transcript

if(!(Test-Path -Path ${DatFileDirPath})) {
          New-Item -Path  ${DatFileDirPath} -ItemType Directory
}

Stop-Service nginx-fs-svc
Start-Service nginx-fs-svc

New-NetFirewallRule -DisplayName "LoadicatorFS" -Direction inbound -Profile Any -Action Allow -LocalPort 8090 -Protocol TCP