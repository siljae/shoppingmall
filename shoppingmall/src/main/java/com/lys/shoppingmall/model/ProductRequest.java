package com.lys.shoppingmall.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductRequest {
    private String name;
    private int price;
    private int stock;
}
