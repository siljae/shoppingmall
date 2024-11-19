package com.lys.shoppingmall.mapper;

import com.lys.shoppingmall.model.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();

    @Insert("INSERT INTO products(name, price, stock) VALUES (#{name}, #{price}, #{stock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProduct(Product product);

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product getProductById(int id);

    @Update("UPDATE products SET name = #{name}, price = #{price}, stock = #{stock} WHERE id = #{id}")
    void updateProduct(Product product);

    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteProduct(int id);

}
