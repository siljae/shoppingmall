package com.lys.shoppingmall.service;

import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;

public class OrderServiceTest {
    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("주문 기록")
    public void makeOrder() {
        // Given
        int productId = 1;

        doAnswer(invocation -> {
            Order savedOrder = invocation.getArgument(0);
            savedOrder.setId(1);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When
        Order order = orderService.makeOrder(productId);

        // Then
        assertEquals(1, order.getId());
    }
}
