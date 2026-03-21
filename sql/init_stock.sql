-- 库存服务数据库
CREATE DATABASE IF NOT EXISTS mtaobao_stock DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE mtaobao_stock;

-- 库存表
CREATE TABLE `stock` (
    `id` BIGINT NOT NULL COMMENT '库存ID',
    `product_id` BIGINT NOT NULL COMMENT '商品ID',
    `sku_id` BIGINT DEFAULT NULL COMMENT 'SKU ID',
    `total_stock` INT NOT NULL DEFAULT 0 COMMENT '总库存',
    `available_stock` INT NOT NULL DEFAULT 0 COMMENT '可用库存',
    `locked_stock` INT NOT NULL DEFAULT 0 COMMENT '锁定库存',
    `warning_stock` INT NOT NULL DEFAULT 10 COMMENT '预警库存',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_product_sku` (`product_id`, `sku_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存表';
