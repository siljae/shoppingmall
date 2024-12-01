package com.lys.shoppingmall.service;

import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;

    public Order makeOrder(int productId) {
        Order order = Order.createOrder(productId);
        orderMapper.insertOrder(order);
        return order;
    }

}
