package com.mtaobao.order.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.order.dto.OrderCreateDTO;
import com.mtaobao.order.entity.Order;
import com.mtaobao.order.entity.OrderItem;
import com.mtaobao.order.mapper.OrderItemMapper;
import com.mtaobao.order.mapper.OrderMapper;
import com.mtaobao.order.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * OrderService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private OrderItemMapper orderItemMapper;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order testOrder;
    private OrderCreateDTO orderCreateDTO;

    @BeforeEach
    void setUp() {
        testOrder = new Order();
        testOrder.setId(1L);
        testOrder.setOrderNo("O123456789");
        testOrder.setUserId(1001L);
        testOrder.setTotalAmount(new BigDecimal("5999.00"));
        testOrder.setPayAmount(new BigDecimal("5999.00"));
        testOrder.setStatus(0);
        testOrder.setReceiverName("张三");
        testOrder.setReceiverPhone("13800138000");
        testOrder.setReceiverAddress("北京市朝阳区xxx");
        testOrder.setDeleted(0);

        orderCreateDTO = new OrderCreateDTO();
        orderCreateDTO.setUserId(1001L);
        orderCreateDTO.setReceiverName("张三");
        orderCreateDTO.setReceiverPhone("13800138000");
        orderCreateDTO.setReceiverAddress("北京市朝阳区xxx");
    }

    /**
     * 测试创建订单
     */
    @Test
    void testCreateOrder() {
        // Given
        OrderCreateDTO.OrderItemDTO item = new OrderCreateDTO.OrderItemDTO();
        item.setProductId(1001L);
        item.setProductName("iPhone 15");
        item.setProductImage("http://example.com/iphone.jpg");
        item.setPrice(new BigDecimal("5999.00"));
        item.setQuantity(1);
        orderCreateDTO.setItems(Arrays.asList(item));

        when(orderMapper.insert(any(Order.class))).thenReturn(1);
        when(orderItemMapper.insert(any(OrderItem.class))).thenReturn(1);

        // When
        Order result = orderService.createOrder(orderCreateDTO);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getUserId()).isEqualTo(1001L);
        assertThat(result.getTotalAmount()).isEqualTo(new BigDecimal("5999.00"));
        verify(orderMapper).insert(any(Order.class));
        verify(orderItemMapper).insert(any(OrderItem.class));
    }

    /**
     * 测试根据ID查询订单
     */
    @Test
    void testGetById() {
        // Given
        when(orderMapper.selectById(1L)).thenReturn(testOrder);

        // When
        Order result = orderService.getById(1L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderNo()).isEqualTo("O123456789");
    }

    /**
     * 测试根据订单号查询订单
     */
    @Test
    void testGetByOrderNo() {
        // Given
        when(orderMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testOrder);

        // When
        Order result = orderService.getByOrderNo("O123456789");

        // Then
        assertThat(result).isNotNull();
    }

    /**
     * 测试根据用户ID查询订单列表
     */
    @Test
    void testGetByUserId() {
        // Given
        List<Order> orders = Arrays.asList(testOrder);
        when(orderMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(orders);

        // When
        List<Order> result = orderService.getByUserId(1001L);

        // Then
        assertThat(result).hasSize(1);
    }

    /**
     * 测试取消订单 - 成功
     */
    @Test
    void testCancelOrder_Success() {
        // Given
        when(orderMapper.selectById(1L)).thenReturn(testOrder);
        when(orderMapper.updateById(any(Order.class))).thenReturn(1);

        // When
        orderService.cancelOrder(1L);

        // Then
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);
        verify(orderMapper).updateById(captor.capture());
        assertThat(captor.getValue().getStatus()).isEqualTo(4);
    }

    /**
     * 测试取消订单 - 订单不存在
     */
    @Test
    void testCancelOrder_NotFound() {
        // Given
        when(orderMapper.selectById(9999L)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> orderService.cancelOrder(9999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("订单不存在");
    }

    /**
     * 测试取消订单 - 订单状态不允许取消
     */
    @Test
    void testCancelOrder_InvalidStatus() {
        // Given
        testOrder.setStatus(1); // 已支付状态
        when(orderMapper.selectById(1L)).thenReturn(testOrder);

        // When & Then
        assertThatThrownBy(() -> orderService.cancelOrder(1L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("订单状态不允许取消");
    }

    /**
     * 测试更新订单状态
     */
    @Test
    void testUpdateStatus() {
        // Given
        when(orderMapper.updateById(any(Order.class))).thenReturn(1);

        // When
        orderService.updateStatus(1L, 1);

        // Then
        verify(orderMapper).updateById(any(Order.class));
    }
}
