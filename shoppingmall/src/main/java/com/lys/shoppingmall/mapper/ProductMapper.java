package com.lys.shoppingmall.mapper;

import com.lys.shoppingmall.model.Product;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductMapper {
    List<Product> getAllProducts();
    void insertProduct(Product product);
    Product getProductById(int id);
    void updateProduct(Product product);
    void deleteProduct(int id);

}
