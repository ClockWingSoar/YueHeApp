# YueHeApp 快速启动指南

## 🚀 快速开始

### 一键启动所有服务

```bash
# 运行快速启动脚本（默认混合模式，自动启动WSL Docker）
quick-start.bat

# 或者直接运行混合开发模式
start-hybrid-dev.bat

# 或者使用WSL Docker专用快速启动
start-wsl-quick.bat
```

### 停止所有服务

```bash
# 运行快速停止脚本
quick-stop.bat

# 或者停止混合开发模式
stop-hybrid-dev.bat
```

### 健康检查

```bash
# 检查所有服务状态
health-check.bat
```

## 📋 启动模式说明

### 1. Docker模式
- **特点**: 所有服务在Docker容器中运行
- **优点**: 环境一致，部署简单，隔离性好
- **适用**: 生产环境、演示环境

### 2. 混合模式（默认推荐）
- **特点**: 基础架构服务（MySQL、Redis）用Docker，应用服务本地运行
- **优点**: 便于调试，开发效率高，支持热重载
- **适用**: 开发环境（默认）
- **架构**: WSL Docker + Windows本地开发

### 3. 本地模式
- **特点**: 所有服务本地运行
- **优点**: 性能最佳，调试最方便
- **适用**: 开发环境（需要完整环境配置）

## 🏗️ 服务架构

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   前端服务      │    │   后端服务      │    │   基础架构      │
│   (Next.js)     │    │   (Spring Boot) │    │   (MySQL+Redis) │
│   Port: 3010    │    │   Port: 8090    │    │   Port: 3316/6389│
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │                       │
         └───────────────────────┼───────────────────────┘
                                 │
                    ┌─────────────────┐
                    │   Nginx代理     │
                    │   Port: 90/453  │
                    └─────────────────┘
```

## 🌐 服务访问地址

| 服务 | 地址 | 说明 |
|------|------|------|
| 前端应用 | http://localhost:3010 | Next.js前端应用 |
| 后端API | http://localhost:8090/api | Spring Boot REST API |
| 健康检查 | http://localhost:8090/actuator/health | 后端服务健康状态 |
| 应用首页 | http://localhost:90 | 通过Nginx访问（Docker模式） |
| 数据库 | localhost:3316 | MySQL数据库 |
| Redis | localhost:6389 | Redis缓存 |

## 🔧 环境要求

### Docker模式
- Docker Desktop
- 8GB+ 内存
- 10GB+ 磁盘空间

### 混合模式（默认）
- Docker Desktop (WSL2) 或 WSL Docker
- Java 11+ (Windows)
- Maven 3.6+ (Windows)
- Node.js 16+ (Windows)
- npm 8+ (Windows)

### 本地模式
- Java 11+
- Maven 3.6+
- Node.js 16+
- npm 8+
- MySQL 8.0+
- Redis 6.0+

## 🚀 混合模式详细说明

### 启动流程
1. **基础架构服务**: 在WSL Docker中启动MySQL和Redis
2. **后端服务**: 在Windows本地启动Spring Boot应用
3. **前端服务**: 在Windows本地启动Next.js应用

### 优势
- ✅ **开发效率高**: 支持热重载，代码修改即时生效
- ✅ **调试方便**: 可以直接在IDE中调试Java和JavaScript代码
- ✅ **资源占用少**: 只有基础服务在Docker中运行
- ✅ **环境隔离**: 数据库和缓存服务隔离，不影响本地环境

### 端口配置
- **MySQL**: localhost:3316 (Docker容器)
- **Redis**: localhost:6389 (Docker容器)
- **后端API**: localhost:8090 (Windows本地)
- **前端应用**: localhost:3010 (Windows本地)

## 📝 常用命令

### Docker命令
```bash
# 查看容器状态
docker-compose ps

# 查看日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend

# 重启服务
docker-compose restart

# 停止服务
docker-compose down

# 清理资源
docker system prune -a
```

### 开发命令
```bash
# 后端开发
cd yuehe-backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev

# 前端开发
cd frontend
npm run dev

# 前端构建
npm run build
npm run start
```

## 🐛 故障排除

### 常见问题

1. **Docker未运行**
   - 启动Docker Desktop
   - 或运行：`start-wsl-docker.bat`
   - 等待Docker完全启动

2. **WSL Docker问题**
   - 检查WSL状态：`wsl --status`
   - 启动Docker Desktop：`wsl -d docker-desktop`
   - 或运行：`start-wsl-docker.bat`

3. **端口被占用**
   - 检查端口占用：`netstat -ano | findstr :3010`
   - 停止占用端口的进程

4. **服务启动失败**
   - 查看日志：`wsl docker-compose logs -f`
   - 检查配置文件
   - 重启服务

5. **数据库连接失败**
   - 检查MySQL容器状态：`wsl docker ps`
   - 验证连接参数
   - 查看数据库日志：`wsl docker logs yuehe-mysql-dev`

### 日志位置

- 应用日志：`logs/` 目录
- Docker日志：`docker-compose logs`
- 系统日志：Windows事件查看器

## 🔄 更新和维护

### 更新代码
```bash
# 拉取最新代码
git pull

# 重新构建并启动
quick-start.bat
```

### 数据备份
```bash
# 备份数据库
docker exec yuehe-mysql-dev mysqldump -u root -proot123 yuehe > backup.sql

# 恢复数据库
docker exec -i yuehe-mysql-dev mysql -u root -proot123 yuehe < backup.sql
```

## 📞 技术支持

如有问题，请查看：
1. 项目文档：`docs/` 目录
2. 日志文件：`logs/` 目录
3. 健康检查：`health-check.bat`

---

**注意**: 首次运行可能需要较长时间下载Docker镜像，请耐心等待。
