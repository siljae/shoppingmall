package com.lys.shoppingmall.model.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class Order {
    private int id; //주문 번호
    private int productId;     //상품 번호
    private int quantity;
    private double total;
    private LocalDateTime orderDate;
}
