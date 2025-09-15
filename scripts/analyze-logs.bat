@echo off
echo ========================================
echo YueHeApp 日志分析工具
echo ========================================
echo.

echo 分析日志文件...
echo.

echo ========== 错误统计 ==========
echo 后端API错误:
if exist "logs\backend\api\error.log" (
    findstr /c:"ERROR" "logs\backend\api\error.log" | find /c /v ""
) else (
    echo 0
)

echo 后端Web错误:
if exist "logs\backend\web\error.log" (
    findstr /c:"ERROR" "logs\backend\web\error.log" | find /c /v ""
) else (
    echo 0
)

echo.
echo ========== 日志文件大小 ==========
for /r logs %%f in (*.log) do (
    echo %%~nxf: %%~zf bytes
)

echo.
echo ========== 最近登录记录 ==========
if exist "logs\backend\api\application.log" (
    findstr /c:"用户登录" "logs\backend\api\application.log" | findstr /c:"成功"
) else (
    echo 无登录记录
)

echo.
echo ========== 数据库操作统计 ==========
if exist "logs\database\sql.log" (
    echo SQL查询次数:
    findstr /c:"SELECT" "logs\database\sql.log" | find /c /v ""
    echo SQL插入次数:
    findstr /c:"INSERT" "logs\database\sql.log" | find /c /v ""
    echo SQL更新次数:
    findstr /c:"UPDATE" "logs\database\sql.log" | find /c /v ""
    echo SQL删除次数:
    findstr /c:"DELETE" "logs\database\sql.log" | find /c /v ""
) else (
    echo 无数据库日志
)

echo.
pause
