package com.mtaobao.trade.feign;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.order.dto.OrderCreateDTO;
import com.mtaobao.trade.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "order-service", url = "${feign.order-service.url:http://localhost:8204}")
public interface OrderFeignClient {

    @PostMapping("/order/create")
    Result<OrderDTO> createOrder(@RequestBody OrderCreateDTO dto);

    @GetMapping("/order/{id}")
    Result<OrderDTO> getOrderById(@PathVariable("id") Long id);

    @GetMapping("/order/no/{orderNo}")
    Result<OrderDTO> getOrderByOrderNo(@PathVariable("orderNo") String orderNo);

    @GetMapping("/order/user/{userId}")
    Result<List<OrderDTO>> getOrderByUserId(@PathVariable("userId") Long userId);

    @GetMapping("/order/{id}/items")
    Result<?> getOrderItems(@PathVariable("id") Long id);

    @PutMapping("/order/cancel/{id}")
    Result<Void> cancelOrder(@PathVariable("id") Long id);

    @PutMapping("/order/status/{id}")
    Result<Void> updateOrderStatus(@PathVariable("id") Long id, @RequestParam("status") Integer status);
}
