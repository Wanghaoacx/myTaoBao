package com.mtaobao.product.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mtaobao.api.common.exception.BusinessException;
import com.mtaobao.product.entity.Product;
import com.mtaobao.product.mapper.ProductMapper;
import com.mtaobao.product.service.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * ProductService 单元测试
 */
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product();
        testProduct.setId(1001L);
        testProduct.setName("iPhone 15");
        testProduct.setPrice(new BigDecimal("5999.00"));
        testProduct.setStock(100);
        testProduct.setSalesCount(0);
        testProduct.setStatus(1);
        testProduct.setDeleted(0);
    }

    /**
     * 测试分页查询商品
     */
    @Test
    void testGetPage() {
        // Given
        Page<Product> page = new Page<>(1, 10);
        List<Product> products = Arrays.asList(testProduct);
        Page<Product> resultPage = new Page<>(1, 10);
        resultPage.setRecords(products);
        resultPage.setTotal(1);

        when(productMapper.selectPage(any(Page.class), any(LambdaQueryWrapper.class)))
                .thenReturn(resultPage);

        // When
        IPage<Product> result = productService.getPage(1, 10, "iPhone", null);

        // Then
        assertThat(result.getRecords()).hasSize(1);
        assertThat(result.getRecords().get(0).getName()).isEqualTo("iPhone 15");
    }

    /**
     * 测试根据ID查询商品 - 成功
     */
    @Test
    void testGetById_Success() {
        // Given
        when(productMapper.selectById(1001L)).thenReturn(testProduct);

        // When
        Product result = productService.getById(1001L);

        // Then
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("iPhone 15");
    }

    /**
     * 测试根据ID查询商品 - 商品不存在
     */
    @Test
    void testGetById_NotFound() {
        // Given
        when(productMapper.selectById(9999L)).thenReturn(null);

        // When & Then
        assertThatThrownBy(() -> productService.getById(9999L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("商品不存在或已下架");
    }

    /**
     * 测试根据ID查询商品 - 商品已下架
     */
    @Test
    void testGetById_StatusDisabled() {
        // Given
        testProduct.setStatus(0);
        when(productMapper.selectById(1001L)).thenReturn(testProduct);

        // When & Then
        assertThatThrownBy(() -> productService.getById(1001L))
                .isInstanceOf(BusinessException.class)
                .hasMessage("商品不存在或已下架");
    }

    /**
     * 测试获取商品列表
     */
    @Test
    void testGetList() {
        // Given
        List<Product> products = Arrays.asList(testProduct);
        when(productMapper.selectList(any(LambdaQueryWrapper.class))).thenReturn(products);

        // When
        List<Product> result = productService.getList(null, 10);

        // Then
        assertThat(result).hasSize(1);
    }

    /**
     * 测试新增商品
     */
    @Test
    void testAddProduct() {
        // Given
        Product newProduct = new Product();
        newProduct.setName("MacBook Pro");
        newProduct.setPrice(new BigDecimal("12999.00"));
        when(productMapper.insert(any(Product.class))).thenReturn(1);

        // When
        productService.addProduct(newProduct);

        // Then
        verify(productMapper).insert(any(Product.class));
    }

    /**
     * 测试扣减库存 - 成功
     */
    @Test
    void testDeductStock_Success() {
        // Given
        testProduct.setStock(100);
        when(productMapper.selectById(1001L)).thenReturn(testProduct);
        when(productMapper.updateById(any(Product.class))).thenReturn(1);

        // When
        boolean result = productService.deductStock(1001L, 10);

        // Then
        assertThat(result).isTrue();
    }

    /**
     * 测试扣减库存 - 库存不足
     */
    @Test
    void testDeductStock_InsufficientStock() {
        // Given
        testProduct.setStock(5);
        when(productMapper.selectById(1001L)).thenReturn(testProduct);

        // When & Then
        assertThatThrownBy(() -> productService.deductStock(1001L, 10))
                .isInstanceOf(BusinessException.class)
                .hasMessage("库存不足");
    }

    /**
     * 测试更新商品状态
     */
    @Test
    void testUpdateStatus() {
        // Given
        when(productMapper.updateById(any(Product.class))).thenReturn(1);

        // When
        productService.updateStatus(1001L, 0);

        // Then
        verify(productMapper).updateById(any(Product.class));
    }
}
