package com.mtaobao.order.service;

import com.mtaobao.order.dto.OrderCreateDTO;
import com.mtaobao.order.entity.Order;
import com.mtaobao.order.entity.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(OrderCreateDTO dto);
    Order getById(Long id);
    Order getByOrderNo(String orderNo);
    List<Order> getByUserId(Long userId);
    void updateStatus(Long id, Integer status);
    void cancelOrder(Long id);
    List<OrderItem> getOrderItems(Long orderId);
}
