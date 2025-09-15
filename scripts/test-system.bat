@echo off
echo 启动悦和国际美容院管理系统测试...

echo.
echo 1. 启动后端API服务...
cd /d "%~dp0.."
start "YueHe API" cmd /k "cd yuehe-api && mvn spring-boot:run -Dspring-boot.run.profiles=dev"

echo.
echo 2. 等待后端服务启动...
timeout /t 10 /nobreak > nul

echo.
echo 3. 启动前端服务...
start "YueHe Frontend" cmd /k "cd frontend && npm run dev"

echo.
echo 4. 系统启动完成！
echo.
echo 访问地址：
echo   前端: http://localhost:3000
echo   后端API: http://localhost:8080
echo.
echo 测试账户：
echo   管理员: admin / admin123
echo   专家: expert / expert123  
echo   操作员: operator / operator123
echo.
echo 按任意键退出...
pause > nul
