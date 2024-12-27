package com.lys.shoppingmall.service;

import com.lys.shoppingmall.model.order.Order;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

public class PurchaseServiceTest {
    @Mock
    ProductStockRedisService productStockRedisService;

    @Mock
    OrderService orderService;

    @InjectMocks
    PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구매 성공")
    public void purchaseTest() {
        // Given
        int productId = 1;

        Order order = Order.createOrderById(1, 1);

        doNothing().when(productStockRedisService).reduceStock(productId, 1);
        when(orderService.makeOrder(productId)).thenReturn(order);

        // When
        Order purchasedOrder = purchaseService.purchase(productId);

        // Then
        assertEquals(1, purchasedOrder.getId());
    }
}
