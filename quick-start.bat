@echo off
REM YueHeApp 快速启动脚本 - 启动所有服务
REM 包括：基础架构服务(MySQL, Redis) + 后端服务 + 前端服务

echo.
echo ========================================
echo 🚀 YueHeApp 快速启动脚本
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

REM 检查WSL Docker是否运行
echo 🔍 检查WSL Docker状态...
wsl docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ WSL Docker未运行
    echo 💡 请确保WSL Docker正在运行
    echo 💡 启动命令: wsl -d docker-desktop
    echo 💡 或运行: start-wsl-docker.bat
    pause
    exit /b 1
) else (
    echo ✅ WSL Docker运行正常
    set USE_WSL_DOCKER=true
)

REM 检查Java环境（用于本地开发模式）
echo 🔍 检查Java环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Java未安装，将使用Docker模式启动
    set USE_DOCKER=true
) else (
    echo ✅ Java环境正常
    set USE_DOCKER=false
)

REM 检查Node.js环境（用于本地开发模式）
echo 🔍 检查Node.js环境...
node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ⚠️  Node.js未安装，将使用Docker模式启动
    set USE_DOCKER=true
) else (
    echo ✅ Node.js环境正常
)

echo.
echo 📋 启动模式选择：
echo 1. Docker模式 - 所有服务在容器中运行
echo 2. 混合模式 (默认) - 基础架构用Docker，应用服务本地运行
echo 3. 本地模式 - 所有服务本地运行 (需要完整环境)
echo.

set /p choice="请选择启动模式 (1-3, 默认2): "
if "%choice%"=="" set choice=2

if "%choice%"=="1" goto docker_mode
if "%choice%"=="2" goto hybrid_mode
if "%choice%"=="3" goto local_mode
goto hybrid_mode

:docker_mode
echo.
echo 🐳 使用Docker模式启动所有服务...
echo.

REM 停止现有容器
echo 🛑 停止现有容器...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose down >nul 2>&1
) else (
    docker-compose down >nul 2>&1
)

REM 清理未使用的镜像和容器
echo 🧹 清理Docker资源...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker system prune -f >nul 2>&1
) else (
    docker system prune -f >nul 2>&1
)

REM 启动基础架构服务
echo 🏗️  启动基础架构服务 (MySQL + Redis)...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml up -d mysql redis
) else (
    docker-compose -f docker-compose.dev.yml up -d mysql redis
)

REM 等待基础服务启动
echo ⏳ 等待基础服务启动...
timeout /t 15 /nobreak >nul

REM 启动应用服务
echo 🚀 启动应用服务 (后端 + 前端 + Nginx)...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose up --build -d
) else (
    docker-compose up --build -d
)

REM 等待所有服务启动
echo ⏳ 等待所有服务启动...
timeout /t 30 /nobreak >nul

goto check_services

:hybrid_mode
echo.
echo 🔄 使用混合模式启动服务 (WSL Docker + Windows本地)...
echo.

REM 停止现有容器
echo 🛑 停止现有容器...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml down >nul 2>&1
) else (
    docker-compose -f docker-compose.dev.yml down >nul 2>&1
)

REM 启动基础架构服务
echo 🏗️  启动基础架构服务 (MySQL + Redis)...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml up -d mysql redis
) else (
    docker-compose -f docker-compose.dev.yml up -d mysql redis
)

REM 等待基础服务启动
echo ⏳ 等待基础服务启动...
timeout /t 15 /nobreak >nul

REM 检查基础服务状态
echo 🔍 检查基础服务状态...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml ps
) else (
    docker-compose -f docker-compose.dev.yml ps
)

REM 启动后端服务
echo 🔧 启动后端服务 (Windows本地)...
cd yuehe-backend
echo 📝 后端服务将在新窗口中启动...
start "YueHe Backend - Spring Boot" cmd /k "echo 启动YueHe后端服务... && mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

REM 等待后端启动
echo ⏳ 等待后端服务启动 (约30秒)...
timeout /t 30 /nobreak >nul

REM 启动前端服务
echo 🌐 启动前端服务 (Windows本地)...
cd frontend
echo 📝 前端服务将在新窗口中启动...
start "YueHe Frontend - Next.js" cmd /k "echo 启动YueHe前端服务... && npm run dev"
cd ..

REM 等待前端启动
echo ⏳ 等待前端服务启动 (约20秒)...
timeout /t 20 /nobreak >nul

echo.
echo ✅ 混合模式启动完成！
echo 📝 服务说明：
echo   - 基础架构 (MySQL/Redis): WSL Docker容器
echo   - 后端服务: Windows本地 (Spring Boot)
echo   - 前端服务: Windows本地 (Next.js)
echo.

goto check_services

:local_mode
echo.
echo 💻 使用本地模式启动服务...
echo.

REM 检查Maven
echo 🔍 检查Maven环境...
mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Maven未安装，无法使用本地模式
    echo 💡 请安装Maven或选择其他模式
    pause
    exit /b 1
)

REM 检查npm
echo 🔍 检查npm环境...
npm --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ npm未安装，无法使用本地模式
    echo 💡 请安装Node.js或选择其他模式
    pause
    exit /b 1
)

REM 启动基础架构服务
echo 🏗️  启动基础架构服务 (MySQL + Redis)...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml up -d mysql redis
) else (
    docker-compose -f docker-compose.dev.yml up -d mysql redis
)

REM 等待基础服务启动
echo ⏳ 等待基础服务启动...
timeout /t 15 /nobreak >nul

REM 安装前端依赖
echo 📦 安装前端依赖...
cd frontend
npm install
cd ..

REM 启动后端服务
echo 🔧 启动后端服务...
cd yuehe-backend
start "YueHe Backend" cmd /k "mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

REM 等待后端启动
echo ⏳ 等待后端服务启动...
timeout /t 20 /nobreak >nul

REM 启动前端服务
echo 🌐 启动前端服务...
cd frontend
start "YueHe Frontend" cmd /k "npm run dev"
cd ..

REM 等待前端启动
echo ⏳ 等待前端服务启动...
timeout /t 15 /nobreak >nul

goto check_services

:check_services
echo.
echo 🔍 检查服务状态...

REM 检查MySQL
echo 📊 检查MySQL服务...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker exec yuehe-mysql-dev mysqladmin ping -h localhost -u root -proot123 >nul 2>&1
) else (
    docker exec yuehe-mysql-dev mysqladmin ping -h localhost -u root -proot123 >nul 2>&1
)
if %errorlevel% equ 0 (
    echo ✅ MySQL服务正常
) else (
    echo ❌ MySQL服务异常
)

REM 检查Redis
echo 📊 检查Redis服务...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker exec yuehe-redis-dev redis-cli ping >nul 2>&1
) else (
    docker exec yuehe-redis-dev redis-cli ping >nul 2>&1
)
if %errorlevel% equ 0 (
    echo ✅ Redis服务正常
) else (
    echo ❌ Redis服务异常
)

REM 检查后端服务
echo 📊 检查后端服务...
curl -f http://localhost:8090/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 后端服务正常
) else (
    echo ❌ 后端服务异常
)

REM 检查前端服务
echo 📊 检查前端服务...
curl -f http://localhost:3010 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 前端服务正常
) else (
    echo ❌ 前端服务异常
)

echo.
echo ========================================
echo ✅ YueHeApp 启动完成！
echo ========================================
echo.
echo 🌐 服务访问地址：
echo   前端应用: http://localhost:3010
echo   后端API:  http://localhost:8090/api
echo   健康检查: http://localhost:8090/actuator/health
echo   数据库:   localhost:3316 (root/root123)
echo   Redis:    localhost:6389
echo.

if "%choice%"=="1" (
    echo 🐳 Docker模式 - 所有服务在容器中运行
    echo.
    echo 📝 常用命令：
    echo   查看日志: docker-compose logs -f
    echo   停止服务: docker-compose down
    echo   重启服务: docker-compose restart
    echo   查看状态: docker-compose ps
) else (
    echo 💻 混合/本地模式 - 应用服务本地运行
    echo.
    echo 📝 管理说明：
    echo   - 基础服务(MySQL/Redis)在Docker中运行
    echo   - 应用服务在本地运行，可独立重启
    echo   - 停止基础服务: docker-compose -f docker-compose.dev.yml down
)

echo.
echo 🎉 现在可以开始使用YueHeApp了！
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
