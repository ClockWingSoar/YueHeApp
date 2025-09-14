# YueHeApp API接口文档

## 1. 接口概述

### 1.1 基本信息
- **基础URL**: `http://localhost:9090`
- **协议**: HTTP/HTTPS
- **数据格式**: JSON
- **字符编码**: UTF-8
- **认证方式**: Session认证

### 1.2 通用响应格式
```json
{
    "code": 200,
    "message": "操作成功",
    "data": {},
    "timestamp": "2024-12-01T10:00:00Z"
}
```

### 1.3 错误码说明
| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未授权访问 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 2. 认证接口

### 2.1 用户登录
**接口地址**: `POST /login`

**请求参数**:
```json
{
    "username": "soveran",
    "password": "5688Sove"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "登录成功",
    "data": {
        "userId": "yh000001",
        "username": "soveran",
        "role": "ADMIN",
        "token": "session_token_here"
    }
}
```

### 2.2 用户登出
**接口地址**: `POST /logout`

**响应示例**:
```json
{
    "code": 200,
    "message": "登出成功",
    "data": null
}
```

## 3. 客户管理接口

### 3.1 获取客户列表
**接口地址**: `GET /getClientList`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |
| name | string | 否 | 客户姓名，模糊查询 |
| shopId | string | 否 | 美容院ID |
| sortBy | string | 否 | 排序字段 |
| sortDir | string | 否 | 排序方向，ASC/DESC |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "content": [
            {
                "id": "kh000001",
                "name": "张三",
                "age": 25,
                "gender": "女",
                "symptom": "皮肤干燥",
                "cosmeticShop": {
                    "id": "mr000001",
                    "name": "悦和美容院"
                }
            }
        ],
        "totalElements": 100,
        "totalPages": 5,
        "currentPage": 1,
        "pageSize": 20
    }
}
```

### 3.2 创建客户
**接口地址**: `POST /createClient`

**请求参数**:
```json
{
    "name": "张三",
    "age": 25,
    "gender": "女",
    "symptom": "皮肤干燥",
    "shopId": "mr000001"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": "kh000001",
        "name": "张三",
        "age": 25,
        "gender": "女",
        "symptom": "皮肤干燥",
        "cosmeticShop": {
            "id": "mr000001",
            "name": "悦和美容院"
        }
    }
}
```

### 3.3 获取客户详情
**接口地址**: `GET /getClientDetail/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | string | 是 | 客户ID |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": "kh000001",
        "name": "张三",
        "age": 25,
        "gender": "女",
        "symptom": "皮肤干燥",
        "cosmeticShop": {
            "id": "mr000001",
            "name": "悦和美容院",
            "discount": 0.8
        },
        "sales": [
            {
                "id": "xs000001",
                "createCardDate": "2024-12-01",
                "createCardTotalAmount": 5000,
                "receivedAmount": 4000
            }
        ]
    }
}
```

### 3.4 更新客户信息
**接口地址**: `PUT /updateClient/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | string | 是 | 客户ID |

**请求参数**:
```json
{
    "name": "张三",
    "age": 26,
    "gender": "女",
    "symptom": "皮肤干燥，需要补水",
    "shopId": "mr000001"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "更新成功",
    "data": {
        "id": "kh000001",
        "name": "张三",
        "age": 26,
        "gender": "女",
        "symptom": "皮肤干燥，需要补水",
        "cosmeticShop": {
            "id": "mr000001",
            "name": "悦和美容院"
        }
    }
}
```

### 3.5 删除客户
**接口地址**: `DELETE /deleteClient/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | string | 是 | 客户ID |

**响应示例**:
```json
{
    "code": 200,
    "message": "删除成功",
    "data": null
}
```

## 4. 销售管理接口

### 4.1 获取销售列表
**接口地址**: `GET /getSaleList`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |
| clientName | string | 否 | 客户姓名 |
| shopId | string | 否 | 美容院ID |
| sellerId | string | 否 | 销售员工ID |
| startDate | string | 否 | 开始日期 |
| endDate | string | 否 | 结束日期 |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "content": [
            {
                "id": "xs000001",
                "client": {
                    "id": "kh000001",
                    "name": "张三"
                },
                "beautifySkinItem": {
                    "id": "xm000001",
                    "name": "深层补水套餐"
                },
                "employee": {
                    "id": "yg000001",
                    "name": "李四"
                },
                "itemNumber": 10,
                "createCardTotalAmount": 5000,
                "receivedAmount": 4000,
                "receivedEarnedAmount": 3200,
                "employeePremium": 200,
                "shopPremium": 100,
                "createCardDate": "2024-12-01",
                "description": "客户首次购买"
            }
        ],
        "totalElements": 50,
        "totalPages": 3,
        "currentPage": 1,
        "pageSize": 20
    }
}
```

### 4.2 创建销售记录
**接口地址**: `POST /createSale`

**请求参数**:
```json
{
    "clientId": "kh000001",
    "beautifySkinItemId": "xm000001",
    "sellerId": "yg000001",
    "itemNumber": 10,
    "createCardTotalAmount": 5000,
    "receivedAmount": 4000,
    "receivedEarnedAmount": 3200,
    "createCardDate": "2024-12-01",
    "description": "客户首次购买"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": "xs000001",
        "client": {
            "id": "kh000001",
            "name": "张三"
        },
        "beautifySkinItem": {
            "id": "xm000001",
            "name": "深层补水套餐"
        },
        "employee": {
            "id": "yg000001",
            "name": "李四"
        },
        "itemNumber": 10,
        "createCardTotalAmount": 5000,
        "receivedAmount": 4000,
        "receivedEarnedAmount": 3200,
        "employeePremium": 200,
        "shopPremium": 100,
        "createCardDate": "2024-12-01",
        "description": "客户首次购买"
    }
}
```

### 4.3 获取销售详情
**接口地址**: `GET /getSaleDetail/{id}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| id | string | 是 | 销售ID |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "id": "xs000001",
        "client": {
            "id": "kh000001",
            "name": "张三",
            "age": 25,
            "gender": "女"
        },
        "beautifySkinItem": {
            "id": "xm000001",
            "name": "深层补水套餐",
            "price": 500
        },
        "employee": {
            "id": "yg000001",
            "name": "李四"
        },
        "itemNumber": 10,
        "createCardTotalAmount": 5000,
        "receivedAmount": 4000,
        "receivedEarnedAmount": 3200,
        "employeePremium": 200,
        "shopPremium": 100,
        "createCardDate": "2024-12-01",
        "description": "客户首次购买",
        "operations": [
            {
                "id": "cz000001",
                "operationDate": "2024-12-01",
                "description": "首次服务",
                "tool": {
                    "id": "gj000001",
                    "name": "补水仪"
                },
                "operator": {
                    "id": "yg000002",
                    "name": "王五"
                }
            }
        ]
    }
}
```

## 5. 操作管理接口

### 5.1 获取操作列表
**接口地址**: `GET /getOperationList`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |
| clientName | string | 否 | 客户姓名 |
| operatorId | string | 否 | 操作员工ID |
| toolId | string | 否 | 仪器ID |
| startDate | string | 否 | 开始日期 |
| endDate | string | 否 | 结束日期 |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "content": [
            {
                "id": "cz000001",
                "sale": {
                    "id": "xs000001",
                    "client": {
                        "id": "kh000001",
                        "name": "张三"
                    }
                },
                "operator": {
                    "id": "yg000002",
                    "name": "王五"
                },
                "tool": {
                    "id": "gj000001",
                    "name": "补水仪"
                },
                "operationDate": "2024-12-01",
                "description": "首次服务"
            }
        ],
        "totalElements": 30,
        "totalPages": 2,
        "currentPage": 1,
        "pageSize": 20
    }
}
```

### 5.2 创建操作记录
**接口地址**: `POST /createOperation`

**请求参数**:
```json
{
    "saleId": "xs000001",
    "operatorId": "yg000002",
    "toolId": "gj000001",
    "operationDate": "2024-12-01",
    "description": "首次服务"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": "cz000001",
        "sale": {
            "id": "xs000001",
            "client": {
                "id": "kh000001",
                "name": "张三"
            }
        },
        "operator": {
            "id": "yg000002",
            "name": "王五"
        },
        "tool": {
            "id": "gj000001",
            "name": "补水仪"
        },
        "operationDate": "2024-12-01",
        "description": "首次服务"
    }
}
```

## 6. 员工管理接口

### 6.1 获取员工列表
**接口地址**: `GET /getEmployeeList`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |
| name | string | 否 | 员工姓名 |
| resigned | string | 否 | 在职状态，在职/离职 |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "content": [
            {
                "id": "yg000001",
                "name": "李四",
                "salary": 5000,
                "birthday": "1990-01-01",
                "description": "资深美容师",
                "resigned": "在职"
            }
        ],
        "totalElements": 20,
        "totalPages": 1,
        "currentPage": 1,
        "pageSize": 20
    }
}
```

### 6.2 创建员工
**接口地址**: `POST /createEmployee`

**请求参数**:
```json
{
    "name": "李四",
    "salary": 5000,
    "birthday": "1990-01-01",
    "description": "资深美容师"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": "yg000001",
        "name": "李四",
        "salary": 5000,
        "birthday": "1990-01-01",
        "description": "资深美容师",
        "resigned": "在职"
    }
}
```

## 7. 美容院管理接口

### 7.1 获取美容院列表
**接口地址**: `GET /getCosmeticShopList`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| page | int | 否 | 页码，默认1 |
| size | int | 否 | 每页数量，默认20 |
| name | string | 否 | 美容院名称 |
| owner | string | 否 | 负责人姓名 |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "content": [
            {
                "id": "mr000001",
                "name": "悦和美容院",
                "owner": "钟悦旻",
                "contactMethod": "13800138000",
                "location": "北京市朝阳区",
                "size": "大型",
                "memberNumber": 100,
                "discount": 0.8,
                "shopPremium": 0.1,
                "description": "专业美容院"
            }
        ],
        "totalElements": 5,
        "totalPages": 1,
        "currentPage": 1,
        "pageSize": 20
    }
}
```

### 7.2 创建美容院
**接口地址**: `POST /createCosmeticShop`

**请求参数**:
```json
{
    "name": "悦和美容院",
    "owner": "钟悦旻",
    "contactMethod": "13800138000",
    "location": "北京市朝阳区",
    "size": "大型",
    "memberNumber": 100,
    "discount": 0.8,
    "shopPremium": 0.1,
    "description": "专业美容院"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "创建成功",
    "data": {
        "id": "mr000001",
        "name": "悦和美容院",
        "owner": "钟悦旻",
        "contactMethod": "13800138000",
        "location": "北京市朝阳区",
        "size": "大型",
        "memberNumber": 100,
        "discount": 0.8,
        "shopPremium": 0.1,
        "description": "专业美容院"
    }
}
```

## 8. 报表接口

### 8.1 销售报表
**接口地址**: `GET /getSaleReport`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| startDate | string | 是 | 开始日期 |
| endDate | string | 是 | 结束日期 |
| shopId | string | 否 | 美容院ID |
| employeeId | string | 否 | 员工ID |
| format | string | 否 | 导出格式，csv/excel/pdf |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "summary": {
            "totalSales": 100000,
            "totalReceived": 80000,
            "totalEarned": 64000,
            "totalPremium": 5000,
            "saleCount": 50,
            "clientCount": 30
        },
        "details": [
            {
                "date": "2024-12-01",
                "sales": 20000,
                "received": 16000,
                "earned": 12800,
                "count": 10
            }
        ]
    }
}
```

### 8.2 客户报表
**接口地址**: `GET /getClientReport`

**请求参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| startDate | string | 是 | 开始日期 |
| endDate | string | 是 | 结束日期 |
| shopId | string | 否 | 美容院ID |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": {
        "summary": {
            "totalClients": 100,
            "newClients": 20,
            "ageDistribution": {
                "18-25": 30,
                "26-35": 40,
                "36-45": 20,
                "46+": 10
            },
            "genderDistribution": {
                "male": 20,
                "female": 80
            }
        }
    }
}
```

## 9. 通用接口

### 9.1 获取下拉选项
**接口地址**: `GET /getDropdownOptions/{type}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | string | 是 | 选项类型，clients/employees/shops/tools/items |

**响应示例**:
```json
{
    "code": 200,
    "message": "查询成功",
    "data": [
        {
            "value": "kh000001",
            "label": "张三"
        },
        {
            "value": "kh000002",
            "label": "李四"
        }
    ]
}
```

### 9.2 数据验证
**接口地址**: `POST /validate/{type}`

**路径参数**:
| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| type | string | 是 | 验证类型，client/sale/operation/employee |

**请求参数**:
```json
{
    "field": "name",
    "value": "张三",
    "id": "kh000001"
}
```

**响应示例**:
```json
{
    "code": 200,
    "message": "验证成功",
    "data": {
        "valid": true,
        "message": "验证通过"
    }
}
```

## 10. 错误处理

### 10.1 常见错误
| 错误码 | 错误信息 | 解决方案 |
|--------|----------|----------|
| 400 | 请求参数错误 | 检查请求参数格式和必填项 |
| 401 | 未授权访问 | 检查登录状态和权限 |
| 403 | 权限不足 | 联系管理员分配权限 |
| 404 | 资源不存在 | 检查资源ID是否正确 |
| 500 | 服务器内部错误 | 联系技术支持 |

### 10.2 错误响应示例
```json
{
    "code": 400,
    "message": "请求参数错误",
    "data": {
        "errors": [
            {
                "field": "name",
                "message": "客户姓名不能为空"
            },
            {
                "field": "age",
                "message": "年龄必须在0-120之间"
            }
        ]
    },
    "timestamp": "2024-12-01T10:00:00Z"
}
```

---

**文档版本**: 1.0  
**创建日期**: 2024年12月  
**最后更新**: 2024年12月  
**文档状态**: 待审核
