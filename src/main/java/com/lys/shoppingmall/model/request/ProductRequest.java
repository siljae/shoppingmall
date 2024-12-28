package com.lys.shoppingmall.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductRequest {
    private String name;
    private int price;
    private int maxStock;
}
