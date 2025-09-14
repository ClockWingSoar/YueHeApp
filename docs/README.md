# YueHeApp 项目文档

## 项目概述

YueHeApp（悦和国际美容院管理系统）是一个基于Spring Boot开发的企业级美容院运营管理平台。系统旨在通过数字化管理提升美容院的运营效率，实现客户关系管理、销售业绩跟踪、员工管理、财务分析等核心业务功能。

## 文档结构

本目录包含了YueHeApp项目的完整技术文档，按功能模块和文档类型进行分类：

### 📋 需求文档
- **[requirements.md](./requirements.md)** - 项目总体需求文档
  - 项目概述和业务背景
  - 核心业务实体定义
  - 功能需求和非功能性需求
  - 技术架构需求
  - 数据字典和业务流程

- **[functional-requirements.md](./functional-requirements.md)** - 详细功能需求说明
  - 各模块详细功能描述
  - 输入输出参数定义
  - 业务规则和处理逻辑
  - 异常处理和错误码

### 🏗️ 技术文档
- **[technical-architecture.md](./technical-architecture.md)** - 技术架构文档
  - 系统架构设计
  - 技术栈选型
  - 模块划分和包结构
  - 安全架构和性能优化

- **[database-design.md](./database-design.md)** - 数据库设计文档
  - 数据库表结构设计
  - 索引和约束定义
  - 视图和存储过程
  - 性能优化策略

- **[api-documentation.md](./api-documentation.md)** - API接口文档
  - RESTful API接口定义
  - 请求响应格式
  - 错误处理机制
  - 接口使用示例

## 快速开始

### 环境要求
- JDK 1.8+
- MySQL 8.0+
- Maven 3.x
- Tomcat 9.x

### 项目结构
```
YueHeApp/
├── yuehe-common/          # 公共模块
│   ├── src/main/java/com/yuehe/app/
│   │   ├── common/        # 实体类
│   │   ├── dto/           # 数据传输对象
│   │   ├── util/          # 工具类
│   │   └── property/      # 常量定义
│   └── pom.xml
├── yuehe-backend/         # 后端模块
│   ├── src/main/java/com/yuehe/app/
│   │   ├── entity/        # 实体类
│   │   ├── service/       # 服务层
│   │   ├── repository/    # 数据访问层
│   │   └── specification/ # 查询规范
│   └── pom.xml
├── yuehe-web/             # Web模块
│   ├── src/main/java/com/yuehe/app/
│   │   └── controller/    # 控制器层
│   ├── src/main/resources/
│   │   ├── templates/     # Thymeleaf模板
│   │   ├── static/        # 静态资源
│   │   └── application.properties
│   └── pom.xml
├── docs/                  # 项目文档
└── pom.xml               # 父级POM文件
```

### 核心功能模块

#### 1. 客户管理
- 客户信息注册、查询、编辑、删除
- 客户问卷管理
- 客户档案跟踪
- 客户统计分析

#### 2. 销售管理
- 销售记录创建和管理
- 提成自动计算
- 销售业绩统计
- 财务报表生成

#### 3. 操作管理
- 美容操作记录
- 仪器使用跟踪
- 操作员工作量统计
- 服务质量评估

#### 4. 员工管理
- 员工信息管理
- 角色权限分配
- 薪资管理
- 绩效考核

#### 5. 美容院管理
- 美容院信息管理
- 折扣政策设置
- 提成规则配置
- 连锁店管理

#### 6. 系统管理
- 用户账户管理
- 角色权限控制
- 系统配置管理
- 数据备份恢复

## 技术特色

### 🚀 高性能
- 基于Spring Boot 2.1.6，启动快速
- 使用Hibernate JPA，ORM性能优化
- 数据库连接池配置，提高并发性能
- 分页查询，减少数据传输量

### 🔒 高安全
- Spring Security安全框架
- 基于角色的访问控制(RBAC)
- 密码BCrypt加密
- CSRF防护和会话管理

### 📊 易扩展
- 模块化设计，松耦合架构
- 多模块Maven项目结构
- 支持微服务架构改造
- 插件式功能扩展

### 🎨 用户友好
- 响应式Bootstrap UI设计
- Thymeleaf模板引擎
- 直观的操作界面
- 完善的错误提示

## 数据库设计

### 核心表结构
- **client** - 客户信息表
- **cosmeticshop** - 美容院信息表
- **sale** - 销售记录表
- **operation** - 操作记录表
- **employee** - 员工信息表
- **tool** - 仪器信息表
- **beautifyskinitem** - 美容项目表
- **user** - 用户账户表
- **role** - 角色信息表

### 关系设计
- 客户属于特定美容院
- 销售记录关联客户、美容项目、销售员工
- 操作记录关联销售、操作员工、使用仪器
- 员工可以分配多个角色和职责

## API接口

### 认证接口
- `POST /login` - 用户登录
- `POST /logout` - 用户登出

### 客户管理接口
- `GET /getClientList` - 获取客户列表
- `POST /createClient` - 创建客户
- `GET /getClientDetail/{id}` - 获取客户详情
- `PUT /updateClient/{id}` - 更新客户信息
- `DELETE /deleteClient/{id}` - 删除客户

### 销售管理接口
- `GET /getSaleList` - 获取销售列表
- `POST /createSale` - 创建销售记录
- `GET /getSaleDetail/{id}` - 获取销售详情

### 操作管理接口
- `GET /getOperationList` - 获取操作列表
- `POST /createOperation` - 创建操作记录

## 部署说明

### 开发环境部署
1. 克隆项目代码
2. 配置数据库连接
3. 运行Maven构建
4. 启动Spring Boot应用

### 生产环境部署
1. 打包WAR文件
2. 部署到Tomcat服务器
3. 配置生产数据库
4. 配置日志和监控

## 开发规范

### 代码规范
- 遵循Java编码规范
- 使用Lombok减少样板代码
- 统一异常处理机制
- 完善的日志记录

### 数据库规范
- 表名使用下划线命名
- 字段名使用下划线命名
- 主键统一使用id字段
- 外键字段以_id结尾

### API规范
- RESTful API设计
- 统一的响应格式
- 完善的错误码定义
- 详细的接口文档

## 维护指南

### 日志管理
- 使用SLF4J日志框架
- 分级日志记录
- 日志文件轮转
- 操作审计日志

### 性能监控
- 接口响应时间监控
- 数据库性能监控
- 内存使用监控
- 错误率监控

### 数据备份
- 定期全量备份
- 增量备份策略
- 备份文件管理
- 恢复测试验证

## 版本历史

### v1.0.0 (2024-12-01)
- 初始版本发布
- 基础功能模块完成
- 核心业务逻辑实现
- 基础文档完善

## 联系方式

- **项目负责人**: 小燕 (XiaoYan)
- **技术栈**: Spring Boot + MySQL + Thymeleaf
- **开发团队**: 悦和国际技术团队

## 许可证

本项目采用GNU General Public License v3.0许可证。

---

**文档版本**: 1.0  
**创建日期**: 2024年12月  
**最后更新**: 2024年12月  
**文档状态**: 已完成
