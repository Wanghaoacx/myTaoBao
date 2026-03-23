package com.mtaobao.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.product.entity.Category;
import com.mtaobao.product.mapper.CategoryMapper;
import com.mtaobao.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, 1)
                .orderByAsc(Category::getLevel, Category::getSort);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public List<Category> getByParentId(Long parentId) {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getParentId, parentId)
                .eq(Category::getStatus, 1)
                .orderByAsc(Category::getSort);
        return categoryMapper.selectList(wrapper);
    }

    @Override
    public Category getById(Long id) {
        return categoryMapper.selectById(id);
    }

    @Override
    public void addCategory(Category category) {
        categoryMapper.insert(category);
    }
}
