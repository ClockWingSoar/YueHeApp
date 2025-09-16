# WSL Docker 自动启动功能

## ✅ 功能完成

我已经为所有启动脚本添加了WSL Docker自动启动功能，现在脚本会：

1. **自动检测Docker环境**
2. **自动启动WSL Docker**（如果未运行）
3. **自动启动基础架构服务**
4. **自动启动应用服务**

## 🚀 启动方式

### 方式1: 主启动脚本（推荐）
```bash
# 自动检测并启动WSL Docker + 所有服务
quick-start.bat
```

### 方式2: 混合模式脚本
```bash
# 专门为混合模式设计，自动启动WSL Docker
start-hybrid-dev.bat
```

### 方式3: WSL Docker专用脚本
```bash
# 专门为WSL Docker环境设计
start-wsl-quick.bat
```

### 方式4: WSL Docker启动脚本
```bash
# 只启动WSL Docker，然后可选择启动应用
start-wsl-docker.bat
```

## 🔧 自动启动流程

### 1. Docker检测和启动
```
检查Windows Docker → 检查WSL Docker → 自动启动Docker Desktop → 等待启动 → 验证状态
```

### 2. 基础架构服务启动
```
启动MySQL容器 → 启动Redis容器 → 等待服务就绪 → 检查服务状态
```

### 3. 应用服务启动
```
启动后端服务 (Spring Boot) → 启动前端服务 (Next.js) → 等待服务就绪
```

## 📋 脚本功能对比

| 脚本 | 自动启动Docker | 启动基础服务 | 启动应用服务 | 适用场景 |
|------|----------------|--------------|--------------|----------|
| `quick-start.bat` | ✅ | ✅ | ✅ | 通用启动 |
| `start-hybrid-dev.bat` | ✅ | ✅ | ✅ | 混合开发模式 |
| `start-wsl-quick.bat` | ✅ | ✅ | ✅ | WSL Docker专用 |
| `start-wsl-docker.bat` | ✅ | ❌ | ❌ | 只启动Docker |

## 🎯 使用建议

### 首次使用
```bash
# 推荐使用主启动脚本
quick-start.bat
```

### 日常开发
```bash
# 使用混合模式脚本
start-hybrid-dev.bat
```

### 只启动基础服务
```bash
# 使用WSL Docker专用脚本
start-wsl-quick.bat
```

## 🔍 故障排除

### 如果自动启动失败
1. **手动启动Docker**：
   ```bash
   start-wsl-docker.bat
   ```

2. **检查WSL状态**：
   ```bash
   wsl --status
   ```

3. **检查Docker状态**：
   ```bash
   wsl docker info
   ```

### 常见问题
- **Docker Desktop未安装**：请安装Docker Desktop
- **WSL未配置**：请运行 `wsl --install`
- **防火墙阻止**：请检查防火墙设置
- **端口被占用**：请检查端口占用情况

## 🌐 服务访问

启动完成后，可以通过以下地址访问服务：

- **前端应用**: http://localhost:3010
- **后端API**: http://localhost:8090/api
- **健康检查**: http://localhost:8090/actuator/health
- **数据库**: localhost:3316
- **Redis**: localhost:6389

## 📝 注意事项

1. **首次启动**：可能需要较长时间下载Docker镜像
2. **网络要求**：需要网络连接下载镜像
3. **系统要求**：需要WSL2和Docker Desktop
4. **权限要求**：可能需要管理员权限启动Docker

---

**功能完成时间**: $(Get-Date -Format "yyyy-MM-dd HH:mm:ss")
**支持环境**: WSL Docker + Windows本地开发
