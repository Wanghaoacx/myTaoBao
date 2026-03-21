# Pay Service - 支付服务

## 模块说明

提供支付创建、支付回调等功能的微服务。

## 端口

`8205`

## 数据库

`mtaobao_pay`

## 主要功能

- 创建支付记录
- 根据订单号查询支付记录
- 根据支付流水号查询
- 更新支付状态（回调）

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /payment/create | 创建支付 |
| GET | /payment/order/{orderNo} | 根据订单号查询 |
| GET | /payment/{paymentNo} | 根据流水号查询 |
| POST | /payment/notify | 支付回调 |

## 支付方式

| 编码 | 说明 |
|------|------|
| 1 | 支付宝 |
| 2 | 微信支付 |
| 3 | 银联支付 |

## 支付状态

| 状态码 | 说明 |
|--------|------|
| 0 | 待支付 |
| 1 | 支付成功 |
| 2 | 支付失败 |

## 数据库表

### payment - 支付记录表
```sql
CREATE TABLE `payment` (
    `id` BIGINT NOT NULL COMMENT '支付ID',
    `payment_no` VARCHAR(50) NOT NULL COMMENT '支付流水号',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `amount` DECIMAL(10,2) NOT NULL COMMENT '支付金额',
    `pay_type` TINYINT NOT NULL COMMENT '支付方式',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '支付状态',
    `trade_no` VARCHAR(100) DEFAULT NULL COMMENT '第三方交易号',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间'
);
```

## 支付流程

1. 用户选择支付方式，调用 `/payment/create` 创建支付记录
2. 跳转到第三方支付平台（模拟）
3. 支付完成后，第三方回调 `/payment/notify`
4. 更新支付状态和订单状态

## 启动方式

```bash
cd pay-service
mvn spring-boot:run
```

## 注意事项

- 当前为模拟支付实现
- 实际生产需对接真实支付渠道（支付宝、微信等）
