package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import com.lys.shoppingmall.model.product.Product;
import com.lys.shoppingmall.model.request.ProductRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductMapper productMapper;

    @Mock
    private RedisTemplate<String, Product> redisTemplate;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mocking ValueOperations
        ValueOperations<String, Product> valueOperations = mock(ValueOperations.class);
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    @DisplayName("제품 id로 제품 정보 가져 오기")
    public void getByIdTest() {
        // Given
        int id = 1;

        Product product = new Product();
        product.setId(1);

        when(productMapper.getProductById(id)).thenReturn(product);

        // When
        Product resultProduct = productService.getById(id);

        // Then
        assertEquals(resultProduct.getId(), product.getId());
    }

    @Test
    @DisplayName("제품 추가")
    public void addProductTest() {
        //Given
        ProductRequest request = new ProductRequest();
        request.setName("고구마");
        request.setPrice(100);
        request.setStock(10);

        doAnswer(invacation -> {
            Product addedProduct = invacation.getArgument(0);
            addedProduct.setId(1);
            return null;
        }).when(productMapper).insertProduct(any(Product.class));

        // When
        Product product = productService.addProduct(request);

        // Then
        assertEquals(1, product.getId());
    }

    @Test
    @DisplayName("상품 수정 실패")
    public void updateProductTest_failed() {
        // Given
        int id = 0;
        Product product = Product.createProduct("고구마", 100, 10);
        Product DBProduct = Product.createProduct("고구마", 500, 50);
        DBProduct.setId(id);

        when(productMapper.getProductById(id)).thenReturn(null);

        // When
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.updateProduct(id, product);
        });

        // Then
        assertEquals("Product with ID 0 is not found.", exception.getMessage());
    }

    @Test
    @DisplayName("상품 수정 성공")
    public void updateProductTest_success() {
        // Given
        int id = 1;
        Product product = Product.createProduct("고구마", 100, 10);
        Product DBProduct = Product.createProduct("고구마", 500, 50);
        DBProduct.setId(id);

        when(productMapper.getProductById(id)).thenReturn(DBProduct);

        // When
        Product updatedProduct = productService.updateProduct(id, product);

        // Then
        assertEquals(DBProduct.getId(), updatedProduct.getId());
        verify(productMapper).updateProduct(updatedProduct);
    }

    @Test
    @DisplayName("상품 삭제")
    public void deleteProduct() {
        // Given
        int id = 1;

        doNothing().when(productMapper).deleteProduct(id);

        // When
        productService.deleteProduct(id);

        // Then
        verify(productMapper).deleteProduct(id);
    }

    @Test
    @DisplayName("제품 없음 예외 발생")
    public void reduceStock_productNotFound() {
        // Given
        int productId = 0;
        int quantity = 1;

        when(productMapper.getProductByIdForUpdate(productId)).thenReturn(null);

        // When
        ProductNotFoundException exception = assertThrows(ProductNotFoundException.class, () -> {
            productService.reduceStock(productId, quantity);
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
            productService.reduceStock(productId, quantity);
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
        productService.reduceStock(productId, quantity);

        // Then
        assertEquals(4, product.getStock()); // 재고가 4로 줄어야 함
        verify(productMapper).updateProductStock(product.getId(), product.getStock());
    }


}
