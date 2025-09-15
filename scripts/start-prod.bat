@echo off
REM 生产环境启动脚本

echo 🚀 启动YueHeApp生产环境...

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

REM 拉取最新镜像
echo 📥 拉取最新镜像...
docker-compose pull

REM 启动服务
echo 🔨 启动服务...
docker-compose up -d

REM 等待服务启动
echo ⏳ 等待服务启动...
timeout /t 60 /nobreak >nul

REM 检查服务状态
echo 🔍 检查服务状态...
docker-compose ps

REM 健康检查
echo 🏥 执行健康检查...
timeout /t 10 /nobreak >nul
curl -f http://localhost:8080/actuator/health >nul 2>&1 || echo ❌ 后端健康检查失败
curl -f http://localhost:3000 >nul 2>&1 || echo ❌ 前端健康检查失败

REM 显示访问信息
echo.
echo ✅ 生产环境启动完成！
echo 🌐 应用地址: http://localhost
echo 🔧 API地址: http://localhost/api
echo 📊 监控地址: http://localhost:8080/actuator/prometheus
echo.
echo 📝 查看日志: docker-compose logs -f
echo 🛑 停止服务: docker-compose down
pause
