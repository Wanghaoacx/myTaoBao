package com.mtaobao.stock.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@TableName("stock")
public class Stock implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    private Long productId;
    private Long skuId;
    private Integer totalStock;
    private Integer availableStock;
    private Integer lockedStock;
    private Integer warningStock;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
