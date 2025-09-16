@echo off
REM 启动WSL Docker服务

echo.
echo ========================================
echo 🐳 启动WSL Docker服务
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 🔍 检查WSL状态...
wsl --status >nul 2>&1
if %errorlevel% neq 0 (
    echo ❌ WSL未运行，请先启动WSL
    echo 💡 请运行: wsl --install
    pause
    exit /b 1
)
echo ✅ WSL运行正常

echo.
echo 🐳 启动Docker服务...
echo 💡 正在尝试多种方式启动Docker...

REM 方法1: 启动Docker Desktop
echo 📡 方法1: 启动Docker Desktop...
start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe" >nul 2>&1

REM 等待一段时间
echo ⏳ 等待Docker Desktop启动...
timeout /t 20 /nobreak >nul

REM 检查Docker状态
echo 🔍 检查Docker状态...
wsl docker info >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ WSL Docker运行正常
    echo.
    echo 🎉 Docker Desktop已成功启动！
    echo 💡 现在可以运行 quick-start.bat 启动YueHeApp
    goto :success
)

REM 方法2: 尝试启动WSL Docker Desktop
echo 📡 方法2: 尝试启动WSL Docker Desktop...
wsl -d docker-desktop >nul 2>&1

REM 等待一段时间
echo ⏳ 等待WSL Docker启动...
timeout /t 15 /nobreak >nul

REM 再次检查Docker状态
echo 🔍 重新检查Docker状态...
wsl docker info >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ WSL Docker运行正常
    echo.
    echo 🎉 WSL Docker已成功启动！
    echo 💡 现在可以运行 quick-start.bat 启动YueHeApp
    goto :success
)

REM 方法3: 尝试启动Docker服务
echo 📡 方法3: 尝试启动Docker服务...
wsl sudo service docker start >nul 2>&1

REM 等待一段时间
echo ⏳ 等待Docker服务启动...
timeout /t 10 /nobreak >nul

REM 最后检查Docker状态
echo 🔍 最后检查Docker状态...
wsl docker info >nul 2>&1
if %errorlevel% equ 0 (
    echo ✅ WSL Docker运行正常
    echo.
    echo 🎉 Docker服务已成功启动！
    echo 💡 现在可以运行 quick-start.bat 启动YueHeApp
    goto :success
)

REM 所有方法都失败
echo ❌ 所有启动方法都失败
echo 💡 请手动检查以下项目：
echo    1. Docker Desktop是否已安装
echo    2. WSL是否正确配置
echo    3. 防火墙是否阻止Docker
echo    4. 尝试手动启动: wsl -d docker-desktop
echo.
echo 💡 或者尝试重新安装Docker Desktop
goto :end

:success
echo.
echo 🚀 准备启动YueHeApp...
set /p start_app="是否现在启动YueHeApp？(y/n, 默认y): "
if "%start_app%"=="" set start_app=y
if /i "%start_app%"=="y" (
    echo 🚀 启动YueHeApp...
    call quick-start.bat
)

:end
echo.
pause
