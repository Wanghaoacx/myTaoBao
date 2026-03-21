package com.mtaobao.trade.dto;

import com.sun.istack.NotNull;
import lombok.Data;

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
