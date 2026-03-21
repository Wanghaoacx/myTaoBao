# User Service - 用户服务

## 模块说明

提供用户注册、登录、地址管理等功能的微服务。

## 端口

`8201`

## 数据库

`mtaobao_user`

## 主要功能

### 用户管理
- 用户注册
- 用户登录
- 获取用户信息

### 地址管理
- 获取用户地址列表
- 获取默认地址
- 新增地址
- 更新地址
- 删除地址
- 设置默认地址

## API 接口

### 用户接口

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | /user/register | 用户注册 |
| POST | /user/login | 用户登录 |
| GET | /user/{id} | 获取用户信息 |

### 地址接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /user/address/list | 获取地址列表 |
| GET | /user/address/default | 获取默认地址 |
| GET | /user/address/{id} | 获取地址详情 |
| POST | /user/address | 新增地址 |
| PUT | /user/address | 更新地址 |
| DELETE | /user/address/{id} | 删除地址 |
| PUT | /user/address/default | 设置默认地址 |

## 数据库表

### user - 用户表
```sql
CREATE TABLE `user` (
    `id` BIGINT NOT NULL COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(100) NOT NULL COMMENT '密码',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `phone` VARCHAR(20) DEFAULT NULL COMMENT '手机号',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `avatar` VARCHAR(255) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT NOT NULL DEFAULT 1 COMMENT '状态: 0-禁用 1-正常',
    `create_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `update_time` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    `deleted` TINYINT NOT NULL DEFAULT 0 COMMENT '逻辑删除'
);
```

### user_address - 用户地址表
```sql
CREATE TABLE `user_address` (
    `id` BIGINT NOT NULL COMMENT '地址ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `receiver_name` VARCHAR(50) NOT NULL COMMENT '收货人姓名',
    `receiver_phone` VARCHAR(20) NOT NULL COMMENT '收货人电话',
    `province` VARCHAR(50) NOT NULL COMMENT '省份',
    `city` VARCHAR(50) NOT NULL COMMENT '城市',
    `district` VARCHAR(50) NOT NULL COMMENT '区县',
    `detail_address` VARCHAR(200) NOT NULL COMMENT '详细地址',
    `postal_code` VARCHAR(10) DEFAULT NULL COMMENT '邮政编码',
    `is_default` TINYINT NOT NULL DEFAULT 0 COMMENT '是否默认: 0-否 1-是'
);
```

## 启动方式

```bash
cd user-service
mvn spring-boot:run
```
