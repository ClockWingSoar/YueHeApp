# YueHeApp 技术架构文档

## 1. 系统架构概述

### 1.1 整体架构
YueHeApp采用分层架构设计，基于Spring Boot框架构建，遵循MVC设计模式，实现业务逻辑与数据访问的分离。

```
┌─────────────────────────────────────────────────────────────┐
│                    表现层 (Presentation Layer)                │
├─────────────────────────────────────────────────────────────┤
│  Thymeleaf Templates  │  Bootstrap UI  │  jQuery JavaScript  │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    控制层 (Controller Layer)                 │
├─────────────────────────────────────────────────────────────┤
│  HomeController  │  ClientController  │  SaleController     │
│  UserController  │  OperationController │  EmployeeController│
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    业务层 (Service Layer)                    │
├─────────────────────────────────────────────────────────────┤
│  ClientService   │  SaleService      │  OperationService    │
│  UserService     │  EmployeeService  │  YueHeCommonService  │
└─────────────────────────────────────────────────────────────┘
┌─────────────────────────────────────────────────────────────┐
│                    数据访问层 (Data Access Layer)             │
├─────────────────────────────────────────────────────────────┤
│  JPA Repositories  │  Hibernate ORM  │  MySQL Database     │
└─────────────────────────────────────────────────────────────┘
```

### 1.2 模块划分
项目采用多模块Maven结构，实现模块化开发和依赖管理：

- **yuehe-common**: 公共模块，包含实体类、工具类、常量定义
- **yuehe-backend**: 后端模块，包含服务层、数据访问层、业务逻辑
- **yuehe-web**: Web模块，包含控制器层、前端模板、静态资源

## 2. 技术栈

### 2.1 后端技术
- **框架**: Spring Boot 2.1.6
- **数据访问**: Spring Data JPA + Hibernate 5.4.3
- **安全框架**: Spring Security
- **模板引擎**: Thymeleaf
- **构建工具**: Maven 3.x
- **Java版本**: JDK 1.8
- **数据库**: MySQL 8.0

### 2.2 前端技术
- **UI框架**: Bootstrap 4.2.1
- **JavaScript库**: jQuery
- **模板引擎**: Thymeleaf
- **样式**: CSS3 + 自定义样式
- **图标**: Glyphicons

### 2.3 开发工具
- **IDE**: IntelliJ IDEA / Eclipse
- **版本控制**: Git
- **数据库工具**: MySQL Workbench
- **API测试**: Postman

## 3. 数据库设计

### 3.1 数据库架构
采用关系型数据库设计，通过外键关联实现数据一致性：

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   CosmeticShop  │    │     Client      │    │      Sale       │
│                 │    │                 │    │                 │
│ id (PK)         │◄───┤ shop_id (FK)    │◄───┤ client_id (FK)  │
│ name            │    │ id (PK)         │    │ id (PK)         │
│ owner           │    │ name            │    │ beautify_skin_  │
│ contact_method  │    │ age             │    │ item_id (FK)    │
│ location        │    │ gender          │    │ seller_id (FK)  │
│ size            │    │ symptom         │    │ item_number     │
│ member_number   │    │                 │    │ create_card_    │
│ discount        │    │                 │    │ total_amount    │
│ shop_premium    │    │                 │    │ received_amount │
│ description     │    │                 │    │ received_earned_│
└─────────────────┘    └─────────────────┘    │ amount          │
                                              │ employee_premium│
                                              │ shop_premium    │
                                              │ create_card_date│
                                              │ description     │
                                              └─────────────────┘
                                                       │
                                                       ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│     Tool        │    │   Employee      │    │   Operation     │
│                 │    │                 │    │                 │
│ id (PK)         │◄───┤ id (PK)         │◄───┤ tool_id (FK)    │
│ name            │    │ name            │    │ operator_id (FK)│
│ major           │    │ salary          │    │ sale_id (FK)    │
│ price           │    │ birthday        │    │ id (PK)         │
│ buy_date        │    │ description     │    │ operation_date  │
│ buy_from        │    │ resigned        │    │ description     │
│ operate_expense │    │                 │    │                 │
│ description     │    │                 │    │                 │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 3.2 表结构设计

#### 3.2.1 核心业务表
- **client**: 客户信息表
- **cosmeticshop**: 美容院信息表
- **sale**: 销售记录表
- **operation**: 操作记录表
- **employee**: 员工信息表
- **tool**: 仪器信息表
- **beautifyskinitem**: 美容项目表

#### 3.2.2 系统管理表
- **user**: 用户账户表
- **role**: 角色信息表
- **duty**: 职责分配表

#### 3.2.3 扩展功能表
- **profile**: 客户档案表
- **clientquestionare**: 客户问卷表
- **salecardamountadjust**: 销售卡片金额调整表
- **shoprefundrule**: 美容院回款规则表

### 3.3 索引设计
为提高查询性能，在关键字段上创建索引：

```sql
-- 主键索引（自动创建）
PRIMARY KEY (id)

-- 外键索引
INDEX idx_client_shop_id (shop_id)
INDEX idx_sale_client_id (client_id)
INDEX idx_sale_item_id (beautify_skin_item_id)
INDEX idx_sale_seller_id (seller_id)
INDEX idx_operation_sale_id (sale_id)
INDEX idx_operation_operator_id (operator_id)
INDEX idx_operation_tool_id (tool_id)

-- 查询优化索引
INDEX idx_client_name (name)
INDEX idx_sale_create_date (create_card_date)
INDEX idx_operation_date (operation_date)
INDEX idx_employee_name (name)
```

## 4. 应用架构

### 4.1 分层架构

#### 4.1.1 表现层 (Presentation Layer)
- **职责**: 处理用户请求，展示数据，收集用户输入
- **组件**: Thymeleaf模板、Bootstrap UI组件、jQuery脚本
- **特点**: 响应式设计，支持多设备访问

#### 4.1.2 控制层 (Controller Layer)
- **职责**: 接收HTTP请求，调用业务服务，返回响应
- **组件**: Spring MVC控制器
- **特点**: RESTful设计，统一异常处理

#### 4.1.3 业务层 (Service Layer)
- **职责**: 实现业务逻辑，处理业务规则，协调数据访问
- **组件**: Spring Service组件
- **特点**: 事务管理，业务规则封装

#### 4.1.4 数据访问层 (Data Access Layer)
- **职责**: 数据持久化，数据查询，数据操作
- **组件**: Spring Data JPA Repository
- **特点**: 自动SQL生成，对象关系映射

### 4.2 包结构设计

```
com.yuehe.app
├── common/                 # 公共模块
│   ├── entity/            # 实体类
│   ├── dto/               # 数据传输对象
│   ├── util/              # 工具类
│   └── property/          # 常量定义
├── config/                # 配置类
│   ├── BeanConfigurations.java
│   └── SecurityConfig.java
├── controller/            # 控制器层
│   ├── HomeController.java
│   ├── ClientController.java
│   ├── SaleController.java
│   └── ...
├── service/               # 服务层
│   ├── ClientService.java
│   ├── SaleService.java
│   ├── YueHeCommonService.java
│   └── ...
├── repository/            # 数据访问层
│   ├── ClientRepository.java
│   ├── SaleRepository.java
│   └── ...
└── specification/         # 查询规范
    └── YueHeSpecification.java
```

## 5. 安全架构

### 5.1 认证机制
- **认证方式**: 用户名密码认证
- **会话管理**: Spring Security Session管理
- **密码加密**: BCrypt加密算法
- **登录控制**: 登录失败锁定机制

### 5.2 授权机制
- **角色管理**: 基于角色的访问控制(RBAC)
- **权限控制**: 细粒度功能权限控制
- **资源保护**: URL级别访问控制
- **方法安全**: 方法级别权限控制

### 5.3 安全配置
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/login", "/css/**", "/js/**", "/images/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
            .and()
            .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error=true")
            .and()
            .logout()
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
            .and()
            .csrf().disable();
    }
}
```

## 6. 数据访问架构

### 6.1 JPA配置
```yaml
spring:
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true
    properties:
      hibernate:
        format_sql: true
        dialect: org.hibernate.dialect.MySQL8Dialect
    database: mysql
```

### 6.2 数据源配置
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/yuehe?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: soveran
    password: 5688Sove
    driver-class-name: com.mysql.cj.jdbc.Driver
```

### 6.3 Repository设计
```java
@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    
    // 自定义查询方法
    List<Client> findByName(String name);
    List<Client> findByShopId(String shopId);
    Client findByClientNameAndShopId(String clientName, String shopId);
    
    // 使用@Query注解的复杂查询
    @Query("SELECT c FROM Client c WHERE c.age BETWEEN :minAge AND :maxAge")
    List<Client> findByAgeRange(@Param("minAge") int minAge, @Param("maxAge") int maxAge);
}
```

## 7. 缓存架构

### 7.1 缓存策略
- **查询缓存**: 缓存频繁查询的数据
- **会话缓存**: 缓存用户会话信息
- **静态资源缓存**: 缓存CSS、JS、图片等静态资源

### 7.2 缓存实现
```java
@Service
public class ClientService {
    
    @Cacheable(value = "clients", key = "#id")
    public Client getById(String id) {
        return clientRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid client Id:" + id));
    }
    
    @CacheEvict(value = "clients", key = "#client.id")
    public Client update(Client client) {
        return clientRepository.save(client);
    }
}
```

## 8. 日志架构

### 8.1 日志配置
```yaml
logging:
  level:
    com.yuehe.app: DEBUG
    org.springframework: ERROR
    org.hibernate: ERROR
  pattern:
    console: "%d{yyyy-MM-dd HH:mm:ss} - %msg%n"
    file: "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%n"
  file: yuehe.log
```

### 8.2 日志分类
- **业务日志**: 记录业务操作和流程
- **系统日志**: 记录系统运行状态
- **错误日志**: 记录异常和错误信息
- **审计日志**: 记录用户操作和权限变更

## 9. 部署架构

### 9.1 部署环境
- **开发环境**: 本地开发环境
- **测试环境**: 集成测试环境
- **生产环境**: 正式运行环境

### 9.2 部署方式
- **WAR包部署**: 部署到Tomcat服务器
- **JAR包部署**: 独立运行Spring Boot应用
- **Docker部署**: 容器化部署

### 9.3 环境配置
```yaml
# application-dev.yml
spring:
  profiles:
    active: dev
  datasource:
    url: jdbc:mysql://localhost:3306/yuehe_dev
    username: dev_user
    password: dev_password

# application-prod.yml
spring:
  profiles:
    active: prod
  datasource:
    url: jdbc:mysql://prod-server:3306/yuehe_prod
    username: prod_user
    password: prod_password
```

## 10. 性能优化

### 10.1 数据库优化
- **索引优化**: 合理创建和使用索引
- **查询优化**: 优化SQL查询语句
- **连接池**: 配置数据库连接池
- **分页查询**: 使用分页减少数据传输

### 10.2 应用优化
- **缓存策略**: 合理使用缓存
- **异步处理**: 使用异步处理耗时操作
- **资源压缩**: 压缩静态资源
- **CDN加速**: 使用CDN加速静态资源访问

### 10.3 监控指标
- **响应时间**: 接口响应时间监控
- **吞吐量**: 系统吞吐量监控
- **错误率**: 系统错误率监控
- **资源使用**: CPU、内存、磁盘使用监控

## 11. 扩展性设计

### 11.1 水平扩展
- **负载均衡**: 使用Nginx实现负载均衡
- **集群部署**: 多实例集群部署
- **数据库分离**: 读写分离，主从复制

### 11.2 功能扩展
- **插件架构**: 支持插件式功能扩展
- **API接口**: 提供RESTful API接口
- **微服务**: 支持微服务架构改造

### 11.3 数据扩展
- **分库分表**: 支持数据分库分表
- **数据迁移**: 支持数据迁移和同步
- **备份恢复**: 完善的数据备份恢复机制

---

**文档版本**: 1.0  
**创建日期**: 2024年12月  
**最后更新**: 2024年12月  
**文档状态**: 待审核
