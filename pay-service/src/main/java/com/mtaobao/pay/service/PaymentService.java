package com.mtaobao.pay.service;

import com.mtaobao.pay.entity.Payment;

public interface PaymentService {
    Payment createPayment(String orderNo, Long userId, Integer payType);
    Payment getByOrderNo(String orderNo);
    Payment getByPaymentNo(String paymentNo);
    void updatePaymentStatus(String paymentNo, Integer status, String tradeNo);
}
