package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    public void addOrder_failed(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);

        doAnswer(invocation -> {
            Order arg = invocation.getArgument(0);
            arg.setId(0);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When & Then
        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.addOrder(request);
        });
        assertEquals("Order with ID 0 not found.", exception.getMessage());
    }
    
    @Test
    public void addOrder_succeed(){
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);

        Order order = new Order();
        order.setId(1);
        order.setProductId(request.getProductId());
        order.setOrderDate(LocalDateTime.now());

        doAnswer(invocation -> {
            Order arg = invocation.getArgument(0);
            arg.setId(1);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When
        Order addedOrder = orderService.addOrder(request);

        // Then
        assertNotNull(addedOrder);
        assertEquals(1, addedOrder.getId());
        assertEquals(1, addedOrder.getProductId());
        verify(productService, times(1)).reduceStock(1, 1);
    }
}
