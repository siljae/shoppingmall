package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

public class OrderServiceTest {
    @Mock
    private OrderMapper orderMapper;

    @Mock
    private ProductService productService;

    @InjectMocks
    private  OrderService orderService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("구매 기록 실패")
    public void addOrder_failed() {
        // Given
        int productId = 1;

        doAnswer(invocation -> {
            Order arg = invocation.getArgument(0);
            arg.setId(0);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.addOrder(productId);
        });

        // Then
        assertEquals("Order with ID 0 not found.", exception.getMessage());
    }

    @Test
    @DisplayName("구매 기록 성공")
    public void addOrder_succeed() {
        // Given
        int productId = 1;

        doAnswer(invocation -> {
            Order arg = invocation.getArgument(0);
            arg.setId(1);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When
        orderService.addOrder(productId);

        // Then
        verify(orderMapper).insertOrder(any(Order.class));
    }
}
