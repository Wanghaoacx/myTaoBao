# Product Service - 商品服务

## 模块说明

提供商品管理、商品分类等功能的微服务。

## 端口

`8202`

## 数据库

`mtaobao_product`

## 主要功能

### 商品管理
- 分页查询商品
- 获取商品详情
- 获取商品列表
- 新增商品
- 更新商品
- 上架/下架商品

### 分类管理
- 获取所有分类
- 根据父ID获取子分类
- 获取分类详情

## API 接口

### 商品接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /product/page | 分页查询商品 |
| GET | /product/{id} | 获取商品详情 |
| GET | /product/list | 获取商品列表 |
| POST | /product | 新增商品 |
| PUT | /product | 更新商品 |
| PUT | /product/status/{id} | 更新商品状态 |

### 分类接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /category/list | 获取所有分类 |
| GET | /category/children | 获取子分类 |
| GET | /category/{id} | 获取分类详情 |

## 数据库表

### category - 商品分类表
```sql
CREATE TABLE `category` (
    `id` BIGINT NOT NULL COMMENT '分类ID',
    `parent_id` BIGINT NOT NULL DEFAULT 0 COMMENT '父分类ID',
    `name` VARCHAR(50) NOT NULL COMMENT '分类名称',
    `level` TINYINT NOT NULL DEFAULT 1 COMMENT '层级',
    `sort` INT NOT NULL DEFAULT 0 COMMENT '排序',
    `icon` VARCHAR(255) DEFAULT NULL COMMENT '图标',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态'
);
```

### product - 商品表
```sql
CREATE TABLE `product` (
    `id` BIGINT NOT NULL COMMENT '商品ID',
    `category_id` BIGINT NOT NULL COMMENT '分类ID',
    `name` VARCHAR(200) NOT NULL COMMENT '商品名称',
    `main_image` VARCHAR(255) DEFAULT NULL COMMENT '主图',
    `images` TEXT COMMENT '商品图片列表',
    `description` TEXT COMMENT '商品描述',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `original_price` DECIMAL(10,2) DEFAULT NULL COMMENT '原价',
    `sales_count` INT NOT NULL DEFAULT 0 COMMENT '销量',
    `stock` INT NOT NULL DEFAULT 0 COMMENT '库存',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-下架 1-上架'
);
```

### product_sku - 商品SKU表
```sql
CREATE TABLE `product_sku` (
    `id` BIGINT NOT NULL COMMENT 'SKU ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `sku_code` VARCHAR(50) NOT NULL COMMENT 'SKU编码',
    `spec_data` VARCHAR(500) DEFAULT NULL COMMENT '规格数据',
    `price` DECIMAL(10,2) NOT NULL COMMENT '价格',
    `stock` INT NOT NULL DEFAULT 0 COMMENT '库存',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态'
);
```

## 启动方式

```bash
cd product-service
mvn spring-boot:run
```
