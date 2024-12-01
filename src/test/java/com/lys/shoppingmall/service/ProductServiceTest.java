package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
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
    @DisplayName("상품 구매 후 재고 차감 실패 - 모델의 값이 null일 경우")
    public void reduceStock_ProductNotFound(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(1);

        when(productMapper.getProductById(request.getProductId())).thenReturn(null);

        // When & Then
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.reduceStock(request);
        });
        assertEquals("Product with ID 1 is not found.", exception.getMessage());
    }

    @Test
    @DisplayName("재고 차감 실패 - 재고가 구매 수량보다 작을 경우")
    public void reduceStock_OutOfStock(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(5);

        Product product = new Product();
        product.setId(1);
        product.setStock(3);

        when(productMapper.getProductById(1)).thenReturn(product);

        // When & Then
        OutOfStockException exception = assertThrows(OutOfStockException.class, () ->{
            productService.reduceStock(request);
            });
        assertEquals("Product with ID 1 has insufficient stock.", exception.getMessage());
    }

    @Test
    @DisplayName("재고 차감 성공")
    public void reduceStock_success(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(5);

        Product product = new Product();
        product.setId(1);
        product.setStock(10);

        when(productMapper.getProductById(1)).thenReturn(product);
        doNothing().when(orderService).addOrder(product.getId());

        // When
        productService.reduceStock(request);

        // Then
        assertEquals(5, product.getStock());
        verify(productMapper, times(1)).updateProductStock(product);
        verify(orderService).addOrder(product.getId());
    }
}
