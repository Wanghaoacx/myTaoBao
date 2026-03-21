package com.mtaobao.stock.controller;

import com.mtaobao.api.common.result.Result;
import com.mtaobao.stock.entity.Stock;
import com.mtaobao.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping("/{productId}")
    public Result<Stock> getStock(@PathVariable Long productId) {
        return Result.success(stockService.getByProductId(productId));
    }

    @PostMapping("/deduct")
    public Result<Void> deductStock(@RequestParam Long productId, @RequestParam Integer quantity) {
        stockService.deductStock(productId, quantity);
        return Result.success();
    }

    @PostMapping("/rollback")
    public Result<Void> rollbackStock(@RequestParam Long productId, @RequestParam Integer quantity) {
        stockService.rollbackStock(productId, quantity);
        return Result.success();
    }
}
