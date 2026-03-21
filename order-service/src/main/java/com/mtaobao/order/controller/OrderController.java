package com.mtaobao.order.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.order.dto.OrderCreateDTO;
import com.mtaobao.order.entity.Order;
import com.mtaobao.order.entity.OrderItem;
import com.mtaobao.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody OrderCreateDTO dto) {
        return Result.success(orderService.createOrder(dto));
    }

    @GetMapping("/{id}")
    public Result<Order> getById(@PathVariable Long id) {
        return Result.success(orderService.getById(id));
    }

    @GetMapping("/no/{orderNo}")
    public Result<Order> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(orderService.getByOrderNo(orderNo));
    }

    @GetMapping("/user/{userId}")
    public Result<List<Order>> getByUserId(@PathVariable Long userId) {
        return Result.success(orderService.getByUserId(userId));
    }

    @GetMapping("/{id}/items")
    public Result<List<OrderItem>> getOrderItems(@PathVariable Long id) {
        return Result.success(orderService.getOrderItems(id));
    }

    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        orderService.updateStatus(id, status);
        return Result.success();
    }

    @PutMapping("/cancel/{id}")
    public Result<Void> cancelOrder(@PathVariable Long id) {
        orderService.cancelOrder(id);
        return Result.success();
    }
}
