# WSL Docker 使用指南

## 🐳 WSL Docker 环境配置

### 问题描述
您的Docker运行在WSL中，而不是Windows的Docker Desktop。这需要特殊的配置来确保脚本能正确工作。

## 🔧 解决方案

### 1. 自动检测和适配
所有启动脚本现在都支持自动检测Docker环境：
- ✅ 优先检测Windows Docker Desktop
- ✅ 自动回退到WSL Docker
- ✅ 使用 `wsl` 前缀执行Docker命令

### 2. 启动WSL Docker
如果Docker未运行，可以使用以下方法：

#### 方法1: 使用启动脚本
```bash
# 运行WSL Docker启动脚本
start-wsl-docker.bat
```

#### 方法2: 手动启动
```bash
# 启动Docker Desktop
wsl -d docker-desktop

# 或者直接启动WSL中的Docker
wsl docker info
```

## 🚀 使用方法

### 启动YueHeApp
```bash
# 现在可以直接运行，脚本会自动适配WSL Docker
quick-start.bat

# 或者使用专用混合模式脚本
start-hybrid-dev.bat
```

### 检查服务状态
```bash
# 健康检查会自动适配WSL Docker
health-check.bat
```

## 🔍 故障排除

### 1. 检查WSL状态
```bash
# 检查WSL是否运行
wsl --status

# 列出WSL发行版
wsl --list --verbose
```

### 2. 检查Docker状态
```bash
# 检查Windows Docker
docker info

# 检查WSL Docker
wsl docker info
```

### 3. 启动Docker服务
```bash
# 启动Docker Desktop
start "" "C:\Program Files\Docker\Docker\Docker Desktop.exe"

# 或启动WSL Docker
wsl -d docker-desktop
```

## 📋 脚本适配说明

### 自动检测逻辑
```batch
# 1. 首先尝试Windows Docker
docker info >nul 2>&1
if %errorlevel% neq 0 (
    # 2. 回退到WSL Docker
    wsl docker info >nul 2>&1
    if %errorlevel% equ 0 (
        set USE_WSL_DOCKER=true
    )
)
```

### 命令适配
所有Docker命令都会根据环境自动选择：
- **Windows Docker**: `docker-compose up -d`
- **WSL Docker**: `wsl docker-compose up -d`

## 🌐 服务访问

### 端口映射
- **MySQL**: localhost:3316 (WSL Docker)
- **Redis**: localhost:6389 (WSL Docker)
- **后端API**: localhost:8090 (Windows本地)
- **前端应用**: localhost:3010 (Windows本地)

### 网络连接
WSL Docker容器会自动映射端口到Windows主机，所以：
- ✅ 可以从Windows访问WSL中的MySQL和Redis
- ✅ 可以从WSL访问Windows中的后端和前端服务
- ✅ 所有服务都在localhost上可用

## 🎯 开发优势

1. **环境隔离**: 基础服务在WSL中，应用服务在Windows中
2. **性能优化**: 只有基础服务在Docker中，减少资源占用
3. **调试便利**: 应用服务在Windows本地，便于IDE调试
4. **热重载**: 支持代码修改后自动重载

## 📝 常用命令

### WSL Docker命令
```bash
# 查看容器状态
wsl docker ps

# 查看日志
wsl docker logs yuehe-mysql-dev
wsl docker logs yuehe-redis-dev

# 停止服务
wsl docker-compose -f docker-compose.dev.yml down

# 重启服务
wsl docker-compose -f docker-compose.dev.yml restart
```

### 混合模式管理
```bash
# 启动所有服务
start-hybrid-dev.bat

# 停止所有服务
stop-hybrid-dev.bat

# 健康检查
health-check.bat
```

---

**配置完成**: 所有脚本已适配WSL Docker环境
**默认模式**: 混合开发模式 (WSL Docker + Windows本地)
