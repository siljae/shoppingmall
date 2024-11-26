package com.lys.shoppingmall.controller;

import com.lys.shoppingmall.model.order.Order;
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
import static org.mockito.Mockito.when;

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
    @DisplayName("상품 구매 실패")
    public void purchaseProduct_failed(){
        OrderRequest request = new OrderRequest();
        Order order = new Order();
        order.setId(0);

        when(orderService.addOrder(request)).thenReturn(order);

        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        assertEquals(400, response.getStatusCode().value());
        assertEquals("재고가 부족합니다.", response.getBody());
    }

    @Test
    @DisplayName("상품 구매 성공")
    public void purchaseProduct_succeed(){
        OrderRequest request = new OrderRequest();
        Order order = new Order();
        order.setId(1);

        when(orderService.addOrder(request)).thenReturn(order);

        ResponseEntity<String> response = apiProductController.purchaseProduct(request);

        assertEquals(200, response.getStatusCode().value());
        assertEquals("구매가 완료되었습니다.", response.getBody());
    }

}
