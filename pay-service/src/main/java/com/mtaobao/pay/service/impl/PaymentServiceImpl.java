package com.mtaobao.pay.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.api.utils.IdGenerator;
import com.mtaobao.pay.entity.Payment;
import com.mtaobao.pay.mapper.PaymentMapper;
import com.mtaobao.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentMapper paymentMapper;

    @Override
    public Payment createPayment(String orderNo, Long userId, Integer payType) {
        // 检查是否已存在支付记录
        Payment exist = getByOrderNo(orderNo);
        if (exist != null && exist.getStatus() == 1) {
            throw new BusinessException("该订单已支付");
        }

        Payment payment = new Payment();
        payment.setId(IdGenerator.nextId());
        payment.setPaymentNo("P" + System.currentTimeMillis() + (int) (Math.random() * 10000));
        payment.setOrderNo(orderNo);
        payment.setUserId(userId);
        payment.setAmount(BigDecimal.ZERO); // 由调用方设置
        payment.setPayType(payType);
        payment.setStatus(0);
        payment.setCreateTime(LocalDateTime.now());
        payment.setUpdateTime(LocalDateTime.now());
        paymentMapper.insert(payment);

        log.info("创建支付记录: {}", payment.getPaymentNo());
        return payment;
    }

    @Override
    public Payment getByOrderNo(String orderNo) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getOrderNo, orderNo);
        return paymentMapper.selectOne(wrapper);
    }

    @Override
    public Payment getByPaymentNo(String paymentNo) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPaymentNo, paymentNo);
        return paymentMapper.selectOne(wrapper);
    }

    @Override
    public void updatePaymentStatus(String paymentNo, Integer status, String tradeNo) {
        LambdaQueryWrapper<Payment> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Payment::getPaymentNo, paymentNo);
        Payment payment = new Payment();
        payment.setStatus(status);
        if (status == 1) {
            payment.setPayTime(LocalDateTime.now());
            payment.setTradeNo(tradeNo);
        }
        payment.setUpdateTime(LocalDateTime.now());
        paymentMapper.update(payment, wrapper);
        log.info("更新支付状态: paymentNo={}, status={}", paymentNo, status);
    }
}
