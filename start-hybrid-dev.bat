@echo off
REM YueHeApp 混合开发模式启动脚本
REM 基础架构服务 (MySQL, Redis) 在WSL Docker中运行
REM 应用服务 (后端, 前端) 在Windows本地运行

echo.
echo ========================================
echo 🚀 YueHeApp 混合开发模式启动
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 启动模式：混合开发模式
echo   - 基础架构: WSL Docker容器 (MySQL + Redis)
echo   - 后端服务: Windows本地 (Spring Boot)
echo   - 前端服务: Windows本地 (Next.js)
echo.

REM 检查Docker是否运行
echo 🔍 检查Docker状态...
docker info >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ Docker未运行，尝试检查WSL Docker...
    wsl docker info >nul 2>&1
    if %errorlevel% neq 0 (
        echo ❌ WSL Docker也未运行，尝试启动WSL Docker...
        echo 🚀 正在启动WSL Docker服务...
        
        REM 尝试启动WSL Docker Desktop
        echo 📡 启动Docker Desktop...
        start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe" >nul 2>&1
        
        REM 等待Docker启动
        echo ⏳ 等待Docker启动 (约30秒)...
        timeout /t 30 /nobreak >nul
        
        REM 再次检查WSL Docker
        echo 🔍 重新检查WSL Docker状态...
        wsl docker info >nul 2>&1
        if %errorlevel% neq 0 (
            echo ❌ WSL Docker启动失败
            echo 💡 请手动启动Docker Desktop或检查WSL配置
            echo 💡 手动启动命令: wsl -d docker-desktop
            echo 💡 或运行: start-wsl-docker.bat
            pause
            exit /b 1
        ) else (
            echo ✅ WSL Docker启动成功
            set USE_WSL_DOCKER=true
        )
    ) else (
        echo ✅ WSL Docker运行正常
        set USE_WSL_DOCKER=true
    )
) else (
    echo ✅ Docker运行正常
    set USE_WSL_DOCKER=false
)

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
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml down >nul 2>&1
) else (
    docker-compose -f docker-compose.dev.yml down >nul 2>&1
)

echo.
echo 🏗️  启动基础架构服务 (MySQL + Redis)...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml up -d mysql redis
) else (
    docker-compose -f docker-compose.dev.yml up -d mysql redis
)

echo ⏳ 等待基础服务启动...
timeout /t 15 /nobreak >nul

echo 🔍 检查基础服务状态...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker-compose -f docker-compose.dev.yml ps
) else (
    docker-compose -f docker-compose.dev.yml ps
)

echo.
echo 🔧 启动后端服务 (Spring Boot)...
cd yuehe-backend
echo 📝 后端服务将在新窗口中启动...
start "YueHe Backend - Spring Boot" cmd /k "echo 启动YueHe后端服务... && echo 数据库: localhost:3316 && echo Redis: localhost:6389 && echo 端口: 8090 && echo. && mvn spring-boot:run -Dspring-boot.run.profiles=dev"
cd ..

echo ⏳ 等待后端服务启动 (约30秒)...
timeout /t 30 /nobreak >nul

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
echo ✅ 混合开发模式启动完成！
echo ========================================
echo.
echo 🌐 服务访问地址：
echo   前端应用: http://localhost:3010
echo   后端API:  http://localhost:8090/api
echo   健康检查: http://localhost:8090/actuator/health
echo   数据库:   localhost:3316 (root/root123)
echo   Redis:    localhost:6389
echo.
echo 📝 服务说明：
echo   - 基础架构 (MySQL/Redis): WSL Docker容器
echo   - 后端服务: Windows本地 (Spring Boot)
echo   - 前端服务: Windows本地 (Next.js)
echo.
echo 🎯 开发提示：
echo   - 后端日志: 查看后端窗口
echo   - 前端日志: 查看前端窗口
echo   - 数据库管理: 使用localhost:3316
echo   - Redis管理: 使用localhost:6389
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
