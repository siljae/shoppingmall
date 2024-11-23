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
}
