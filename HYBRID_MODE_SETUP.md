# YueHeApp 混合开发模式设置完成

## ✅ 配置完成

已成功将YueHeApp配置为默认使用混合开发模式，具体配置如下：

## 🏗️ 架构设计

```
┌─────────────────────────────────────────────────────────────┐
│                    Windows 主机                            │
├─────────────────────────────────────────────────────────────┤
│  WSL2 Docker                    │  Windows 本地开发        │
│  ┌─────────────────┐            │  ┌─────────────────┐      │
│  │   MySQL:3316    │            │  │  Spring Boot    │      │
│  │   Redis:6389    │            │  │  Port: 8090     │      │
│  └─────────────────┘            │  └─────────────────┘      │
│                                 │  ┌─────────────────┐      │
│                                 │  │   Next.js       │      │
│                                 │  │   Port: 3010    │      │
│                                 │  └─────────────────┘      │
└─────────────────────────────────────────────────────────────┘
```

## 🚀 启动方式

### 方式1: 快速启动（推荐）
```bash
# 直接运行，默认选择混合模式
quick-start.bat
```

### 方式2: 专用混合模式脚本
```bash
# 专门为混合模式设计的启动脚本
start-hybrid-dev.bat
```

## 📋 服务配置

### 基础架构服务（WSL Docker）
- **MySQL**: localhost:3316
- **Redis**: localhost:6389
- **容器**: yuehe-mysql-dev, yuehe-redis-dev

### 应用服务（Windows本地）
- **后端API**: localhost:8090
- **前端应用**: localhost:3010
- **进程**: 在独立的PowerShell窗口中运行

## 🔧 配置文件更新

### 后端配置
- ✅ `yuehe-backend/src/main/resources/application-dev.yml`
  - 数据库端口: 3306 → 3316
  - Redis端口: 6379 → 6389
  - 服务端口: 8080 → 8090

### 前端配置
- ✅ `frontend/src/lib/api.ts`
  - API地址: localhost:8080 → localhost:8090

### Docker配置
- ✅ `docker-compose.dev.yml`
  - MySQL端口映射: 3306 → 3316
  - Redis端口映射: 6379 → 6389

## 🌐 访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端应用 | http://localhost:3010 | Next.js开发服务器 |
| 后端API | http://localhost:8090/api | Spring Boot REST API |
| 健康检查 | http://localhost:8090/actuator/health | 后端服务状态 |
| 数据库 | localhost:3316 | MySQL数据库 |
| Redis | localhost:6389 | Redis缓存 |

## 🎯 开发优势

1. **热重载支持**: 前端和后端都支持代码修改后自动重载
2. **调试便利**: 可以直接在IDE中设置断点调试
3. **资源优化**: 只有基础服务在Docker中，减少资源占用
4. **环境隔离**: 数据库和缓存服务隔离，不影响本地环境
5. **开发效率**: 支持现代开发工具链和调试方式

## 📝 使用说明

1. **启动服务**: 运行 `quick-start.bat` 或 `start-hybrid-dev.bat`
2. **开发调试**: 在IDE中直接修改代码，服务会自动重载
3. **查看日志**: 后端和前端服务在独立窗口中显示日志
4. **停止服务**: 运行 `stop-hybrid-dev.bat` 或手动关闭服务窗口

## 🔍 故障排除

### 常见问题
1. **端口冲突**: 确保3316、6389、8090、3010端口未被占用
2. **Docker未启动**: 确保Docker Desktop正在运行
3. **Java/Node.js未安装**: 确保已安装Java 11+和Node.js 16+

### 检查命令
```bash
# 检查端口占用
netstat -ano | findstr :3316
netstat -ano | findstr :6389
netstat -ano | findstr :8090
netstat -ano | findstr :3010

# 检查Docker状态
docker info

# 检查服务状态
health-check.bat
```

---

**配置完成时间**: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**默认启动模式**: 混合开发模式 (WSL Docker + Windows本地)
