package com.mtaobao.product.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mtaobao.api.common.result.Result;
import com.mtaobao.product.entity.Product;
import com.mtaobao.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/page")
    public Result<IPage<Product>> getPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId) {
        return Result.success(productService.getPage(pageNum, pageSize, keyword, categoryId));
    }

    @GetMapping("/{id}")
    public Result<Product> getById(@PathVariable Long id) {
        return Result.success(productService.getById(id));
    }

    @GetMapping("/list")
    public Result<List<Product>> getList(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "20") Integer limit) {
        return Result.success(productService.getList(categoryId, limit));
    }

    @PostMapping
    public Result<Void> addProduct(@RequestBody Product product) {
        productService.addProduct(product);
        return Result.success();
    }

    @PutMapping
    public Result<Void> updateProduct(@RequestBody Product product) {
        productService.updateProduct(product);
        return Result.success();
    }

    @PutMapping("/status/{id}")
    public Result<Void> updateStatus(@PathVariable Long id, @RequestParam Integer status) {
        productService.updateStatus(id, status);
        return Result.success();
    }
}
