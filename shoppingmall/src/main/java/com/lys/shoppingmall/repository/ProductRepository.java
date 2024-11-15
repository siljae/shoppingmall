package com.lys.shoppingmall.repository;

import com.lys.shoppingmall.model.Product;

import java.util.List;

public interface ProductRepository {
    List<Product> findAll();
    Product findById(int id);
    void save(Product product);
    void update(Product product);
    void delete(int id);
}
