package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.request.OrderRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductService productService;

    public OrderService(OrderMapper orderMapper, ProductService productService) {
        this.orderMapper = orderMapper;
        this.productService = productService;
    }

    @Transactional
    public Order addOrder(OrderRequest request){
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setOrderDate(LocalDateTime.now());

        orderMapper.insertOrder(order);

        if(order.getId() == 0){
            throw new OrderNotFoundException("Order with ID " + 0 + " not found.");
        }

        productService.reduceStock(order.getProductId(), 1);

        return order;
    }

}
