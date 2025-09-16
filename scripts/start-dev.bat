@echo off
REM 开发环境启动脚本

echo 🚀 启动YueHeApp开发环境...

REM 检查Docker是否运行
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker未运行，请先启动Docker
    pause
    exit /b 1
)

REM 停止现有容器
echo 🛑 停止现有容器...
docker-compose down

REM 构建并启动服务
echo 🔨 构建并启动服务...
docker-compose up --build -d

REM 等待服务启动
echo ⏳ 等待服务启动...
timeout /t 30 /nobreak >nul

REM 检查服务状态
echo 🔍 检查服务状态...
docker-compose ps

REM 显示访问信息
echo.
echo ✅ 服务启动完成！
echo 🌐 前端地址: http://localhost:3010
echo 🔧 后端API: http://localhost:8090/api
echo 📊 健康检查: http://localhost:8090/actuator/health
echo 🗄️  数据库: localhost:3316
echo 🔴 Redis: localhost:6389
echo.
echo 📝 查看日志: docker-compose logs -f
echo 🛑 停止服务: docker-compose down
pause
