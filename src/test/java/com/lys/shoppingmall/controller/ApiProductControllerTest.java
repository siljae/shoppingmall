package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.model.response.OrderResponse;
import com.lys.shoppingmall.service.PurchaseService;
import com.lys.shoppingmall.service.PurchaseServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.doReturn;

public class ApiProductControllerTest {
    @Mock
    PurchaseService purchaseService;

    @InjectMocks
    ApiProductController apiProductController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("상품 구매 성공")
    public void purchaseProduct_succeed() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);

        Order order = Order.createOrder(1);
        order.setId(1);

        doReturn(order).when(purchaseService).purchase(request.getProductId());

        // When
        OrderResponse orderResponse = apiProductController.purchaseProduct(request);

        // Then
        assertNotNull(orderResponse);
        assertEquals(1, orderResponse.getId());
        assertEquals(request.getProductId(), orderResponse.getProductId());
    }
}