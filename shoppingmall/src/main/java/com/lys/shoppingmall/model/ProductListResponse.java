package com.lys.shoppingmall.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductListResponse {
    private List<ProductResponse> products;

    public ProductListResponse(List<ProductResponse> products){
        this.products = products;
    }
}
