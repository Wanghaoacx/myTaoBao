package com.mtaobao.pay.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.pay.entity.Payment;
import com.mtaobao.pay.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public Result<Payment> createPayment(
            @RequestParam String orderNo,
            @RequestParam Long userId,
            @RequestParam Integer payType,
            @RequestParam BigDecimal amount) {
        Payment payment = paymentService.createPayment(orderNo, userId, payType);
        // 这里返回payment，amount在实际使用时设置
        return Result.success(payment);
    }

    @GetMapping("/order/{orderNo}")
    public Result<Payment> getByOrderNo(@PathVariable String orderNo) {
        return Result.success(paymentService.getByOrderNo(orderNo));
    }

    @GetMapping("/{paymentNo}")
    public Result<Payment> getByPaymentNo(@PathVariable String paymentNo) {
        return Result.success(paymentService.getByPaymentNo(paymentNo));
    }

    @PostMapping("/notify")
    public Result<Void> paymentNotify(
            @RequestParam String paymentNo,
            @RequestParam Integer status,
            @RequestParam(required = false) String tradeNo) {
        paymentService.updatePaymentStatus(paymentNo, status, tradeNo);
        return Result.success();
    }
}
