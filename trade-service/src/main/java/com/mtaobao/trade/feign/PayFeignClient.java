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
            @RequestParam("orderNo") String orderNo,
            @RequestParam("userId") Long userId,
            @RequestParam("payType") Integer payType,
            @RequestParam("amount") BigDecimal amount);

    @GetMapping("/payment/order/{orderNo}")
    Result<PaymentDTO> getPaymentByOrderNo(@PathVariable("orderNo") String orderNo);

    @GetMapping("/payment/{paymentNo}")
    Result<PaymentDTO> getPaymentByPaymentNo(@PathVariable("paymentNo") String paymentNo);
}
