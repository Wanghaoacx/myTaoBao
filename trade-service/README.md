# Trade Service - 交易服务

## 模块说明

交易服务是前端统一的入口，聚合了其他微服务的业务接口，通过 Feign 远程调用各业务服务。

## 端口

`8206`

## 主要功能

- 首页数据聚合
- 商品详情查询
- 用户地址查询
- 订单创建（聚合商品、用户服务）
- 订单列表查询
- 支付流程（聚合订单、支付服务）

## API 接口

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | /trade/index/products | 获取首页商品数据 |
| GET | /trade/product/{id} | 获取商品详情 |
| GET | /trade/user/addresses | 获取用户地址列表 |
| GET | /trade/user/default-address | 获取默认地址 |
| POST | /trade/order/create | 创建订单 |
| GET | /trade/order/{id} | 获取订单详情 |
| GET | /trade/orders | 获取用户订单列表 |
| POST | /trade/order/cancel/{id} | 取消订单 |
| POST | /trade/pay/create | 创建支付 |
| POST | /trade/pay/simulate | 模拟支付 |

## Feign 客户端

trade-service 通过 Feign 调用其他服务：

```java
@FeignClient(name = "user-service", url = "http://localhost:8201")
public interface UserFeignClient { ... }

@FeignClient(name = "product-service", url = "http://localhost:8202")
public interface ProductFeignClient { ... }

@FeignClient(name = "order-service", url = "http://localhost:8204")
public interface OrderFeignClient { ... }

@FeignClient(name = "pay-service", url = "http://localhost:8205")
public interface PayFeignClient { ... }
```

## 交易流程

```
1. 首页 -> 获取商品列表
2. 商品详情 -> 立即购买
3. 订单确认 -> 选择地址
4. 提交订单 -> trade-service 聚合调用
   4.1 查询商品信息
   4.2 查询地址信息
   4.3 调用 order-service 创建订单
5. 支付页面 -> 模拟支付
6. 支付成功 -> 更新订单状态
```

## 启动方式

```bash
cd trade-service
mvn spring-boot:run
```

## 注意事项

- trade-service 是前端唯一直接访问的后端服务
- 其他服务不对外暴露，起到隔离作用
- 当前使用 HTTP 直连，未使用注册中心
