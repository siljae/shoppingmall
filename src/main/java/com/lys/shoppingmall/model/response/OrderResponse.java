package com.lys.shoppingmall.model.response;

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
    private int quantity;
    private double total;
    private LocalDateTime orderDate;

    public OrderResponse(int id, int productId, int quantity, double total, LocalDateTime orderDate){
        this.id = id;
        this.productId = productId;
        this.quantity = quantity;
        this.total = total;
        this.orderDate = orderDate;
    }
}
