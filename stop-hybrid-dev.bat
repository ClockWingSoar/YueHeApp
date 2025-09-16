@echo off
REM YueHeApp 混合开发模式停止脚本

echo.
echo ========================================
echo 🛑 YueHeApp 混合开发模式停止
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 🔍 检查运行中的服务...

REM 停止基础架构服务容器
echo 🏗️  停止基础架构服务容器...
wsl docker-compose -f docker-compose.dev.yml down

REM 检查是否有本地服务在运行
echo 🔍 检查本地服务...

REM 检查后端服务
netstat -ano | findstr :8090 >nul 2>&1
if %errorlevel% equ 0 (
    echo ⚠️  检测到后端服务正在运行 (端口8090)
    echo 💡 请手动关闭后端服务窗口
) else (
    echo ✅ 后端服务已停止
)

REM 检查前端服务
netstat -ano | findstr :3010 >nul 2>&1
if %errorlevel% equ 0 (
    echo ⚠️  检测到前端服务正在运行 (端口3010)
    echo 💡 请手动关闭前端服务窗口
) else (
    echo ✅ 前端服务已停止
)

echo.
echo ✅ 混合开发模式停止完成！
echo.
echo 📝 说明：
echo   - 基础架构服务 (MySQL/Redis) 已停止
echo   - 请手动关闭后端和前端服务窗口
echo   - 如需完全清理，可运行: docker system prune -f
echo.

pause
