package com.lys.shoppingmall.model.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Product {
    private int id;      //상품 고유 번호
    private String name; //상품 명
    private int price;   //상품 가격
    private int stock;   //상품 재고

    public static Product createProduct(String name, int price, int stock) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setStock(stock);
        return product;
    }
}
