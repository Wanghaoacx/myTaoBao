package com.mtaobao.pay.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.pay.entity.Payment;
import com.mtaobao.pay.mapper.PaymentMapper;
import com.mtaobao.pay.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * PaymentService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment testPayment;

    @BeforeEach
    void setUp() {
        testPayment = new Payment();
        testPayment.setId(1L);
        testPayment.setPaymentNo("P123456789");
        testPayment.setOrderNo("O123456789");
        testPayment.setUserId(1001L);
        testPayment.setAmount(new BigDecimal("5999.00"));
        testPayment.setPayType(1);
        testPayment.setStatus(0);
    }

    /**
     * 测试创建支付 - 成功
     */
    @Test
    void testCreatePayment_Success() {
        // Given
        when(paymentMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(null);
        when(paymentMapper.insert(any(Payment.class))).thenReturn(1);

        // When
        Payment result = paymentService.createPayment("O123456789", 1001L, 1);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getOrderNo()).isEqualTo("O123456789");
        assertThat(result.getStatus()).isEqualTo(0);
        verify(paymentMapper).insert(any(Payment.class));
    }

    /**
     * 测试创建支付 - 订单已支付
     */
    @Test
    void testCreatePayment_AlreadyPaid() {
        // Given
        testPayment.setStatus(1); // 已支付
        when(paymentMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testPayment);

        // When & Then
        assertThatThrownBy(() -> paymentService.createPayment("O123456789", 1001L, 1))
                .isInstanceOf(BusinessException.class)
                .hasMessage("该订单已支付");
    }

    /**
     * 测试根据订单号查询支付记录
     */
    @Test
    void testGetByOrderNo() {
        // Given
        when(paymentMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testPayment);

        // When
        Payment result = paymentService.getByOrderNo("O123456789");

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getPaymentNo()).isEqualTo("P123456789");
    }

    /**
     * 测试根据支付单号查询支付记录
     */
    @Test
    void testGetByPaymentNo() {
        // Given
        when(paymentMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testPayment);

        // When
        Payment result = paymentService.getByPaymentNo("P123456789");

        // Then
        assertThat(result).isNotNull();
    }

    /**
     * 测试更新支付状态 - 支付成功
     */
    @Test
    void testUpdatePaymentStatus_Success() {
        // Given
        when(paymentMapper.update(any(Payment.class), any(LambdaQueryWrapper.class))).thenReturn(1);

        // When
        paymentService.updatePaymentStatus("P123456789", 1, "TRADE123456");

        // Then
        verify(paymentMapper).update(any(Payment.class), any(LambdaQueryWrapper.class));
    }

    /**
     * 测试更新支付状态 - 支付失败
     */
    @Test
    void testUpdatePaymentStatus_Failed() {
        // Given
        when(paymentMapper.update(any(Payment.class), any(LambdaQueryWrapper.class))).thenReturn(1);

        // When
        paymentService.updatePaymentStatus("P123456789", 2, null);

        // Then
        verify(paymentMapper).update(any(Payment.class), any(LambdaQueryWrapper.class));
    }
}
