package com.lys.shoppingmall.service;

import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import com.lys.shoppingmall.model.request.OrderRequest;
import com.lys.shoppingmall.model.response.OrderResponse;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private final OrderMapper orderMapper;

    public OrderService(OrderMapper orderMapper) {
        this.orderMapper = orderMapper;
    }

    public OrderResponse addOrder(OrderRequest request){
        Order order = new Order();
        order.setProductId(request.getProductId());
        order.setQuantity(request.getQuantity());
        order.setTotal(request.getTotal());
        order.setOrderDate(LocalDateTime.now());

        orderMapper.insertOrder(order);

        return new OrderResponse(order.getId(), order.getProductId(), order.getQuantity(), order.getTotal(), order.getOrderDate());
    }

}
