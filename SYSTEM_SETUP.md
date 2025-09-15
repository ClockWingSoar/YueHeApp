# 悦和国际美容院管理系统 - 系统设置指南

## 系统概述

悦和国际美容院管理系统是一个基于Spring Boot + Next.js的现代化美容院管理平台，支持客户管理、销售管理、员工管理、操作管理等核心业务功能。

## 技术架构

### 后端技术栈
- **框架**: Spring Boot 2.1.6
- **数据访问**: Spring Data JPA + Hibernate
- **安全框架**: Spring Security
- **数据库**: MySQL 8.0
- **构建工具**: Maven 3.x

### 前端技术栈
- **框架**: Next.js 14 + React 18
- **UI组件**: Ant Design 5.x
- **状态管理**: Redux Toolkit
- **样式**: Tailwind CSS
- **类型检查**: TypeScript

## 快速开始

### 1. 环境要求

- Java 8 或更高版本
- Node.js 16 或更高版本
- MySQL 8.0 或更高版本
- Maven 3.x

### 2. 数据库设置

1. 创建数据库：
```sql
CREATE DATABASE yuehe CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 导入初始数据：
```bash
mysql -u root -p yuehe < Dump20200212.sql
```

### 3. 后端启动

1. 进入后端目录：
```bash
cd yuehe-api
```

2. 修改数据库配置（如需要）：
编辑 `src/main/resources/application-dev.yml`

3. 启动后端服务：
```bash
mvn spring-boot:run -Dspring-boot.run.profiles=dev
```

后端服务将在 http://localhost:8080 启动

### 4. 前端启动

1. 进入前端目录：
```bash
cd frontend
```

2. 安装依赖：
```bash
npm install
```

3. 启动前端服务：
```bash
npm run dev
```

前端服务将在 http://localhost:3000 启动

### 5. 快速测试

运行测试脚本：
```bash
# Windows
scripts/test-system.bat

# Linux/Mac
scripts/test-system.sh
```

## 系统功能

### 用户认证
- 支持三种角色：管理员(ADMIN)、专家(EXPERT)、操作员(OPERATOR)
- 基于角色的权限控制
- JWT Token认证

### 核心功能模块

#### 1. 客户管理
- 客户信息录入、查询、编辑、删除
- 客户档案管理
- 客户问卷调研
- 支持按美容院、性别、年龄等条件筛选

#### 2. 销售管理
- 销售记录创建、查询、编辑、删除
- 自动提成计算
- 销售统计分析
- 支持按客户、员工、日期等条件筛选

#### 3. 员工管理
- 员工信息管理
- 角色权限分配
- 薪资管理
- 在职状态管理

#### 4. 美容院管理
- 美容院信息管理
- 折扣政策设置
- 提成规则配置
- 客户统计

#### 5. 操作管理
- 美容操作记录
- 仪器使用跟踪
- 操作统计分析

#### 6. 报表管理
- 销售报表
- 客户报表
- 员工报表
- 财务报表
- 支持CSV、Excel、PDF导出

## 默认账户

系统提供以下测试账户：

| 用户名 | 密码 | 角色 | 权限 |
|--------|------|------|------|
| admin | admin123 | 管理员 | 所有功能权限 |
| expert | expert123 | 专家 | 业务功能权限 |
| operator | operator123 | 操作员 | 基础操作权限 |

## API接口

### 认证接口
- `POST /api/auth/login` - 用户登录
- `POST /api/auth/logout` - 用户登出
- `GET /api/auth/me` - 获取当前用户信息

### 客户管理接口
- `GET /api/clients` - 获取客户列表
- `GET /api/clients/{id}` - 获取客户详情
- `POST /api/clients` - 创建客户
- `PUT /api/clients/{id}` - 更新客户
- `DELETE /api/clients/{id}` - 删除客户

### 销售管理接口
- `GET /api/sales` - 获取销售列表
- `GET /api/sales/{id}` - 获取销售详情
- `POST /api/sales` - 创建销售记录
- `PUT /api/sales/{id}` - 更新销售记录
- `DELETE /api/sales/{id}` - 删除销售记录
- `GET /api/sales/summary` - 获取销售汇总

## 开发指南

### 后端开发

1. 实体类位于 `yuehe-common/src/main/java/com/yuehe/app/entity/`
2. 服务类位于 `yuehe-backend/src/main/java/com/yuehe/app/service/`
3. 控制器位于 `yuehe-api/src/main/java/com/yuehe/app/api/controller/`
4. 数据访问层位于 `yuehe-backend/src/main/java/com/yuehe/app/repository/`

### 前端开发

1. 页面组件位于 `frontend/app/`
2. 通用组件位于 `frontend/components/`
3. 状态管理位于 `frontend/store/`
4. API调用位于 `frontend/src/lib/api.ts`
5. 类型定义位于 `frontend/src/types/`

### 数据库设计

主要数据表：
- `user` - 用户表
- `client` - 客户表
- `cosmeticshop` - 美容院表
- `sale` - 销售表
- `operation` - 操作表
- `employee` - 员工表
- `tool` - 仪器表
- `beautifyskinitem` - 美容项目表

## 部署说明

### 开发环境
- 后端：http://localhost:8080
- 前端：http://localhost:3000
- 数据库：localhost:3306

### 生产环境
- 使用Docker容器化部署
- 配置文件：`docker-compose.prod.yml`
- 启动命令：`docker-compose -f docker-compose.prod.yml up -d`

## 故障排除

### 常见问题

1. **数据库连接失败**
   - 检查MySQL服务是否启动
   - 验证数据库配置信息
   - 确认数据库用户权限

2. **前端无法连接后端**
   - 检查后端服务是否启动
   - 验证API地址配置
   - 检查CORS设置

3. **登录失败**
   - 确认用户名密码正确
   - 检查用户角色配置
   - 查看后端日志

### 日志查看

- 后端日志：`yuehe-api/logs/`
- 前端日志：浏览器开发者工具控制台

## 联系支持

如有问题，请联系开发团队或查看项目文档。

---

**系统版本**: 1.0.0  
**最后更新**: 2024年12月  
**开发团队**: Soveran Development Team
