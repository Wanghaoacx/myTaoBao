package com.mtaobao.product.service;

import com.mtaobao.product.entity.Category;

import java.util.List;

public interface CategoryService {

    /**
     * 获取所有分类
     */
    List<Category> getAll();

    /**
     * 根据父ID获取子分类
     */
    List<Category> getByParentId(Long parentId);

    /**
     * 根据ID获取分类
     */
    Category getById(Long id);
}
