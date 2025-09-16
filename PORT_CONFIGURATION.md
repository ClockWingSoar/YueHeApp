# YueHeApp 端口配置说明

## 🔧 端口配置修复

### 问题描述
1. **前端端口问题** - Next.js默认使用3000端口，但配置为3010
2. **yuehe-web和yuehe-api未启动** - 这些模块需要单独启动
3. **端口冲突** - 需要统一端口配置

### ✅ 已修复的问题

#### 1. 前端端口配置
- **Next.js配置**: 在`next.config.js`中添加端口配置
- **package.json**: 更新dev脚本使用端口3010
- **启动脚本**: 所有脚本都使用端口3010

#### 2. 服务端口分配
| 服务 | 端口 | 说明 |
|------|------|------|
| MySQL | 3316 | WSL Docker容器 |
| Redis | 6389 | WSL Docker容器 |
| 后端服务 | 8090 | Spring Boot (yuehe-backend) |
| Web服务 | 8081 | Spring Boot (yuehe-web) |
| API服务 | 8082 | Spring Boot (yuehe-api) |
| 前端服务 | 3010 | Next.js (frontend) |
| Nginx | 90 | 反向代理 (Docker模式) |

#### 3. 启动脚本更新
- **start-yuehe.bat** - 启动所有服务
- **start-minimal.bat** - 只启动核心服务
- **start-docker-only.bat** - Docker模式启动

## 🚀 推荐启动方式

### 方式1: 最小化启动（推荐）
```bash
start-minimal.bat
```
- 只启动核心服务：MySQL + Redis + 后端 + 前端
- 避免编译问题
- 快速启动

### 方式2: 完整启动
```bash
start-yuehe.bat
```
- 启动所有服务：MySQL + Redis + 后端 + Web + API + 前端
- 需要解决编译问题

### 方式3: Docker模式
```bash
start-docker-only.bat
```
- 所有服务在Docker中运行
- 完全避免本地编译问题

## 🌐 服务访问地址

### 核心服务
- **前端应用**: http://localhost:3010
- **后端API**: http://localhost:8090/api
- **健康检查**: http://localhost:8090/actuator/health

### 完整服务
- **Web服务**: http://localhost:8081
- **API服务**: http://localhost:8082

### 基础服务
- **数据库**: localhost:3316
- **Redis**: localhost:6389

## 🔍 验证方法

### 检查服务状态
```bash
# 检查端口占用
netstat -ano | findstr :3010
netstat -ano | findstr :8090
netstat -ano | findstr :8081
netstat -ano | findstr :8082

# 检查服务响应
curl http://localhost:3010
curl http://localhost:8090/actuator/health
curl http://localhost:8081
curl http://localhost:8082
```

### 检查Docker容器
```bash
wsl docker ps
wsl docker-compose -f docker-compose.dev.yml ps
```

## 📝 配置文件更新

### 前端配置
- ✅ `frontend/next.config.js` - 添加端口配置
- ✅ `frontend/package.json` - 更新dev脚本

### 后端配置
- ✅ `yuehe-backend/src/main/resources/application-dev.yml` - 端口8090
- ✅ `yuehe-web/src/main/resources/application.properties` - 端口8081
- ✅ `yuehe-api/src/main/resources/application.properties` - 端口8082

### Docker配置
- ✅ `docker-compose.yml` - 端口映射
- ✅ `docker-compose.dev.yml` - 开发环境端口

## ⚠️ 注意事项

1. **端口冲突**: 确保端口3010、8090、8081、8082未被占用
2. **编译问题**: yuehe-web和yuehe-api可能有编译问题
3. **数据库连接**: 所有服务都连接到localhost:3316
4. **Redis连接**: 所有服务都连接到localhost:6389

---

**配置完成时间**: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**状态**: 端口配置已修复，建议使用最小化启动
