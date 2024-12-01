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
    @DisplayName("상품 구매 성공")
    public void purchaseProduct_succeed(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(1);

        // When
        doNothing().when(orderService).purchaseOrder(request);
        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        // Then
        assertEquals(200, response.getStatusCode().value());
        assertEquals("구매가 완료되었습니다.", response.getBody());
    }
}