package com.mtaobao.product.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体
 */
@Data
@TableName("product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    /** 分类ID */
    private Long categoryId;

    /** 商品名称 */
    private String name;

    /** 主图 */
    private String mainImage;

    /** 商品图片列表 */
    private String images;

    /** 商品描述 */
    private String description;

    /** 价格 */
    private BigDecimal price;

    /** 原价 */
    private BigDecimal originalPrice;

    /** 销量 */
    private Integer salesCount;

    /** 库存 */
    private Integer stock;

    /** 状态: 0-下架 1-上架 */
    private Integer status;

    /** 创建时间 */
    private LocalDateTime createTime;

    /** 更新时间 */
    private LocalDateTime updateTime;

    /** 逻辑删除 */
    @TableLogic
    private Integer deleted;
}
