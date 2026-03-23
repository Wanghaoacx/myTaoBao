package com.mtaobao.trade.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderCreateRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Long addressId;
    private String remark;
    @NotNull
    private List<OrderItemRequest> items;

    @Data
    public static class OrderItemRequest {
        @NotNull
        private Long productId;
        @NotNull
        private Integer quantity;
    }
}
