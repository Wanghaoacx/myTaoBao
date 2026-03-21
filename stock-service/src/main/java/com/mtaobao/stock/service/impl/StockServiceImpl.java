package com.mtaobao.stock.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.stock.entity.Stock;
import com.mtaobao.stock.mapper.StockMapper;
import com.mtaobao.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final StockMapper stockMapper;

    @Override
    public Stock getByProductId(Long productId) {
        LambdaQueryWrapper<Stock> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Stock::getProductId, productId);
        return stockMapper.selectOne(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deductStock(Long productId, Integer quantity) {
        Stock stock = getByProductId(productId);
        if (stock == null) {
            throw new BusinessException("库存记录不存在");
        }
        if (stock.getAvailableStock() < quantity) {
            throw new BusinessException("库存不足");
        }
        stock.setAvailableStock(stock.getAvailableStock() - quantity);
        stock.setTotalStock(stock.getTotalStock() - quantity);
        stock.setUpdateTime(LocalDateTime.now());
        stockMapper.updateById(stock);
        log.info("扣减库存: productId={}, quantity={}", productId, quantity);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean rollbackStock(Long productId, Integer quantity) {
        Stock stock = getByProductId(productId);
        if (stock == null) {
            throw new BusinessException("库存记录不存在");
        }
        stock.setAvailableStock(stock.getAvailableStock() + quantity);
        stock.setTotalStock(stock.getTotalStock() + quantity);
        stock.setUpdateTime(LocalDateTime.now());
        stockMapper.updateById(stock);
        log.info("回滚库存: productId={}, quantity={}", productId, quantity);
        return true;
    }
}
