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
    private LocalDateTime orderDate;

    public OrderResponse(int id, int productId, LocalDateTime orderDate){
        this.id = id;
        this.productId = productId;
        this.orderDate = orderDate;
    }
}
