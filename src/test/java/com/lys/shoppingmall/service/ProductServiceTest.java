package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private OrderService orderService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getByIdTest(){
        int id = 17;
        Product product = productService.getById(id);
        if(product == null){
            throw new ProductNotFoundException(id);
        }
    }

    @Test
    @DisplayName("제품 없음 예외 발생")
    public void reduceStock_productNotFound() {
        // Given
        int productId = 1;
        int quantity = 1;

        // Mocking productMapper methods
        when(productMapper.getProductById(productId)).thenReturn(null); // 제품이 없음

        // When & Then
        assertThrows(ProductNotFoundException.class, () -> {
            productService.reduceStock(productId, quantity);
        });
        verify(productMapper, never()).updateProductStock(any(Product.class)); // 재고 감소가 이루어지지 않아야 함
    }

    @Test
    @DisplayName("재고 부족 예외 발생")
    public void reduceStock_outOfStock() {
        // Given
        int productId = 1;
        int quantity = 10; // 요청하는 수량
        Product product = new Product();
        product.setId(productId);
        product.setStock(5); // 현재 재고

        // Mocking productMapper methods
        when(productMapper.getProductById(productId)).thenReturn(product);

        // When & Then
        assertThrows(OutOfStockException.class, () -> {
            productService.reduceStock(productId, quantity);
        });
        verify(productMapper, never()).updateProductStock(any(Product.class)); // 재고 감소가 이루어지지 않아야 함
    }

    @Test
    @DisplayName("재고 감소 성공")
    public void reduceStock_success() {
        // Given
        int productId = 1;
        int quantity = 1;
        Product product = new Product();
        product.setId(productId);
        product.setStock(5); // 현재 재고

        // Mocking productMapper methods
        when(productMapper.getProductById(productId)).thenReturn(product);
        doNothing().when(productMapper).updateProductStock(product);

        // When
        productService.reduceStock(productId, quantity);

        // Then
        assertEquals(4, product.getStock()); // 재고가 4로 줄어야 함
        verify(productMapper).updateProductStock(product);
    }
}
