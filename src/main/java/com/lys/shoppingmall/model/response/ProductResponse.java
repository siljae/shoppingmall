package com.lys.shoppingmall.model.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductResponse {
    private int id;
    private String name;
    private int price;
    private int maxStock;

    public static ProductResponse valueOf(int id, String name, int price, int maxStock) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(id);
        productResponse.setName(name);
        productResponse.setPrice(price);
        productResponse.setMaxStock(maxStock);
        return productResponse;
    }
}
