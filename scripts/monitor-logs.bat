@echo off
echo ========================================
echo YueHeApp 日志监控工具
echo ========================================
echo.

echo 开始监控日志文件...
echo 按 Ctrl+C 停止监控
echo.

:monitor
echo.
echo [%date% %time%] 检查日志文件状态...

for /r logs %%f in (*.log) do (
    if exist "%%f" (
        echo 监控: %%~nxf (大小: %%~zf bytes)
    )
)

echo.
echo 等待5秒后继续监控...
timeout /t 5 /nobreak >nul
goto monitor
