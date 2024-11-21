package com.lys.shoppingmall.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductListResponse {
    private List<ProductResponse> products;

    public ProductListResponse(List<ProductResponse> products){
        this.products = products;
    }
}
