# 端口变更总结

## 📋 变更概述

为了避免与其他应用的端口冲突，已将YueHeApp的所有端口都加10。

## 🔄 端口映射表

| 服务 | 原端口 | 新端口 | 说明 |
|------|--------|--------|------|
| MySQL | 3306 | 3316 | 数据库服务 |
| Redis | 6379 | 6389 | 缓存服务 |
| 后端API | 8080 | 8090 | Spring Boot应用 |
| 前端应用 | 3000 | 3010 | Next.js应用 |
| Nginx HTTP | 80 | 90 | 反向代理HTTP |
| Nginx HTTPS | 443 | 453 | 反向代理HTTPS |
| Nginx健康检查 | 8081 | 8091 | 健康检查端口 |

## 📁 修改的文件

### Docker配置文件
- ✅ `docker-compose.yml` - 主配置文件
- ✅ `docker-compose.dev.yml` - 开发环境配置
- ✅ `docker-compose.prod.yml` - 生产环境配置

### 启动脚本
- ✅ `quick-start.bat` - 快速启动脚本
- ✅ `quick-stop.bat` - 快速停止脚本
- ✅ `health-check.bat` - 健康检查脚本
- ✅ `scripts/start-dev.bat` - 开发环境启动脚本
- ✅ `scripts/start-prod.bat` - 生产环境启动脚本
- ✅ `scripts/test-system.bat` - 系统测试脚本

### 文档文件
- ✅ `QUICK_START_README.md` - 快速启动说明文档

## 🌐 新的访问地址

### 直接访问
- **前端应用**: http://localhost:3010
- **后端API**: http://localhost:8090/api
- **健康检查**: http://localhost:8090/actuator/health

### 通过Nginx访问（Docker模式）
- **应用首页**: http://localhost:90
- **API接口**: http://localhost:90/api

### 数据库连接
- **MySQL**: localhost:3316 (用户名: root, 密码: root123)
- **Redis**: localhost:6389

## ⚠️ 注意事项

1. **容器内部端口不变**: 容器内部的端口配置保持不变，只修改了外部映射端口
2. **环境变量更新**: 前端的环境变量 `NEXT_PUBLIC_API_URL` 已更新为新的后端地址
3. **Nginx配置**: Nginx内部代理配置保持不变，因为容器间通信使用内部端口
4. **数据库连接**: 应用内部的数据库连接配置保持不变，因为使用容器间网络

## 🚀 使用方法

现在可以直接运行 `quick-start.bat` 来启动所有服务，所有端口都已更新为新的配置。

## 🔍 验证方法

运行 `health-check.bat` 可以检查所有服务是否在新端口上正常运行。

---

**变更时间**: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**变更原因**: 避免与其他应用的端口冲突
