package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OrderNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public void addOrder(int productId){
        Order order = new Order();
        order.setProductId(productId);
        order.setOrderDate(LocalDateTime.now());

        orderMapper.insertOrder(order);
        if(order.getId() == 0){
            throw new OrderNotFoundException(0);
        }
    }

}
