package com.mtaobao.stock.service;

import com.mtaobao.stock.entity.Stock;

public interface StockService {
    Stock getByProductId(Long productId);
    boolean deductStock(Long productId, Integer quantity);
    boolean rollbackStock(Long productId, Integer quantity);
}
