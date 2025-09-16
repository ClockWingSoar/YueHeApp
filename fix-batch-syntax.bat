@echo off
REM 修复批处理文件中的语法错误

echo 修复批处理文件语法错误...

REM 修复quick-start.bat
echo 修复 quick-start.bat...
powershell -Command "(Get-Content 'quick-start.bat') -replace '\.\.\.', '' | Set-Content 'quick-start.bat'"

REM 修复start-hybrid-dev.bat
echo 修复 start-hybrid-dev.bat...
powershell -Command "(Get-Content 'start-hybrid-dev.bat') -replace '\.\.\.', '' | Set-Content 'start-hybrid-dev.bat'"

REM 修复start-wsl-docker.bat
echo 修复 start-wsl-docker.bat...
powershell -Command "(Get-Content 'start-wsl-docker.bat') -replace '\.\.\.', '' | Set-Content 'start-wsl-docker.bat'"

REM 修复start-wsl-quick.bat
echo 修复 start-wsl-quick.bat...
powershell -Command "(Get-Content 'start-wsl-quick.bat') -replace '\.\.\.', '' | Set-Content 'start-wsl-quick.bat'"

REM 修复health-check.bat
echo 修复 health-check.bat...
powershell -Command "(Get-Content 'health-check.bat') -replace '\.\.\.', '' | Set-Content 'health-check.bat'"

echo 修复完成！
pause
