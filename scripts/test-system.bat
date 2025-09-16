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
echo   前端: http://localhost:3010
echo   后端API: http://localhost:8090
echo.
echo 测试账户：
echo   管理员: admin / admin123
echo   专家: expert / expert123  
echo   操作员: operator / operator123
echo.
echo 日志文件位置：
echo   后端API日志: logs\backend\api\
echo   后端Web日志: logs\backend\web\
echo   前端日志: logs\frontend\nextjs\
echo   数据库日志: logs\database\
echo.
echo 日志工具：
echo   查看日志: scripts\view-logs.bat
echo   监控日志: scripts\monitor-logs.bat
echo   清理日志: scripts\clean-logs.bat
echo.
echo 按任意键退出...
pause > nul
