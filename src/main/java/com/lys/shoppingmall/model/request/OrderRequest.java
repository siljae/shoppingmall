package com.lys.shoppingmall.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class OrderRequest {
    private int id;
    private int productId;
    private int quantity;
    private double total;
    private LocalDateTime orderDate;
}
