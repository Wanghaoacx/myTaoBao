package com.mtaobao.trade.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.order.dto.OrderCreateDTO;
import com.mtaobao.trade.dto.*;
import com.mtaobao.trade.feign.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/trade")
@RequiredArgsConstructor
public class TradeController {

    private final UserFeignClient userFeignClient;
    private final ProductFeignClient productFeignClient;
    private final OrderFeignClient orderFeignClient;
    private final PayFeignClient payFeignClient;

    /**
     * 首页商品列表
     */
    @GetMapping("/index/products")
    public Result<Map<String, Object>> getIndexProducts() {
        // 获取商品分类
        Result<?> categoryResult = productFeignClient.getCategoryList();

        // 获取热门商品
        Result<List<ProductDTO>> productResult = productFeignClient.getProductList(null, 20);

        Map<String, Object> data = new HashMap<>();
        data.put("categories", categoryResult.getData());
        data.put("products", productResult.getData());
        return Result.success(data);
    }

    /**
     * 商品详情
     */
    @GetMapping("/product/{id}")
    public Result<ProductDTO> getProductDetail(@PathVariable Long id) {
        return productFeignClient.getProductById(id);
    }

    /**
     * 用户地址列表
     */
    @GetMapping("/user/addresses")
    public Result<List<UserAddressDTO>> getUserAddresses(@RequestParam Long userId) {
        return userFeignClient.getAddressList(userId);
    }

    /**
     * 用户默认地址
     */
    @GetMapping("/user/default-address")
    public Result<UserAddressDTO> getDefaultAddress(@RequestParam Long userId) {
        return userFeignClient.getDefaultAddress(userId);
    }

    /**
     * 创建订单
     */
    @PostMapping("/order/create")
    public Result<OrderDTO> createOrder(@RequestBody OrderCreateRequest request) {
        // 1. 获取地址信息
        UserAddressDTO address = userFeignClient.getAddressById(request.getAddressId()).getData();
        if (address == null) {
            return Result.error("地址不存在");
        }

        // 2. 构建订单项
        List<OrderCreateDTO.OrderItemDTO> items = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderCreateRequest.OrderItemRequest itemRequest : request.getItems()) {
            ProductDTO product = productFeignClient.getProductById(itemRequest.getProductId()).getData();
            if (product == null) {
                return Result.error("商品不存在: " + itemRequest.getProductId());
            }

            OrderCreateDTO.OrderItemDTO item = new OrderCreateDTO.OrderItemDTO();
            item.setProductId(product.getId());
            item.setProductName(product.getName());
            item.setProductImage(product.getMainImage());
            item.setPrice(product.getPrice());
            item.setQuantity(itemRequest.getQuantity());
            items.add(item);

            totalAmount = totalAmount.add(product.getPrice().multiply(new BigDecimal(itemRequest.getQuantity())));
        }

        // 3. 创建订单
        OrderCreateDTO orderDTO = new OrderCreateDTO();
        orderDTO.setUserId(request.getUserId());
        orderDTO.setReceiverName(address.getReceiverName());
        orderDTO.setReceiverPhone(address.getReceiverPhone());
        orderDTO.setReceiverAddress(address.getProvince() + address.getCity() + address.getDistrict() + address.getDetailAddress());
        orderDTO.setRemark(request.getRemark());
        orderDTO.setItems(items);

        Result<OrderDTO> orderResult = orderFeignClient.createOrder(orderDTO);
        return orderResult;
    }

    /**
     * 订单详情
     */
    @GetMapping("/order/{id}")
    public Result<OrderDTO> getOrderDetail(@PathVariable Long id) {
        return orderFeignClient.getOrderById(id);
    }

    /**
     * 用户订单列表
     */
    @GetMapping("/orders")
    public Result<List<OrderDTO>> getUserOrders(@RequestParam Long userId) {
        return orderFeignClient.getOrderByUserId(userId);
    }

    /**
     * 取消订单
     */
    @PostMapping("/order/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        return orderFeignClient.cancelOrder(id);
    }

    /**
     * 创建支付
     */
    @PostMapping("/pay/create")
    public Result<PaymentDTO> createPayment(
            @RequestParam String orderNo,
            @RequestParam Long userId,
            @RequestParam Integer payType) {
        // 获取订单信息
        OrderDTO order = orderFeignClient.getOrderByOrderNo(orderNo).getData();
        if (order == null) {
            return Result.error("订单不存在");
        }

        // 创建支付记录
        Result<PaymentDTO> paymentResult = payFeignClient.createPayment(
                orderNo, userId, payType, order.getPayAmount());

        // 模拟支付成功
        if (paymentResult.getData() != null) {
            // 更新订单状态为已支付
            orderFeignClient.updateOrderStatus(order.getId(), 1);
        }

        return paymentResult;
    }

    /**
     * 模拟支付
     */
    @PostMapping("/pay/simulate")
    public Result<Void> simulatePay(@RequestParam String orderNo) {
        // 1. 获取订单
        OrderDTO order = orderFeignClient.getOrderByOrderNo(orderNo).getData();
        if (order == null) {
            return Result.error("订单不存在");
        }

        // 2. 更新订单状态为已支付
        orderFeignClient.updateOrderStatus(order.getId(), 1);

        return Result.success();
    }
}
