@echo off
echo ========================================
echo YueHeApp 日志清理工具
echo ========================================
echo.

echo 当前日志文件大小:
for /r logs %%f in (*.log) do (
    echo %%~nxf: %%~zf bytes
)
echo.

set /p confirm=确定要清理30天前的日志文件吗? (y/n): 

if /i "%confirm%"=="y" (
    echo.
    echo 开始清理日志文件...
    
    forfiles /p logs /s /m *.log /d -30 /c "cmd /c del @path" 2>nul
    if %errorlevel%==0 (
        echo 成功清理30天前的日志文件
    ) else (
        echo 没有找到需要清理的日志文件
    )
    
    echo.
    echo 清理后的日志文件大小:
    for /r logs %%f in (*.log) do (
        echo %%~nxf: %%~zf bytes
    )
) else (
    echo 取消清理操作
)

echo.
pause
