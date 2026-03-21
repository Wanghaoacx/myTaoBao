package com.mtaobao.trade.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class ProductDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long categoryId;
    private String name;
    private String mainImage;
    private String images;
    private String description;
    private BigDecimal price;
    private BigDecimal originalPrice;
    private Integer salesCount;
    private Integer stock;
    private Integer status;
}
