# 我的淘宝电商项目

## 项目概述

全栈电商系统，包含 6 个微服务模块，采用 SpringCloud + Vue 技术栈。

## 技术架构

### 后端
- **框架**: SpringBoot 2.7.x + SpringCloud 2021.x
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **搜索引擎**: Elasticsearch
- **API文档**: Swagger
- **ORM**: MyBatis Plus

### 前端
- **框架**: Vue 3 + Vite
- **UI组件**: Element Plus
- **状态管理**: Pinia
- **HTTP客户端**: Axios

## 项目决策（讨论确定）

| 决策项 | 选择 | 说明 |
|--------|------|------|
| 微服务拆分 | 6个微服务 | 每个业务模块单独一个服务 |
| 认证授权 | 暂不需要 | MVP阶段先实现核心业务功能 |
| 数据库设计 | 完整设计 | 包含索引、外键、字段注释 |

## 项目结构

### 后端 (myTaoBao/)
```
myTaoBao/
├── pom.xml                    # 父项目
├── api-common/               # 公共模块 - 实体类、枚举、异常
├── api-utils/                # 工具模块 - ID生成器、字符串工具
├── user-service/             # 用户服务 (端口8201)
├── product-service/          # 商品服务 (端口8202)
├── stock-service/            # 库存服务 (端口8203)
├── order-service/            # 订单服务 (端口8204)
├── pay-service/              # 支付服务 (端口8205)
├── trade-service/            # 交易服务 (端口8206)
└── sql/                      # 数据库初始化脚本
```

### 前端 (myTaoBao-web/)
```
myTaoBao-web/
├── src/
│   ├── api/                  # API 接口
│   ├── views/                # 页面视图
│   │   ├── home/            # 首页
│   │   ├── product/         # 商品详情
│   │   ├── order/           # 订单页面
│   │   ├── pay/             # 支付页
│   │   └── user/            # 用户中心
│   ├── router/              # 路由配置
│   ├── stores/              # Pinia 状态管理
│   └── main.js
└── package.json
```

## 模块文档

各模块详细说明请查看对应 README.md：

- [api-common 模块](./api-common/README.md)
- [api-utils 模块](./api-utils/README.md)
- [user-service 用户服务](./user-service/README.md)
- [product-service 商品服务](./product-service/README.md)
- [stock-service 库存服务](./stock-service/README.md)
- [order-service 订单服务](./order-service/README.md)
- [pay-service 支付服务](./pay-service/README.md)
- [trade-service 交易服务](./trade-service/README.md)

---

## 核心模块说明

### 用户模块 (user-service)
- 用户注册/登录
- 用户信息管理
- 收货地址管理
- 端口: 8201

### 商品模块 (product-service)
- 商品 CRUD
- 商品分类
- 端口: 8202

### 库存模块 (stock-service)
- 库存查询
- 库存扣减/回滚
- 端口: 8203

### 订单模块 (order-service)
- 订单创建/查询/取消
- 订单状态管理
- 端口: 8204

### 支付模块 (pay-service)
- 支付创建
- 支付回调（模拟）
- 端口: 8205

### 交易模块 (trade-service)
- 前端统一入口
- 聚合业务接口
- Feign 远程调用其他服务
- 端口: 8206

## 数据库表结构

### 用户模块 (mtaobao_user)
- `user` - 用户表
- `user_address` - 用户地址表

### 商品模块 (mtaobao_product)
- `category` - 商品分类表
- `product` - 商品表
- `product_sku` - 商品SKU表

### 库存模块 (mtaobao_stock)
- `stock` - 库存表

### 订单模块 (mtaobao_order)
- `order` - 订单主表
- `order_item` - 订单明细表

### 支付模块 (mtaobao_pay)
- `payment` - 支付记录表

## 核心功能

### 后端接口
- 用户模块：注册、登录、地址管理
- 商品模块：商品CRUD、商品分类
- 库存模块：库存查询、库存扣减
- 订单模块：订单创建、订单查询、订单取消
- 支付模块：支付创建、支付回调
- 交易模块：聚合接口、流程编排

### 前端页面
- 首页：商品展示
- 商品详情页：商品信息、立即购买
- 下单页：确认订单、选择地址、提交订单
- 支付页：选择支付方式、模拟支付
- 用户中心：订单列表、地址管理

## 启动方式

### 1. 初始化数据库
执行 `sql/` 目录下的 SQL 文件创建数据库和表。

### 2. 启动后端服务
```bash
cd myTaoBao
mvn clean install
# 分别启动各服务
cd user-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
# ... 其他服务同理
```

### 3. 启动前端
```bash
cd myTaoBao-web
npm install
npm run dev
```

前端访问: http://localhost:3000

## API 端口对照

| 服务 | 端口 | 说明 |
|------|------|------|
| user-service | 8201 | 用户服务 |
| product-service | 8202 | 商品服务 |
| stock-service | 8203 | 库存服务 |
| order-service | 8204 | 订单服务 |
| pay-service | 8205 | 支付服务 |
| trade-service | 8206 | 交易服务(前端入口) |

## 注意事项

- 当前版本为 MVP，暂未集成 Nacos/Sentinel 等微服务治理组件
- 密码存储为明文，生产环境需加密
- 支付为模拟实现，需对接真实支付渠道
- 未包含 Spring Security 认证授权（按讨论决策）
- 微服务间通过 HTTP Feign 直连，未使用注册中心
