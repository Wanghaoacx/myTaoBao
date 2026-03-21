package com.mtaobao.trade.feign;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.trade.dto.PaymentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@FeignClient(name = "pay-service", url = "${feign.pay-service.url:http://localhost:8205}")
public interface PayFeignClient {

    @PostMapping("/payment/create")
    Result<PaymentDTO> createPayment(
            @RequestParam String orderNo,
            @RequestParam Long userId,
            @RequestParam Integer payType,
            @RequestParam BigDecimal amount);

    @GetMapping("/payment/order/{orderNo}")
    Result<PaymentDTO> getPaymentByOrderNo(@PathVariable String orderNo);

    @GetMapping("/payment/{paymentNo}")
    Result<PaymentDTO> getPaymentByPaymentNo(@PathVariable String paymentNo);
}
