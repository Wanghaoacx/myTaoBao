package com.mtaobao.trade.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.trade.dto.OrderDTO;
import com.mtaobao.trade.dto.PaymentDTO;
import com.mtaobao.trade.dto.ProductDTO;
import com.mtaobao.trade.dto.UserAddressDTO;
import com.mtaobao.trade.feign.OrderFeignClient;
import com.mtaobao.trade.feign.PayFeignClient;
import com.mtaobao.trade.feign.ProductFeignClient;
import com.mtaobao.trade.feign.UserFeignClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

/**
 * TradeController 单元测试
 * 注意: 这是一个聚合服务，主要测试Controller层的基本逻辑
 */
@ExtendWith(MockitoExtension.class)
class TradeControllerTest {

    @Mock
    private UserFeignClient userFeignClient;

    @Mock
    private ProductFeignClient productFeignClient;

    @Mock
    private OrderFeignClient orderFeignClient;

    @Mock
    private PayFeignClient payFeignClient;

    @InjectMocks
    private TradeController tradeController;

    /**
     * 测试获取首页商品列表
     */
    @Test
    void testGetIndexProducts() {
        // Given
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("iPhone 15");
        product.setPrice(new BigDecimal("5999.00"));

        when(productFeignClient.getCategoryList()).thenReturn(Result.success(null));
        when(productFeignClient.getProductList(any(), anyInt()))
                .thenReturn(Result.success(Arrays.asList(product)));

        // When
        Result<?> result = tradeController.getIndexProducts();

        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData()).isNotNull();
    }

    /**
     * 测试获取商品详情
     */
    @Test
    void testGetProductDetail() {
        // Given
        ProductDTO product = new ProductDTO();
        product.setId(1L);
        product.setName("iPhone 15");
        product.setPrice(new BigDecimal("5999.00"));

        when(productFeignClient.getProductById(1L))
                .thenReturn(Result.success(product));

        // When
        Result<ProductDTO> result = tradeController.getProductDetail(1L);

        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData().getName()).isEqualTo("iPhone 15");
    }

    /**
     * 测试获取用户地址列表
     */
    @Test
    void testGetUserAddresses() {
        // Given
        UserAddressDTO address = new UserAddressDTO();
        address.setId(1L);
        address.setReceiverName("张三");
        address.setReceiverPhone("13800138000");

        when(userFeignClient.getAddressList(1001L))
                .thenReturn(Result.success(Arrays.asList(address)));

        // When
        Result<List<UserAddressDTO>> result = tradeController.getUserAddresses(1001L);

        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData()).hasSize(1);
    }

    /**
     * 测试获取订单详情
     */
    @Test
    void testGetOrderDetail() {
        // Given
        OrderDTO order = new OrderDTO();
        order.setId(1L);
        order.setOrderNo("O123456789");
        order.setTotalAmount(new BigDecimal("5999.00"));

        when(orderFeignClient.getOrderById(1L))
                .thenReturn(Result.success(order));

        // When
        Result<OrderDTO> result = tradeController.getOrderDetail(1L);

        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData().getOrderNo()).isEqualTo("O123456789");
    }

    /**
     * 测试获取用户订单列表
     */
    @Test
    void testGetUserOrders() {
        // Given
        OrderDTO order = new OrderDTO();
        order.setId(1L);
        order.setOrderNo("O123456789");

        when(orderFeignClient.getOrderByUserId(1001L))
                .thenReturn(Result.success(Arrays.asList(order)));

        // When
        Result<List<OrderDTO>> result = tradeController.getUserOrders(1001L);

        // Then
        assertThat(result.getCode()).isEqualTo(200);
        assertThat(result.getData()).hasSize(1);
    }

    /**
     * 测试创建支付
     */
    @Test
    void testCreatePayment() {
        // Given
        OrderDTO order = new OrderDTO();
        order.setId(1L);
        order.setOrderNo("O123456789");
        order.setPayAmount(new BigDecimal("5999.00"));

        PaymentDTO payment = new PaymentDTO();
        payment.setId(1L);
        payment.setPaymentNo("P123456789");
        payment.setStatus(0);

        when(orderFeignClient.getOrderByOrderNo("O123456789"))
                .thenReturn(Result.success(order));
        when(payFeignClient.createPayment(anyString(), anyLong(), anyInt(), any()))
                .thenReturn(Result.success(payment));
        when(orderFeignClient.updateOrderStatus(1L, 1))
                .thenReturn(Result.success(null));

        // When
        Result<PaymentDTO> result = tradeController.createPayment("O123456789", 1001L, 1);

        // Then
        assertThat(result.getCode()).isEqualTo(200);
    }

    /**
     * 测试模拟支付
     */
    @Test
    void testSimulatePay() {
        // Given
        OrderDTO order = new OrderDTO();
        order.setId(1L);
        order.setOrderNo("O123456789");

        when(orderFeignClient.getOrderByOrderNo("O123456789"))
                .thenReturn(Result.success(order));
        when(orderFeignClient.updateOrderStatus(1L, 1))
                .thenReturn(Result.success(null));

        // When
        Result<Void> result = tradeController.simulatePay("O123456789");

        // Then
        assertThat(result.getCode()).isEqualTo(200);
    }
}
