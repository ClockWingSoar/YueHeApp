@echo off
REM YueHeApp 启动脚本 - 简化版本

echo.
echo ========================================
echo 🚀 YueHeApp 启动脚本
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 启动模式：混合开发模式
echo   - 基础架构: WSL Docker容器 (MySQL + Redis)
echo   - 后端服务: Windows本地 (Spring Boot) - 端口8090
echo   - Web服务:  Windows本地 (Spring Boot) - 端口9090
echo   - API服务:  Windows本地 (Spring Boot) - 端口9091
echo   - 前端服务: Windows本地 (Next.js) - 端口3010
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

REM 检查Java环境
echo 🔍 检查Java环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Java未安装，无法启动后端服务
    echo 💡 请安装Java 11或更高版本
    pause
    exit /b 1
)
echo ✅ Java环境正常

REM 检查Node.js环境
echo 🔍 检查Node.js环境...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Node.js未安装，无法启动前端服务
    echo 💡 请安装Node.js 16或更高版本
    pause
    exit /b 1
)
echo ✅ Node.js环境正常

echo.
echo 🛑 停止现有容器...
wsl docker-compose -f docker-compose.dev.yml down >nul 2>&1

echo.
echo 🏗️  启动基础架构服务 (MySQL + Redis)...
wsl docker-compose -f docker-compose.dev.yml up -d mysql redis

echo ⏳ 等待基础服务启动...
timeout /t 15 /nobreak >nul

echo 🔍 检查基础服务状态...
wsl docker-compose -f docker-compose.dev.yml ps

echo.
echo 🔧 启动后端服务 (Spring Boot)...
cd yuehe-backend
echo 📝 后端服务将在新窗口中启动...
start "YueHe Backend - Spring Boot" cmd /k "echo 启动YueHe后端服务... && echo 数据库: localhost:3316 && echo Redis: localhost:6389 && echo 端口: 8090 && echo. && mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

echo ⏳ 等待后端服务启动 (约30秒)...
timeout /t 30 /nobreak >nul

echo.
echo 🌐 启动Web服务 (Spring Boot)...
cd yuehe-web
echo 📝 Web服务将在新窗口中启动...
start "YueHe Web - Spring Boot" cmd /k "echo 启动YueHe Web服务... && echo 端口: 9090 && echo. && mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

echo ⏳ 等待Web服务启动 (约20秒)...
timeout /t 20 /nobreak >nul

echo.
echo 🔌 启动API服务 (Spring Boot)...
cd yuehe-api
echo 📝 API服务将在新窗口中启动...
start "YueHe API - Spring Boot" cmd /k "echo 启动YueHe API服务... && echo 端口: 9091 && echo. && mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

echo ⏳ 等待API服务启动 (约20秒)...
timeout /t 20 /nobreak >nul

echo.
echo 🌐 启动前端服务 (Next.js)...
cd frontend
echo 📝 前端服务将在新窗口中启动...
start "YueHe Frontend - Next.js" cmd /k "echo 启动YueHe前端服务... && echo API地址: http://localhost:8090/api && echo 端口: 3010 && echo. && npm run dev"
cd ..

echo ⏳ 等待前端服务启动 (约20秒)...
timeout /t 20 /nobreak >nul

echo.
echo ========================================
echo ✅ YueHeApp 启动完成！
echo ========================================
echo.
echo 🌐 服务访问地址：
echo   前端应用: http://localhost:3010
echo   后端服务: http://localhost:8090/api
echo   Web服务:  http://localhost:8081
echo   API服务:  http://localhost:9091
echo   健康检查: http://localhost:8090/actuator/health
echo   数据库:   localhost:3316 (root/root123)
echo   Redis:    localhost:6389
echo.
echo 📝 服务说明：
echo   - 基础架构 (MySQL/Redis): WSL Docker容器
echo   - 后端服务: Windows本地 (Spring Boot) - 端口8090
echo   - Web服务:  Windows本地 (Spring Boot) - 端口9090
echo   - API服务:  Windows本地 (Spring Boot) - 端口9091
echo   - 前端服务: Windows本地 (Next.js) - 端口3010
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
