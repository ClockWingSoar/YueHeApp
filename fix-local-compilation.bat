@echo off
REM 修复本地编译问题的脚本

echo.
echo ========================================
echo 🔧 修复本地编译问题
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 修复步骤：
echo   1. 清理Maven缓存
echo   2. 重新编译后端服务
echo   3. 修复Lombok注解问题
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
call mvn compile -Dmaven.compiler.forceJavacCompilerUse=true -Dlombok.addLombokGeneratedAnnotation=true -q
if %errorlevel% neq 0 (
    echo ❌ 编译失败，尝试其他方法...
    
    echo.
    echo 🔧 步骤3: 尝试跳过测试编译...
    call mvn compile -DskipTests -q
    if %errorlevel% neq 0 (
        echo ❌ 跳过测试编译也失败
        
        echo.
        echo 🔧 步骤4: 尝试降级Lombok版本...
        echo 正在修改pom.xml中的Lombok版本...
        powershell -Command "(Get-Content pom.xml) -replace '<lombok.version>1.18.34</lombok.version>', '<lombok.version>1.18.30</lombok.version>' | Set-Content pom.xml"
        call mvn clean compile -q
        if %errorlevel% neq 0 (
            echo ❌ 所有编译方法都失败
            echo.
            echo 💡 建议：
            echo   1. 检查Java版本是否为17
            echo   2. 检查Maven版本是否为3.6+
            echo   3. 使用Docker模式启动：start-docker-quick.bat
            echo.
            pause
            exit /b 1
        )
    )
)

echo ✅ 后端编译成功！

echo.
echo 🔄 步骤5: 编译其他服务...
cd ../yuehe-web
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo ⚠️  Web服务编译失败，但可以继续
)
cd ../yuehe-api
call mvn clean compile -q
if %errorlevel% neq 0 (
    echo ⚠️  API服务编译失败，但可以继续
)
cd ..

echo.
echo ✅ 编译修复完成！
echo.
echo 🚀 现在可以启动本地开发服务：
echo   start-yuehe.bat
echo.
echo 🌐 服务端口：
echo   后端服务: 8090
echo   Web服务:  9090
echo   API服务:  9091
echo   前端服务: 3010
echo.

pause
