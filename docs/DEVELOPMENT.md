# 开发环境设置指南

## 环境配置

本项目支持两种运行模式：

### 1. 开发模式（推荐）
- 数据库服务使用Docker运行
- 前端和后端在本地运行
- 支持热重载和快速开发

### 2. 生产模式
- 所有服务都使用Docker运行
- 适合部署和测试完整环境

## 开发环境启动步骤

### 1. 启动数据库服务

```bash
# 使用开发环境Docker Compose
./scripts/start-dev.sh

# 或者手动启动
docker-compose -f docker-compose.dev.yml up -d
```

### 2. 启动后端服务

```bash
cd yuehe-backend
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

### 3. 启动前端服务

```bash
cd frontend
npm install
npm run dev
```

## 访问地址

- **前端**: http://localhost:3000
- **后端API**: http://localhost:8080/api
- **数据库**: localhost:3306 (用户: yuehe, 密码: yuehe123)
- **Redis**: localhost:6379

## 环境配置文件

### 后端配置
- 开发环境: `yuehe-backend/src/main/resources/application-dev.yml`
- 生产环境: `yuehe-backend/src/main/resources/application-docker.yml`

### 前端配置
- 环境变量: `frontend/env.example`
- 复制为 `.env.local` 进行本地配置

## 常用命令

### 数据库管理
```bash
# 查看数据库日志
docker-compose -f docker-compose.dev.yml logs -f mysql

# 重启数据库
docker-compose -f docker-compose.dev.yml restart mysql

# 停止所有服务
docker-compose -f docker-compose.dev.yml down
```

### 开发调试
```bash
# 后端热重载（如果配置了spring-boot-devtools）
# 修改代码后自动重启

# 前端热重载
# 修改代码后自动刷新浏览器
```

## 故障排除

### 端口冲突
如果遇到端口冲突，可以修改以下配置：
- 数据库端口: `docker-compose.dev.yml` 中的 `3306:3306`
- 后端端口: `application-dev.yml` 中的 `server.port`
- 前端端口: `package.json` 中的 `dev` 脚本

### 数据库连接问题
1. 确保Docker服务正在运行
2. 检查数据库容器状态: `docker ps`
3. 查看数据库日志: `docker-compose -f docker-compose.dev.yml logs mysql`

### 前端构建问题
1. 清除node_modules: `rm -rf node_modules && npm install`
2. 清除Next.js缓存: `rm -rf .next`
3. 重新安装依赖: `npm install`
