@echo off
REM YueHeApp 快速停止脚本 - 停止所有服务

echo.
echo ========================================
echo 🛑 YueHeApp 快速停止脚本
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 🔍 检查运行中的服务...

REM 检查Docker容器
echo 📊 检查Docker容器状态...
docker-compose ps

echo.
echo 🛑 停止所有服务...

REM 停止应用服务容器
echo 🔧 停止应用服务容器...
docker-compose down

REM 停止基础架构服务容器
echo 🏗️  停止基础架构服务容器...
docker-compose -f docker-compose.dev.yml down

REM 停止生产环境容器（如果存在）
echo 🏭 停止生产环境容器...
docker-compose -f docker-compose.prod.yml down

REM 清理未使用的容器和网络
echo 🧹 清理Docker资源...
docker container prune -f
docker network prune -f

echo.
echo ✅ 所有服务已停止！
echo.
echo 📝 如需完全清理，可运行：
echo   docker system prune -a
echo.

pause
