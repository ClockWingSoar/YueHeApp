@echo off
echo ========================================
echo YueHeApp 日志查看工具
echo ========================================
echo.

:menu
echo 请选择要查看的日志类型：
echo 1. 后端API日志
echo 2. 后端Web日志
echo 3. 前端日志
echo 4. 数据库日志
echo 5. 错误日志
echo 6. 所有日志
echo 7. 实时监控所有日志
echo 8. 退出
echo.

set /p choice=请输入选择 (1-8): 

if "%choice%"=="1" goto api_logs
if "%choice%"=="2" goto web_logs
if "%choice%"=="3" goto frontend_logs
if "%choice%"=="4" goto database_logs
if "%choice%"=="5" goto error_logs
if "%choice%"=="6" goto all_logs
if "%choice%"=="7" goto monitor_logs
if "%choice%"=="8" goto exit
goto menu

:api_logs
echo.
echo ========== 后端API日志 ==========
if exist "logs\backend\api\application.log" (
    type "logs\backend\api\application.log"
) else (
    echo 日志文件不存在: logs\backend\api\application.log
)
echo.
pause
goto menu

:web_logs
echo.
echo ========== 后端Web日志 ==========
if exist "logs\backend\web\application.log" (
    type "logs\backend\web\application.log"
) else (
    echo 日志文件不存在: logs\backend\web\application.log
)
echo.
pause
goto menu

:frontend_logs
echo.
echo ========== 前端日志 ==========
if exist "logs\frontend\nextjs\application.log" (
    type "logs\frontend\nextjs\application.log"
) else (
    echo 日志文件不存在: logs\frontend\nextjs\application.log
)
echo.
pause
goto menu

:database_logs
echo.
echo ========== 数据库日志 ==========
if exist "logs\database\sql.log" (
    type "logs\database\sql.log"
) else (
    echo 日志文件不存在: logs\database\sql.log
)
echo.
pause
goto menu

:error_logs
echo.
echo ========== 错误日志 ==========
echo 后端API错误日志:
if exist "logs\backend\api\error.log" (
    type "logs\backend\api\error.log"
) else (
    echo 日志文件不存在: logs\backend\api\error.log
)
echo.
echo 后端Web错误日志:
if exist "logs\backend\web\error.log" (
    type "logs\backend\web\error.log"
) else (
    echo 日志文件不存在: logs\backend\web\error.log
)
echo.
pause
goto menu

:all_logs
echo.
echo ========== 所有日志 ==========
for /r logs %%f in (*.log) do (
    echo ========== %%f ==========
    type "%%f"
    echo.
)
pause
goto menu

:monitor_logs
echo.
echo ========== 实时监控所有日志 ==========
echo 按 Ctrl+C 停止监控
echo.
for /r logs %%f in (*.log) do (
    echo 监控文件: %%f
)
echo.
echo 开始监控...
powershell -Command "Get-ChildItem -Path 'logs' -Recurse -Filter '*.log' | ForEach-Object { Get-Content $_.FullName -Wait -Tail 10 }"
goto menu

:exit
echo 退出日志查看工具
exit
