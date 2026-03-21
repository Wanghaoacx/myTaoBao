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
    Result<ProductDTO> getProductById(@PathVariable Long id);

    @GetMapping("/product/list")
    Result<List<ProductDTO>> getProductList(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(defaultValue = "20") Integer limit);

    @GetMapping("/product/page")
    Result<?> getProductPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Long categoryId);

    @GetMapping("/category/list")
    Result<?> getCategoryList();
}
