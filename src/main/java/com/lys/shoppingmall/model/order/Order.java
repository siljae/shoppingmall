package com.lys.shoppingmall.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Order {
    private int id;
    private int productId;
    private LocalDateTime orderDate;

    public static Order createOrder(int productId) {
        Order order = new Order();
        order.setProductId(productId);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    public static Order createOrderById(int id, int productId) {
        Order order = new Order();
        order.setId(id);
        order.setProductId(productId);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }
}
