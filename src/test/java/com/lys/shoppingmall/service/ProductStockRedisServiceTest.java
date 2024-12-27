package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductStockRedisServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RedisTemplate<String, Product> redisTemplate;

    @Mock
    private RedissonClient redissonClient;

    @Mock
    private RLock lock;

    @InjectMocks
    private ProductStockRedisService productStockRedisService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking ValueOperations
        ValueOperations<String, Product> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations); // ValueOperations가 null이 아니도록 설정
    }

    @Test
    @DisplayName("제품 없음 예외 발생")
    public void reduceStock_productNotFound() throws InterruptedException {
        // Given
        int productId = 0;
        int quantity = 1;
        String productStockKey = "productStock:" + productId;

        when(redisTemplate.opsForValue().get(productStockKey)).thenReturn(null);
        when(productMapper.getProductStockById(productId)).thenReturn(null);

        RLock lock = Mockito.mock(RLock.class);
        when(redissonClient.getLock("lock:" + productStockKey)).thenReturn(lock);
        when(lock.tryLock(2, 3, TimeUnit.SECONDS)).thenReturn(true);

        // When
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productStockRedisService.reduceStock(productId, quantity);
        });

        // Then
        assertEquals("Product with ID 0 is not found.", exception.getMessage());
    }

    @Test
    @DisplayName("재고 부족 예외 발생")
    public void reduceStock_outOfStock() {
        // Given
        int productId = 1;
        int quantity = 10;

        Product product = new Product();
        product.setId(productId);
        product.setStock(5);

        when(productMapper.getProductByIdForUpdate(productId)).thenReturn(product);

        // When & Then
        OutOfStockException exception = assertThrows(OutOfStockException.class, () -> {
            productStockRedisService.reduceStock(productId, quantity);
        });

        assertEquals("Product with ID 1 has insufficient stock.", exception.getMessage());
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
        when(redisTemplate.opsForValue().get("product:" + productId)).thenReturn(null); // Redis 에서 제품이 없음, (Redis 추가 12-22)
        when(productMapper.getProductByIdForUpdate(productId)).thenReturn(product);
        doNothing().when(productMapper).updateProductStock(product.getId(), product.getStock());

        // When
        productStockRedisService.reduceStock(productId, quantity);

        // Then
        assertEquals(4, product.getStock()); // 재고가 4로 줄어야 함
        verify(productMapper).updateProductStock(product.getId(), product.getStock());
    }
}
