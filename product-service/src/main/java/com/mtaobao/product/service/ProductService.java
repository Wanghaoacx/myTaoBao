package com.mtaobao.product.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mtaobao.product.entity.Product;

import java.util.List;

public interface ProductService {

    /**
     * 分页查询商品
     */
    IPage<Product> getPage(Integer pageNum, Integer pageSize, String keyword, Long categoryId);

    /**
     * 根据ID查询商品
     */
    Product getById(Long id);

    /**
     * 查询商品列表
     */
    List<Product> getList(Long categoryId, Integer limit);

    /**
     * 新增商品
     */
    void addProduct(Product product);

    /**
     * 更新商品
     */
    void updateProduct(Product product);

    /**
     * 上架/下架商品
     */
    void updateStatus(Long id, Integer status);

    /**
     * 扣减库存
     */
    boolean deductStock(Long productId, Integer quantity);
}
