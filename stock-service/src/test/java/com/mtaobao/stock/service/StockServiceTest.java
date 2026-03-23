package com.mtaobao.stock.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.stock.entity.Stock;
import com.mtaobao.stock.mapper.StockMapper;
import com.mtaobao.stock.service.impl.StockServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * StockService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private StockMapper stockMapper;

    @InjectMocks
    private StockServiceImpl stockService;

    private Stock testStock;

    @BeforeEach
    void setUp() {
        testStock = new Stock();
        testStock.setId(1L);
        testStock.setProductId(1001L);
        testStock.setTotalStock(100);
        testStock.setAvailableStock(100);
        testStock.setLockedStock(0);
    }

    /**
     * 测试根据商品ID查询库存
     */
    @Test
    void testGetByProductId() {
        // Given
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testStock);

        // When
        Stock result = stockService.getByProductId(1001L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getProductId()).isEqualTo(1001L);
        assertThat(result.getAvailableStock()).isEqualTo(100);
    }

    /**
     * 测试扣减库存 - 成功
     */
    @Test
    void testDeductStock_Success() {
        // Given
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testStock);
        when(stockMapper.updateById(any(Stock.class))).thenReturn(1);

        // When
        boolean result = stockService.deductStock(1001L, 10);

        // Then
        assertThat(result).isTrue();
        verify(stockMapper).updateById(any(Stock.class));
    }

    /**
     * 测试扣减库存 - 库存记录不存在
     */
    @Test
    void testDeductStock_NotFound() {
        // Given
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> stockService.deductStock(9999L, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage("库存记录不存在");
    }

    /**
     * 测试扣减库存 - 库存不足
     */
    @Test
    void testDeductStock_InsufficientStock() {
        // Given
        testStock.setAvailableStock(5);
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testStock);

        // When & Then
        assertThatThrownBy(() -> stockService.deductStock(1001L, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage("库存不足");
    }

    /**
     * 测试回滚库存 - 成功
     */
    @Test
    void testRollbackStock_Success() {
        // Given
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(testStock);
        when(stockMapper.updateById(any(Stock.class))).thenReturn(1);

        // When
        boolean result = stockService.rollbackStock(1001L, 10);

        // Then
        assertThat(result).isTrue();
    }

    /**
     * 测试回滚库存 - 库存记录不存在
     */
    @Test
    void testRollbackStock_NotFound() {
        // Given
        when(stockMapper.selectOne(any(LambdaQueryWrapper.class))).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> stockService.rollbackStock(9999L, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage("库存记录不存在");
    }
}
