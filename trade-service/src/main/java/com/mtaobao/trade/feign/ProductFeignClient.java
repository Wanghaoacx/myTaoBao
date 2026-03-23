package com.mtaobao.trade.feign;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.trade.dto.ProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "product-service", url = "${feign.product-service.url:http://localhost:8202}")
public interface ProductFeignClient {

    @GetMapping("/product/{id}")
    Result<ProductDTO> getProductById(@PathVariable("id") Long id);

    @GetMapping("/product/list")
    Result<List<ProductDTO>> getProductList(
            @RequestParam(value = "categoryId", required = false) Long categoryId,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit);

    @GetMapping("/product/page")
    Result<?> getProductPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "categoryId", required = false) Long categoryId);

    @GetMapping("/category/list")
    Result<?> getCategoryList();
}
