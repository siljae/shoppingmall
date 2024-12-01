package com.lys.shoppingmall.model.response;

import com.lys.shoppingmall.model.order.Order;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderResponse {
    private int id;
    private int productId;
    private LocalDateTime orderDate;

    public static OrderResponse valueOf(Order order) {
        OrderResponse orderResponse = new OrderResponse();
        orderResponse.id = order.getId();
        orderResponse.productId = order.getProductId();
        orderResponse.orderDate = order.getOrderDate();
        return orderResponse;
    }
}
