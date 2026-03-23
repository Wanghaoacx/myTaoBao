# 淘宝电商微服务系统

全栈电商系统，包含 6 个微服务模块，采用 SpringCloud + Vue 技术栈。

## 项目架构

```
myTaoBao/
├── api-common/          # 公共模块 - 实体类、枚举、异常
├── api-utils/           # 工具模块 - ID生成器、字符串工具
├── user-service/        # 用户服务 (端口8201)
├── product-service/      # 商品服务 (端口8202)
├── stock-service/       # 库存服务 (端口8203)
├── order-service/       # 订单服务 (端口8204)
├── pay-service/         # 支付服务 (端口8205)
└── trade-service/       # 交易服务 (端口8206)
```

## 技术栈

### 后端
- **框架**: SpringBoot 2.7.x + SpringCloud 2021.x
- **数据库**: MySQL 8.0
- **缓存**: Redis
- **ORM**: MyBatis Plus

### 前端
- **框架**: Vue 3 + Vite
- **UI组件**: Element Plus

## 快速开始

### 1. 环境要求
- JDK 8+
- Maven 3.6+
- MySQL 8.0
- Node.js 16+ (前端)

### 2. 初始化数据库

```bash
# 执行 SQL 脚本创建数据库和表
mysql -uroot -p123456 < sql/*.sql
```

### 3. 启动后端服务

```bash
cd myTaoBao
mvn clean install

# 分别启动各服务
cd user-service && mvn spring-boot:run
cd product-service && mvn spring-boot:run
# ... 其他服务同理
```

### 4. 启动前端

```bash
cd myTaoBao-web
npm install
npm run dev
```

## 服务端口

| 服务 | 端口 | 说明 |
|------|------|------|
| user-service | 8201 | 用户服务 |
| product-service | 8202 | 商品服务 |
| stock-service | 8203 | 库存服务 |
| order-service | 8204 | 订单服务 |
| pay-service | 8205 | 支付服务 |
| trade-service | 8206 | 交易服务(前端入口) |

## API 文档

各模块详细说明请查看对应 README.md：
- [api-common 模块](./api-common/README.md)
- [api-utils 模块](./api-utils/README.md)
- [user-service 用户服务](./user-service/README.md)
- [product-service 商品服务](./product-service/README.md)
- [stock-service 库存服务](./stock-service/README.md)
- [order-service 订单服务](./order-service/README.md)
- [pay-service 支付服务](./pay-service/README.md)
- [trade-service 交易服务](./trade-service/README.md)

## 测试数据

测试数据已通过 SQL 脚本初始化，包含：
- 2 个测试用户 (test001/test002, 密码: 123456)
- 12 个商品分类
- 7 个商品
- 3 个用户地址
- 7 条库存记录

## 功能模块

### 用户模块 (user-service)
- 用户注册/登录
- 用户信息管理
- 收货地址管理

### 商品模块 (product-service)
- 商品 CRUD
- 商品分类

### 库存模块 (stock-service)
- 库存查询
- 库存扣减/回滚

### 订单模块 (order-service)
- 订单创建/查询/取消
- 订单状态管理

### 支付模块 (pay-service)
- 支付创建
- 支付回调（模拟）

### 交易模块 (trade-service)
- 前端统一入口
- 聚合业务接口
- Feign 远程调用其他服务

## License

MIT
