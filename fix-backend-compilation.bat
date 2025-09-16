@echo off
REM 修复后端编译问题

echo.
echo ========================================
echo 🔧 修复后端编译问题
echo ========================================
echo.

REM 设置编码为UTF-8
chcp 65001 >nul 2>&1

echo 📋 修复步骤：
echo 1. 更新Lombok版本
echo 2. 修复MySQL连接器依赖
echo 3. 修复Hibernate兼容性问题
echo 4. 重新编译项目
echo.

echo 🔧 步骤1: 更新Lombok版本...
powershell -Command "(Get-Content 'pom.xml') -replace '<lombok.version>1.18.30</lombok.version>', '<lombok.version>1.18.34</lombok.version>' | Set-Content 'pom.xml'"

echo 🔧 步骤2: 修复MySQL连接器依赖...
powershell -Command "(Get-Content 'yuehe-backend/pom.xml') -replace 'mysql-connector-java', 'mysql-connector-j' | Set-Content 'yuehe-backend/pom.xml'"

echo 🔧 步骤3: 更新Lombok版本在backend pom中...
powershell -Command "(Get-Content 'yuehe-backend/pom.xml') -replace '1.18.30', '1.18.34' | Set-Content 'yuehe-backend/pom.xml'"

echo 🔧 步骤4: 清理并重新编译...
cd yuehe-backend
mvn clean compile -DskipTests

if %errorlevel% equ 0 (
    echo ✅ 编译成功！
    echo.
    echo 🚀 现在可以启动后端服务了
    echo 运行: mvn spring-boot:run -Dspring-boot.run.profiles=dev
) else (
    echo ❌ 编译失败，请检查错误信息
    echo.
    echo 💡 可能的解决方案：
    echo 1. 检查Java版本是否为17
    echo 2. 检查Maven版本是否为3.6+
    echo 3. 尝试删除target目录后重新编译
    echo 4. 检查IDE是否正确配置了Lombok
)

cd ..
echo.
pause
