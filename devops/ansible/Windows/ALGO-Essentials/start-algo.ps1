
Write-Host "Starting ALGO...`n"
Start-Sleep(3)
Set-Location C:\Users\cpdssadmin\Desktop\ALGO\loadableStudy
.\venvs\Scripts\activate.ps1 
uvicorn main:app --reload --host 0.0.0.0 --port 8080