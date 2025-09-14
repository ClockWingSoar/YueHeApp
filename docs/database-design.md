# YueHeApp 数据库设计文档

## 1. 数据库概述

### 1.1 基本信息
- **数据库类型**: MySQL 8.0
- **字符集**: UTF-8
- **排序规则**: utf8mb4_unicode_ci
- **存储引擎**: InnoDB
- **连接池**: HikariCP

### 1.2 设计原则
- **规范化**: 遵循第三范式，减少数据冗余
- **性能优化**: 合理设计索引，优化查询性能
- **数据完整性**: 使用外键约束保证数据一致性
- **扩展性**: 预留扩展字段，支持功能升级

## 2. 表结构设计

### 2.1 客户表 (client)
**表说明**: 存储客户基本信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 客户ID，主键 |
| name | VARCHAR | 100 | NULL | - | 客户姓名 |
| age | INT | - | NULL | - | 年龄 |
| gender | VARCHAR | 10 | NULL | - | 性别 |
| symptom | VARCHAR | 500 | NULL | - | 症状描述 |
| shop_id | VARCHAR | 50 | NOT NULL | - | 美容院ID，外键 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 外键索引
INDEX idx_client_shop_id (shop_id)

-- 查询优化索引
INDEX idx_client_name (name)
INDEX idx_client_gender (gender)
INDEX idx_client_age (age)
```

**外键约束**:
```sql
ALTER TABLE client 
ADD CONSTRAINT fk_client_shop 
FOREIGN KEY (shop_id) REFERENCES cosmeticshop(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;
```

### 2.2 美容院表 (cosmeticshop)
**表说明**: 存储美容院基本信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 美容院ID，主键 |
| name | VARCHAR | 100 | NULL | - | 美容院名称 |
| owner | VARCHAR | 100 | NULL | - | 负责人 |
| contact_method | VARCHAR | 200 | NULL | - | 联系方式 |
| location | VARCHAR | 200 | NULL | - | 地址 |
| size | VARCHAR | 50 | NULL | - | 规模 |
| member_number | INT | - | NULL | 0 | 会员数量 |
| discount | FLOAT | - | NULL | 1.0 | 折扣率 |
| shop_premium | FLOAT | - | NULL | 0.0 | 店铺提成 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 查询优化索引
INDEX idx_shop_name (name)
INDEX idx_shop_owner (owner)
INDEX idx_shop_location (location)
```

### 2.3 销售表 (sale)
**表说明**: 存储销售记录信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 销售ID，主键 |
| client_id | VARCHAR | 50 | NOT NULL | - | 客户ID，外键 |
| beautify_skin_item_id | VARCHAR | 50 | NOT NULL | - | 美容项目ID，外键 |
| seller_id | VARCHAR | 50 | NOT NULL | - | 销售员工ID，外键 |
| item_number | INT | - | NOT NULL | - | 项目数量 |
| create_card_total_amount | BIGINT | - | NOT NULL | - | 开卡总金额 |
| received_amount | BIGINT | - | NOT NULL | - | 实际回款 |
| received_earned_amount | BIGINT | - | NOT NULL | - | 公司收到回款 |
| employee_premium | FLOAT | - | NULL | 0.0 | 员工提成 |
| shop_premium | FLOAT | - | NULL | 0.0 | 店铺提成 |
| create_card_date | VARCHAR | 20 | NOT NULL | - | 开卡日期 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 外键索引
INDEX idx_sale_client_id (client_id)
INDEX idx_sale_item_id (beautify_skin_item_id)
INDEX idx_sale_seller_id (seller_id)

-- 查询优化索引
INDEX idx_sale_create_date (create_card_date)
INDEX idx_sale_amount (create_card_total_amount)
INDEX idx_sale_received (received_amount)
```

**外键约束**:
```sql
-- 客户外键
ALTER TABLE sale 
ADD CONSTRAINT fk_sale_client 
FOREIGN KEY (client_id) REFERENCES client(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;

-- 美容项目外键
ALTER TABLE sale 
ADD CONSTRAINT fk_sale_item 
FOREIGN KEY (beautify_skin_item_id) REFERENCES beautifyskinitem(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;

-- 销售员工外键
ALTER TABLE sale 
ADD CONSTRAINT fk_sale_seller 
FOREIGN KEY (seller_id) REFERENCES employee(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;
```

### 2.4 操作表 (operation)
**表说明**: 存储操作记录信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 操作ID，主键 |
| sale_id | VARCHAR | 50 | NOT NULL | - | 销售ID，外键 |
| operator_id | VARCHAR | 50 | NOT NULL | - | 操作员工ID，外键 |
| tool_id | VARCHAR | 50 | NOT NULL | - | 仪器ID，外键 |
| operation_date | VARCHAR | 20 | NULL | - | 操作日期 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 外键索引
INDEX idx_operation_sale_id (sale_id)
INDEX idx_operation_operator_id (operator_id)
INDEX idx_operation_tool_id (tool_id)

-- 查询优化索引
INDEX idx_operation_date (operation_date)
```

**外键约束**:
```sql
-- 销售外键
ALTER TABLE operation 
ADD CONSTRAINT fk_operation_sale 
FOREIGN KEY (sale_id) REFERENCES sale(id) 
ON DELETE CASCADE ON UPDATE CASCADE;

-- 操作员工外键
ALTER TABLE operation 
ADD CONSTRAINT fk_operation_operator 
FOREIGN KEY (operator_id) REFERENCES employee(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;

-- 仪器外键
ALTER TABLE operation 
ADD CONSTRAINT fk_operation_tool 
FOREIGN KEY (tool_id) REFERENCES tool(id) 
ON DELETE RESTRICT ON UPDATE CASCADE;
```

### 2.5 员工表 (employee)
**表说明**: 存储员工基本信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 员工ID，主键 |
| name | VARCHAR | 100 | NULL | - | 员工姓名 |
| salary | INT | - | NULL | 0 | 薪资 |
| birthday | VARCHAR | 20 | NULL | - | 生日 |
| description | VARCHAR | 1000 | NULL | - | 描述 |
| resigned | VARCHAR | 10 | NULL | '在职' | 离职状态 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 查询优化索引
INDEX idx_employee_name (name)
INDEX idx_employee_resigned (resigned)
INDEX idx_employee_salary (salary)
```

### 2.6 仪器表 (tool)
**表说明**: 存储仪器信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 仪器ID，主键 |
| name | VARCHAR | 100 | NULL | - | 仪器名称 |
| major | VARCHAR | 100 | NULL | - | 专业领域 |
| price | INT | - | NULL | 0 | 价格 |
| buy_date | VARCHAR | 20 | NULL | - | 购买日期 |
| buy_from | VARCHAR | 100 | NULL | - | 购买渠道 |
| operate_expense | INT | - | NULL | 0 | 操作费用 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 查询优化索引
INDEX idx_tool_name (name)
INDEX idx_tool_major (major)
INDEX idx_tool_price (price)
```

### 2.7 美容项目表 (beautifyskinitem)
**表说明**: 存储美容项目信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 项目ID，主键 |
| name | VARCHAR | 100 | NULL | - | 项目名称 |
| price | INT | - | NULL | 0 | 价格 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 查询优化索引
INDEX idx_item_name (name)
INDEX idx_item_price (price)
```

### 2.8 用户表 (user)
**表说明**: 存储系统用户信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 用户ID，主键 |
| username | VARCHAR | 100 | NULL | - | 用户名 |
| password | VARCHAR | 255 | NULL | - | 密码 |
| role | VARCHAR | 50 | NULL | - | 角色 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 唯一索引
UNIQUE INDEX idx_user_username (username)

-- 查询优化索引
INDEX idx_user_role (role)
```

### 2.9 角色表 (role)
**表说明**: 存储系统角色信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 角色ID，主键 |
| name | VARCHAR | 100 | NULL | - | 角色名称 |
| description | VARCHAR | 500 | NULL | - | 角色描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 唯一索引
UNIQUE INDEX idx_role_name (name)
```

### 2.10 职责表 (duty)
**表说明**: 存储员工职责分配信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 职责ID，主键 |
| employee_id | VARCHAR | 50 | NOT NULL | - | 员工ID，外键 |
| role_id | VARCHAR | 50 | NOT NULL | - | 角色ID，外键 |
| description | VARCHAR | 500 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 外键索引
INDEX idx_duty_employee_id (employee_id)
INDEX idx_duty_role_id (role_id)

-- 唯一索引
UNIQUE INDEX idx_duty_employee_role (employee_id, role_id)
```

**外键约束**:
```sql
-- 员工外键
ALTER TABLE duty 
ADD CONSTRAINT fk_duty_employee 
FOREIGN KEY (employee_id) REFERENCES employee(id) 
ON DELETE CASCADE ON UPDATE CASCADE;

-- 角色外键
ALTER TABLE duty 
ADD CONSTRAINT fk_duty_role 
FOREIGN KEY (role_id) REFERENCES role(id) 
ON DELETE CASCADE ON UPDATE CASCADE;
```

### 2.11 客户档案表 (profile)
**表说明**: 存储客户档案信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| sale_id | VARCHAR | 50 | NOT NULL | - | 销售ID，主键 |
| rest_card_amount | INT | - | NULL | 0 | 剩余卡片数量 |
| create_profile_date | DATE | - | NULL | - | 创建档案日期 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (sale_id)

-- 外键索引
ALTER TABLE profile 
ADD CONSTRAINT fk_profile_sale 
FOREIGN KEY (sale_id) REFERENCES sale(id) 
ON DELETE CASCADE ON UPDATE CASCADE;
```

### 2.12 客户问卷表 (clientquestionare)
**表说明**: 存储客户问卷信息

| 字段名 | 数据类型 | 长度 | 是否为空 | 默认值 | 说明 |
|--------|----------|------|----------|--------|------|
| id | VARCHAR | 50 | NOT NULL | - | 问卷ID，主键 |
| client_id | VARCHAR | 50 | NOT NULL | - | 客户ID，外键 |
| skin_type | VARCHAR | 50 | NULL | - | 皮肤类型 |
| main_problem | VARCHAR | 200 | NULL | - | 主要问题 |
| expected_effect | VARCHAR | 200 | NULL | - | 期望效果 |
| budget_range | VARCHAR | 50 | NULL | - | 预算范围 |
| service_preference | VARCHAR | 100 | NULL | - | 服务偏好 |
| description | VARCHAR | 1000 | NULL | - | 描述 |

**索引设计**:
```sql
-- 主键索引
PRIMARY KEY (id)

-- 外键索引
INDEX idx_questionare_client_id (client_id)

-- 唯一索引
UNIQUE INDEX idx_questionare_client (client_id)
```

**外键约束**:
```sql
ALTER TABLE clientquestionare 
ADD CONSTRAINT fk_questionare_client 
FOREIGN KEY (client_id) REFERENCES client(id) 
ON DELETE CASCADE ON UPDATE CASCADE;
```

## 3. 视图设计

### 3.1 销售详情视图 (v_sale_detail)
**视图说明**: 提供销售记录的详细信息

```sql
CREATE VIEW v_sale_detail AS
SELECT 
    s.id,
    s.item_number,
    s.create_card_total_amount,
    s.received_amount,
    s.received_earned_amount,
    s.employee_premium,
    s.shop_premium,
    s.create_card_date,
    s.description,
    c.id as client_id,
    c.name as client_name,
    c.age as client_age,
    c.gender as client_gender,
    bsi.id as item_id,
    bsi.name as item_name,
    bsi.price as item_price,
    e.id as seller_id,
    e.name as seller_name,
    cs.id as shop_id,
    cs.name as shop_name,
    cs.discount as shop_discount
FROM sale s
LEFT JOIN client c ON s.client_id = c.id
LEFT JOIN beautifyskinitem bsi ON s.beautify_skin_item_id = bsi.id
LEFT JOIN employee e ON s.seller_id = e.id
LEFT JOIN cosmeticshop cs ON c.shop_id = cs.id;
```

### 3.2 操作详情视图 (v_operation_detail)
**视图说明**: 提供操作记录的详细信息

```sql
CREATE VIEW v_operation_detail AS
SELECT 
    o.id,
    o.operation_date,
    o.description,
    s.id as sale_id,
    c.id as client_id,
    c.name as client_name,
    e.id as operator_id,
    e.name as operator_name,
    t.id as tool_id,
    t.name as tool_name,
    t.major as tool_major
FROM operation o
LEFT JOIN sale s ON o.sale_id = s.id
LEFT JOIN client c ON s.client_id = c.id
LEFT JOIN employee e ON o.operator_id = e.id
LEFT JOIN tool t ON o.tool_id = t.id;
```

### 3.3 客户统计视图 (v_client_statistics)
**视图说明**: 提供客户统计信息

```sql
CREATE VIEW v_client_statistics AS
SELECT 
    c.id as client_id,
    c.name as client_name,
    c.age,
    c.gender,
    cs.name as shop_name,
    COUNT(s.id) as sale_count,
    SUM(s.create_card_total_amount) as total_amount,
    SUM(s.received_amount) as total_received,
    MAX(s.create_card_date) as last_sale_date
FROM client c
LEFT JOIN cosmeticshop cs ON c.shop_id = cs.id
LEFT JOIN sale s ON c.id = s.client_id
GROUP BY c.id, c.name, c.age, c.gender, cs.name;
```

## 4. 存储过程设计

### 4.1 计算销售提成存储过程
**存储过程说明**: 自动计算销售记录的员工和店铺提成

```sql
DELIMITER //
CREATE PROCEDURE CalculateSalePremium(
    IN p_sale_id VARCHAR(50)
)
BEGIN
    DECLARE v_employee_premium FLOAT DEFAULT 0.0;
    DECLARE v_shop_premium FLOAT DEFAULT 0.0;
    DECLARE v_total_amount BIGINT DEFAULT 0;
    DECLARE v_employee_rate FLOAT DEFAULT 0.0;
    DECLARE v_shop_rate FLOAT DEFAULT 0.0;
    
    -- 获取销售总金额
    SELECT create_card_total_amount INTO v_total_amount
    FROM sale WHERE id = p_sale_id;
    
    -- 获取员工提成比例（根据角色确定）
    SELECT 
        CASE 
            WHEN r.name = '专家' THEN 0.1
            WHEN r.name = '操作人' THEN 0.05
            ELSE 0.03
        END INTO v_employee_rate
    FROM sale s
    JOIN employee e ON s.seller_id = e.id
    JOIN duty d ON e.id = d.employee_id
    JOIN role r ON d.role_id = r.id
    WHERE s.id = p_sale_id;
    
    -- 获取店铺提成比例
    SELECT cs.shop_premium INTO v_shop_rate
    FROM sale s
    JOIN client c ON s.client_id = c.id
    JOIN cosmeticshop cs ON c.shop_id = cs.id
    WHERE s.id = p_sale_id;
    
    -- 计算提成
    SET v_employee_premium = v_total_amount * v_employee_rate;
    SET v_shop_premium = v_total_amount * v_shop_rate;
    
    -- 更新销售记录
    UPDATE sale 
    SET employee_premium = v_employee_premium,
        shop_premium = v_shop_premium
    WHERE id = p_sale_id;
    
END //
DELIMITER ;
```

### 4.2 生成报表数据存储过程
**存储过程说明**: 生成指定时间范围的销售报表数据

```sql
DELIMITER //
CREATE PROCEDURE GenerateSaleReport(
    IN p_start_date VARCHAR(20),
    IN p_end_date VARCHAR(20),
    IN p_shop_id VARCHAR(50)
)
BEGIN
    SELECT 
        DATE(s.create_card_date) as sale_date,
        COUNT(s.id) as sale_count,
        SUM(s.create_card_total_amount) as total_amount,
        SUM(s.received_amount) as total_received,
        SUM(s.received_earned_amount) as total_earned,
        SUM(s.employee_premium) as total_employee_premium,
        SUM(s.shop_premium) as total_shop_premium,
        COUNT(DISTINCT s.client_id) as client_count
    FROM sale s
    JOIN client c ON s.client_id = c.id
    WHERE s.create_card_date BETWEEN p_start_date AND p_end_date
    AND (p_shop_id IS NULL OR c.shop_id = p_shop_id)
    GROUP BY DATE(s.create_card_date)
    ORDER BY sale_date;
END //
DELIMITER ;
```

## 5. 触发器设计

### 5.1 销售记录插入触发器
**触发器说明**: 在插入销售记录时自动计算提成

```sql
DELIMITER //
CREATE TRIGGER tr_sale_insert_premium
AFTER INSERT ON sale
FOR EACH ROW
BEGIN
    CALL CalculateSalePremium(NEW.id);
END //
DELIMITER ;
```

### 5.2 客户删除触发器
**触发器说明**: 在删除客户前检查是否有关联数据

```sql
DELIMITER //
CREATE TRIGGER tr_client_delete_check
BEFORE DELETE ON client
FOR EACH ROW
BEGIN
    DECLARE sale_count INT DEFAULT 0;
    
    SELECT COUNT(*) INTO sale_count
    FROM sale WHERE client_id = OLD.id;
    
    IF sale_count > 0 THEN
        SIGNAL SQLSTATE '45000' 
        SET MESSAGE_TEXT = 'Cannot delete client with associated sales';
    END IF;
END //
DELIMITER ;
```

## 6. 数据初始化

### 6.1 基础数据插入
```sql
-- 插入角色数据
INSERT INTO role (id, name, description) VALUES
('js000001', '专家', '具有高级权限的专家角色'),
('js000002', '操作人', '具有基础操作权限的角色'),
('js000003', '管理员', '具有系统管理权限的角色');

-- 插入美容院数据
INSERT INTO cosmeticshop (id, name, owner, contact_method, location, size, member_number, discount, shop_premium, description) VALUES
('mr000001', '悦和美容院', '钟悦旻', '13800138000', '北京市朝阳区', '大型', 100, 0.8, 0.1, '专业美容院'),
('mr000002', '美丽人生美容院', '李美丽', '13900139000', '上海市浦东区', '中型', 50, 0.9, 0.05, '高端美容院');

-- 插入美容项目数据
INSERT INTO beautifyskinitem (id, name, price, description) VALUES
('xm000001', '深层补水套餐', 500, '深层补水美容套餐'),
('xm000002', '抗衰老套餐', 800, '抗衰老美容套餐'),
('xm000003', '美白套餐', 600, '美白美容套餐');

-- 插入仪器数据
INSERT INTO tool (id, name, major, price, buy_date, buy_from, operate_expense, description) VALUES
('gj000001', '补水仪', '补水', 5000, '2024-01-01', '专业设备公司', 50, '专业补水仪器'),
('gj000002', '抗衰仪', '抗衰', 8000, '2024-01-01', '专业设备公司', 80, '专业抗衰仪器'),
('gj000003', '美白仪', '美白', 6000, '2024-01-01', '专业设备公司', 60, '专业美白仪器');
```

### 6.2 测试数据插入
```sql
-- 插入员工数据
INSERT INTO employee (id, name, salary, birthday, description, resigned) VALUES
('yg000001', '李四', 5000, '1990-01-01', '资深美容师', '在职'),
('yg000002', '王五', 4500, '1992-05-15', '美容技师', '在职'),
('yg000003', '赵六', 4000, '1995-08-20', '初级美容师', '在职');

-- 插入职责数据
INSERT INTO duty (id, employee_id, role_id, description) VALUES
('zz000001', 'yg000001', 'js000001', '李四担任专家角色'),
('zz000002', 'yg000002', 'js000002', '王五担任操作人角色'),
('zz000003', 'yg000003', 'js000002', '赵六担任操作人角色');

-- 插入用户数据
INSERT INTO user (id, username, password, role) VALUES
('yh000001', 'soveran', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'ADMIN'),
('yh000002', 'expert', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'EXPERT'),
('yh000003', 'operator', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVEFDi', 'OPERATOR');
```

## 7. 性能优化

### 7.1 索引优化策略
- **主键索引**: 所有表都有主键索引
- **外键索引**: 所有外键字段都创建索引
- **查询索引**: 根据常用查询条件创建复合索引
- **唯一索引**: 对唯一性字段创建唯一索引

### 7.2 查询优化建议
- 使用EXPLAIN分析查询执行计划
- 避免SELECT *，只查询需要的字段
- 使用LIMIT限制返回结果数量
- 合理使用JOIN，避免笛卡尔积
- 使用适当的WHERE条件过滤数据

### 7.3 分区策略
对于大数据量表，可以考虑按时间分区：
```sql
-- 销售表按月份分区
ALTER TABLE sale PARTITION BY RANGE (YEAR(STR_TO_DATE(create_card_date, '%Y-%m-%d'))) (
    PARTITION p2024 VALUES LESS THAN (2025),
    PARTITION p2025 VALUES LESS THAN (2026),
    PARTITION p_future VALUES LESS THAN MAXVALUE
);
```

## 8. 备份与恢复

### 8.1 备份策略
```bash
# 全量备份
mysqldump -u root -p yuehe > yuehe_backup_$(date +%Y%m%d).sql

# 增量备份（使用binlog）
mysqlbinlog --start-datetime="2024-12-01 00:00:00" --stop-datetime="2024-12-01 23:59:59" mysql-bin.000001 > yuehe_incremental_20241201.sql
```

### 8.2 恢复策略
```bash
# 全量恢复
mysql -u root -p yuehe < yuehe_backup_20241201.sql

# 增量恢复
mysql -u root -p yuehe < yuehe_incremental_20241201.sql
```

---

**文档版本**: 1.0  
**创建日期**: 2024年12月  
**最后更新**: 2024年12月  
**文档状态**: 待审核
