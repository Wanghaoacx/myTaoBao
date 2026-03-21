# Order Service - 订单服务

## 模块说明

提供订单创建、查询、取消等功能的微服务。

## 端口

`8204`

## 数据库

`mtaobao_order`

## 主要功能

- 创建订单
- 查询订单详情
- 查询用户订单列表
- 更新订单状态
- 取消订单
- 获取订单明细

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /order/create | 创建订单 |
| GET | /order/{id} | 获取订单详情 |
| GET | /order/no/{orderNo} | 根据订单号查询 |
| GET | /order/user/{userId} | 获取用户订单列表 |
| GET | /order/{id}/items | 获取订单明细 |
| PUT | /order/status/{id} | 更新订单状态 |
| PUT | /order/cancel/{id} | 取消订单 |

## 订单状态

| 状态码 | 说明 |
|--------|------|
| 0 | 待支付 |
| 1 | 已支付 |
| 2 | 已发货 |
| 3 | 已完成 |
| 4 | 已取消 |

## 数据库表

### order - 订单主表
```sql
CREATE TABLE `order` (
    `id` BIGINT NOT NULL COMMENT '订单ID',
    `order_no` VARCHAR(50) NOT NULL COMMENT '订单编号',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `total_amount` DECIMAL(10,2) NOT NULL COMMENT '订单总金额',
    `pay_amount` DECIMAL(10,2) NOT NULL COMMENT '实付金额',
    `freight_amount` DECIMAL(10,2) DEFAULT 0.00 COMMENT '运费',
    `status` TINYINT NOT NULL DEFAULT 0 COMMENT '订单状态',
    `receiver_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
    `receiver_address` VARCHAR(200) NOT NULL COMMENT '收货地址',
    `remark` VARCHAR(500) DEFAULT NULL COMMENT '订单备注',
    `pay_time` DATETIME DEFAULT NULL COMMENT '支付时间',
    `delivery_time` DATETIME DEFAULT NULL COMMENT '发货时间',
    `receive_time` DATETIME DEFAULT NULL COMMENT '收货时间'
);
```

### order_item - 订单明细表
```sql
CREATE TABLE `order_item` (
    `id` BIGINT NOT NULL COMMENT '明细ID',
    `order_id` BIGINT NOT NULL COMMENT '订单ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `product_name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `product_image` VARCHAR(255) DEFAULT NULL COMMENT '商品图片',
    `price` DECIMAL(10,2) NOT NULL COMMENT '单价',
    `quantity` INT NOT NULL COMMENT '数量',
    `total_price` DECIMAL(10,2) NOT NULL COMMENT '小计'
);
```

## 订单创建流程

1. 接收 OrderCreateDTO 请求
2. 计算订单总金额
3. 生成订单编号
4. 保存订单主表和明细表
5. 返回订单信息

## 启动方式

```bash
cd order-service
mvn spring-boot:run
```
