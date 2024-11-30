package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.service.OrderService;
import com.lys.shoppingmall.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class ApiProductControllerTest {
    @Mock
    ProductService productService;

    @Mock
    OrderService orderService;

    @InjectMocks
    ApiProductController apiProductController;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("상품 구매 실패 - 제품 미발견")
    public void purchaseProduct_productNotFound() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(0);
        request.setQuantity(1);

        // When
        doThrow(new ProductNotFoundException(1)).when(productService).reduceStock(request);
        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("제품을 찾을 수 없습니다.", response.getBody());
    }

    @Test
    @DisplayName("상품 구매 실패 - 재고 부족")
    public void purchaseProduct_outOfStock() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(1);

        // When
        doThrow(new OutOfStockException(1)).when(productService).reduceStock(request);
        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        // Then
        assertEquals(400, response.getStatusCode().value());
        assertEquals("재고가 부족합니다.", response.getBody());
    }

    @Test
    @DisplayName("상품 구매 실패 - 주문 추가 실패")
    public void purchaseProduct_orderAdditionFailed() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);

        // When
        doThrow(new OrderNotFoundException(1)).when(orderService).addOrder(request.getProductId());
        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        // Then
        assertEquals(500, response.getStatusCode().value());
        assertEquals("주문 추가에 실패했습니다.", response.getBody());
    }

    @Test
    @DisplayName("상품 구매 성공")
    public void purchaseProduct_succeed(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(1);

        // When
        doNothing().when(productService).reduceStock(request);
        doNothing().when(orderService).addOrder(request.getProductId());
        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("구매가 완료되었습니다.", response.getBody());
    }
}