# YueHeApp 悦和国际美容院管理系统

## 项目概述

YueHeApp是一个现代化的美容院运营管理平台，采用前后端分离架构，提供客户管理、销售管理、员工管理、报表统计等核心功能。

## 技术栈

### 前端
- **框架**: Next.js 14+ + React 18+ + TypeScript
- **UI组件**: Ant Design 5+
- **状态管理**: Redux Toolkit + RTK Query
- **样式**: Tailwind CSS 3+
- **数据可视化**: Recharts + Chart.js

### 后端
- **框架**: Spring Boot 3.2+ + Java 17
- **数据库**: MySQL 8.0+ + Redis 7+
- **安全**: Spring Security 6+ + JWT
- **监控**: Micrometer + Prometheus
- **文档**: SpringDoc OpenAPI 3

### 部署
- **容器化**: Docker + Docker Compose
- **编排**: Kubernetes (生产环境)
- **CI/CD**: GitHub Actions
- **代理**: Nginx

## 快速开始

### 环境要求

- Node.js 18+
- Java 17+
- Docker & Docker Compose
- MySQL 8.0+
- Redis 7+

### 开发环境启动

#### 方式一：使用Docker Compose（推荐）

```bash
# 克隆项目
git clone <repository-url>
cd YueHeApp

# 启动开发环境
./scripts/start-dev.sh  # Linux/Mac
# 或
scripts\start-dev.bat   # Windows
```

#### 方式二：手动启动

```bash
# 启动数据库和Redis
docker-compose up -d mysql redis

# 启动后端
cd yuehe-backend
mvn spring-boot:run

# 启动前端
cd frontend
npm install
npm run dev
```

### 生产环境部署

```bash
# 使用Docker Compose部署
./scripts/start-prod.sh  # Linux/Mac
# 或
scripts\start-prod.bat   # Windows
```

## 项目结构

```
YueHeApp/
├── frontend/                 # Next.js前端应用
│   ├── app/                 # App Router页面
│   ├── src/                 # 源代码
│   │   ├── components/      # React组件
│   │   ├── lib/            # 工具库
│   │   └── types/          # TypeScript类型
│   ├── store/              # Redux状态管理
│   └── public/             # 静态资源
├── yuehe-backend/          # Spring Boot后端
│   ├── src/main/java/      # Java源代码
│   └── src/main/resources/ # 配置文件
├── yuehe-common/           # 公共模块
├── yuehe-web/              # Web模块
├── yuehe-api/              # API模块
├── docs/                   # 项目文档
├── scripts/                # 启动脚本
├── docker-compose.yml      # Docker编排
└── Dockerfile.*            # Docker镜像构建
```

## 功能特性

### 核心功能
- ✅ **客户管理**: 客户信息、问卷、档案管理
- ✅ **销售管理**: 销售记录、提成计算、回款管理
- ✅ **员工管理**: 员工信息、薪资、职责分配
- ✅ **美容院管理**: 美容院信息、折扣、提成规则
- ✅ **操作管理**: 美容操作记录、仪器使用
- ✅ **报表管理**: 各类统计报表和数据分析

### 技术特性
- 🚀 **现代化UI**: 基于Ant Design的现代化界面
- ⚡ **高性能**: 多层缓存、代码分割、懒加载
- 🔒 **安全性**: JWT认证、RBAC权限控制
- 📱 **响应式**: 支持移动端和桌面端
- 🔧 **可扩展**: 模块化设计、微服务就绪
- 📊 **监控**: 完整的监控和日志体系

## API文档

启动后端服务后，访问以下地址查看API文档：

- Swagger UI: http://localhost:8080/swagger-ui.html
- OpenAPI JSON: http://localhost:8080/v3/api-docs

## 开发指南

### 前端开发

```bash
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev

# 运行测试
npm test

# 构建生产版本
npm run build
```

### 后端开发

```bash
cd yuehe-backend

# 运行测试
mvn test

# 启动应用
mvn spring-boot:run

# 打包应用
mvn clean package
```

### 代码规范

- 前端使用ESLint + Prettier
- 后端遵循Java编码规范
- 提交前自动运行代码检查
- 测试覆盖率要求 > 80%

## 部署说明

### Docker部署

```bash
# 构建镜像
docker-compose build

# 启动服务
docker-compose up -d

# 查看日志
docker-compose logs -f

# 停止服务
docker-compose down
```

### Kubernetes部署

```bash
# 应用Kubernetes配置
kubectl apply -f k8s/

# 查看Pod状态
kubectl get pods

# 查看服务状态
kubectl get services
```

## 监控和日志

### 监控指标
- 应用健康状态: http://localhost:8080/actuator/health
- Prometheus指标: http://localhost:8080/actuator/prometheus
- 应用信息: http://localhost:8080/actuator/info

### 日志查看
```bash
# 查看所有服务日志
docker-compose logs -f

# 查看特定服务日志
docker-compose logs -f backend
docker-compose logs -f frontend
```

## 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 GNU General Public License v3.0 许可证。

## 联系方式

- **项目负责人**: 小燕 (XiaoYan)
- **技术栈**: Next.js + Spring Boot + MySQL + Redis
- **开发团队**: 悦和国际技术团队

## 更新日志

### v3.0.0 (2024-12)
- 🎉 全新现代化架构
- ✨ 前后端分离设计
- 🚀 性能大幅提升
- 📱 响应式界面设计
- 🔒 增强安全特性
- 📊 完善监控体系

---

**文档版本**: 3.0.0  
**创建日期**: 2024年12月  
**最后更新**: 2024年12月
