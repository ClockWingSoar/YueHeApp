@echo off
REM YueHeApp Docker快速启动脚本 - 避免编译问题

echo.
echo ========================================
echo 🚀 YueHeApp Docker快速启动
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 启动模式：Docker模式
echo   - 所有服务在Docker容器中运行
echo   - 避免本地编译问题
echo   - 快速启动，立即可用
echo.

REM 检查WSL Docker
echo 🔍 检查WSL Docker状态...
wsl docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ WSL Docker未运行
    echo 💡 请确保WSL Docker正在运行
    echo 💡 启动命令: wsl -d docker-desktop
    pause
    exit /b 1
)
echo ✅ WSL Docker运行正常

echo.
echo 🛑 停止现有容器...
wsl docker-compose down >nul 2>&1

echo.
echo 🏗️  启动所有服务 (Docker模式)...
wsl docker-compose up -d

echo ⏳ 等待所有服务启动...
timeout /t 30 /nobreak >nul

echo 🔍 检查服务状态...
wsl docker-compose ps

echo.
echo ========================================
echo ✅ YueHeApp Docker启动完成！
echo ========================================
echo.
echo 🌐 服务访问地址：
echo   前端应用: http://localhost:3010
echo   后端API:  http://localhost:8090/api
echo   Web服务:  http://localhost:8081
echo   API服务:  http://localhost:8082
echo   健康检查: http://localhost:8090/actuator/health
echo   数据库:   localhost:3316 (root/root123)
echo   Redis:    localhost:6389
echo.
echo 📝 服务说明：
echo   - 所有服务在Docker容器中运行
echo   - 避免本地编译问题
echo   - 完全隔离的环境
echo.

REM 询问是否打开浏览器
set /p open_browser="是否打开浏览器访问应用？(y/n, 默认y): "
if "%open_browser%"=="" set open_browser=y
if /i "%open_browser%"=="y" (
    start http://localhost:3010
)

echo.
echo 按任意键退出...
pause >nul
