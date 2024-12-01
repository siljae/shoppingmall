package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.request.OrderRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    @DisplayName("구매 기록 실패")
    public void purchaseOrder_failed() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(0);
        request.setQuantity(1);

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setOrderDate(LocalDateTime.now());

        doThrow(new OrderNotFoundException(request.getProductId())).when(productService).reduceStock(request.getProductId(), request.getQuantity());

        OrderNotFoundException exception = assertThrows(OrderNotFoundException.class, () -> {
            orderService.purchaseOrder(request);
        });

        // Then
        assertEquals("Order with ID 0 not found.", exception.getMessage());
    }

    @Test
    @DisplayName("구매 기록 성공")
    public void purchaseOrder_succeed() {
        // Given
        OrderRequest request = new OrderRequest();
        request.setProductId(1);
        request.setQuantity(1);

        doNothing().when(productService).reduceStock(request.getProductId(), request.getQuantity());

        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setOrderDate(LocalDateTime.now());

        doAnswer(invocation -> {
            Order inseredOrder = invocation.getArgument(0);
            inseredOrder.setId(1);
            return null;
        }).when(orderMapper).insertOrder(any(Order.class));

        // When
        orderService.purchaseOrder(request);

        // Then
        verify(orderMapper).insertOrder(any(Order.class));
    }
}
