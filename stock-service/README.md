# Stock Service - 库存服务

## 模块说明

提供库存查询、库存扣减、库存回滚等功能的微服务。

## 端口

`8203`

## 数据库

`mtaobao_stock`

## 主要功能

- 根据商品ID查询库存
- 扣减库存（下单时调用）
- 回滚库存（取消订单时调用）

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /stock/{productId} | 获取商品库存 |
| POST | /stock/deduct | 扣减库存 |
| POST | /stock/rollback | 回滚库存 |

## 数据库表

### stock - 库存表
```sql
CREATE TABLE `stock` (
    `id` BIGINT NOT NULL COMMENT '库存ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `sku_id` BIGINT DEFAULT NULL COMMENT 'SKU ID',
    `total_stock` INT NOT NULL DEFAULT 0 COMMENT '总库存',
    `available_stock` INT NOT NULL DEFAULT 0 COMMENT '可用库存',
    `locked_stock` INT NOT NULL DEFAULT 0 COMMENT '锁定库存',
    `warning_stock` INT NOT NULL DEFAULT 10 COMMENT '预警库存'
);
```

## 库存流程

1. **扣减库存**: 用户下单时，调用 `/stock/deduct` 扣减库存
2. **回滚库存**: 订单取消时，调用 `/stock/rollback` 回滚库存

## 启动方式

```bash
cd stock-service
mvn spring-boot:run
```
