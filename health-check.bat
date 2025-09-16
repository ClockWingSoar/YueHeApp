@echo off
REM YueHeApp 健康检查脚本

echo.
echo ========================================
echo 🏥 YueHeApp 健康检查
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

set /a total_checks=0
set /a passed_checks=0

echo 🔍 开始健康检查...
echo.

REM 检查Docker是否运行
set /a total_checks+=1
echo [%total_checks%] 检查Docker状态...
docker info >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Docker运行正常
    set /a passed_checks+=1
    set USE_WSL_DOCKER=false
) else (
    wsl docker info >nul 2>&1
    if %errorlevel% equ 0 (
        echo ✅ WSL Docker运行正常
        set /a passed_checks+=1
        set USE_WSL_DOCKER=true
    ) else (
        echo ❌ Docker未运行
    )
)

REM 检查MySQL服务
set /a total_checks+=1
echo [%total_checks%] 检查MySQL服务...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker exec yuehe-mysql-dev mysqladmin ping -h localhost -u root -proot123 >nul 2>&1
) else (
    docker exec yuehe-mysql-dev mysqladmin ping -h localhost -u root -proot123 >nul 2>&1
)
if %errorlevel% equ 0 (
    echo ✅ MySQL服务正常
    set /a passed_checks+=1
) else (
    echo ❌ MySQL服务异常
)

REM 检查Redis服务
set /a total_checks+=1
echo [%total_checks%] 检查Redis服务...
if "%USE_WSL_DOCKER%"=="true" (
    wsl docker exec yuehe-redis-dev redis-cli ping >nul 2>&1
) else (
    docker exec yuehe-redis-dev redis-cli ping >nul 2>&1
)
if %errorlevel% equ 0 (
    echo ✅ Redis服务正常
    set /a passed_checks+=1
) else (
    echo ❌ Redis服务异常
)

REM 检查后端服务
set /a total_checks+=1
echo [%total_checks%] 检查后端服务...
curl -f http://localhost:8090/actuator/health >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 后端服务正常
    set /a passed_checks+=1
) else (
    echo ❌ 后端服务异常
)

REM 检查前端服务
set /a total_checks+=1
echo [%total_checks%] 检查前端服务...
curl -f http://localhost:3010 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ 前端服务正常
    set /a passed_checks+=1
) else (
    echo ❌ 前端服务异常
)

REM 检查Nginx服务（如果使用Docker模式）
set /a total_checks+=1
echo [%total_checks%] 检查Nginx服务...
curl -f http://localhost:90 >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ Nginx服务正常
    set /a passed_checks+=1
) else (
    echo ⚠️  Nginx服务未运行（可能使用混合模式）
)

echo.
echo ========================================
echo 📊 健康检查结果
echo ========================================
echo.
echo 总检查项: %total_checks%
echo 通过检查: %passed_checks%
echo 失败检查: %total_checks%-%passed_checks%

if %passed_checks% equ %total_checks% (
    echo.
    echo 🎉 所有服务运行正常！
) else (
    echo.
    echo ⚠️  部分服务存在问题，请检查日志
    echo.
    echo 📝 查看日志命令：
    echo   docker-compose logs -f
    echo   docker-compose logs -f backend
    echo   docker-compose logs -f frontend
)

echo.
echo 🌐 服务访问地址：
echo   前端应用: http://localhost:3010
echo   后端API:  http://localhost:8090/api
echo   健康检查: http://localhost:8090/actuator/health
echo   应用首页: http://localhost:90 (如果使用Nginx)

echo.
pause
