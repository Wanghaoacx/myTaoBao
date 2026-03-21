package com.mtaobao.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String paymentNo;
    private String orderNo;
    private Long userId;
    private BigDecimal amount;
    private Integer payType;
    private Integer status;
    private String tradeNo;
    private LocalDateTime payTime;
}
