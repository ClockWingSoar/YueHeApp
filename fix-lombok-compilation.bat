@echo off
REM 修复Lombok编译错误的脚本

echo.
echo ========================================
echo 🔧 修复Lombok编译错误
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 修复步骤：
echo   1. 清理Maven缓存
echo   2. 重新编译项目
echo   3. 强制重新生成Lombok注解
echo.

echo 🧹 步骤1: 清理Maven缓存...
cd yuehe-backend
call mvn clean -q
if %errorlevel% neq 0 (
    echo ❌ Maven清理失败
    pause
    exit /b 1
)
echo ✅ Maven缓存清理完成

echo.
echo 🔄 步骤2: 强制重新编译...
call mvn compile -Dmaven.compiler.forceJavacCompilerUse=true -q
if %errorlevel% neq 0 (
    echo ❌ 编译失败，尝试其他方法...
    
    echo.
    echo 🔧 步骤3: 尝试跳过测试编译...
    call mvn compile -DskipTests -q
    if %errorlevel% neq 0 (
        echo ❌ 跳过测试编译也失败
        
        echo.
        echo 🔧 步骤4: 尝试强制重新生成Lombok注解...
        call mvn clean compile -Dmaven.compiler.forceJavacCompilerUse=true -Dlombok.addLombokGeneratedAnnotation=true -q
        if %errorlevel% neq 0 (
            echo ❌ 所有编译方法都失败
            echo.
            echo 💡 建议使用Docker模式启动：
            echo    start-docker-only.bat
            echo.
            pause
            exit /b 1
        )
    )
)

echo ✅ 编译成功！

echo.
echo 🚀 尝试启动后端服务...
call mvn spring-boot:run -Dspring-boot.run.profiles=dev -q
if %errorlevel% neq 0 (
    echo ❌ 后端服务启动失败
    echo.
    echo 💡 建议使用Docker模式启动：
    echo    start-docker-only.bat
    echo.
    pause
    exit /b 1
)

echo ✅ 后端服务启动成功！
echo.
echo 🌐 服务访问地址：
echo   后端API: http://localhost:8090/api
echo   健康检查: http://localhost:8090/actuator/health
echo.

pause
