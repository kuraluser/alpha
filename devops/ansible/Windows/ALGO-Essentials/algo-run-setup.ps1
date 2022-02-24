cd C:\Users\Administrator\Desktop\ALGO\essentials\nssm-2.24\win64\
./nssm.exe install algo-run C:\Windows\System32\WindowsPowerShell\v1.0\powershell.exe .\start-algo.ps1
./nssm.exe set algo-run AppDirectory C:\Users\Administrator\Desktop\ALGO\essentials
./nssm.exe set algo-run DisplayName algo-run 
./nssm.exe set algo-run Start SERVICE_DELAYED_AUTO_START 
./nssm.exe set algo-run AppStdout C:\Users\Administrator\Desktop\ALGO\essentials\algo.out.txt
./nssm.exe set algo-run AppStderr C:\Users\Administrator\Desktop\ALGO\essentials\algo.out.txt