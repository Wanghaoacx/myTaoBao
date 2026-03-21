package com.mtaobao.product.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.product.entity.Category;
import com.mtaobao.product.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> getAll() {
        return Result.success(categoryService.getAll());
    }

    @GetMapping("/children")
    public Result<List<Category>> getByParentId(@RequestParam Long parentId) {
        return Result.success(categoryService.getByParentId(parentId));
    }

    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        return Result.success(categoryService.getById(id));
    }
}
