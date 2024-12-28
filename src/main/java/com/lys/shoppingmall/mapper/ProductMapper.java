package com.lys.shoppingmall.mapper;

import com.lys.shoppingmall.model.product.Product;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM products")
    List<Product> getAllProducts();

    @Insert("INSERT INTO products(name, price, max_stock) VALUES (#{name}, #{price}, #{maxStock})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProduct(Product product);

    @Select("SELECT * FROM products WHERE id = #{id}")
    Product getProductById(int id);

    @Update("UPDATE products SET name = #{name}, price = #{price}, max_stock = #{maxStock} WHERE id = #{id}")
    void updateProduct(Product product);

    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteProduct(int id);

    @Update("UPDATE products SET max_stock = #{maxStock} WHERE id = #{id}")
    void updateProductStock(@Param("id") int id, @Param("maxStock") int stock);

    @Select("SELECT * FROM products WHERE id = #{id} FOR UPDATE")
    Product getProductByIdForUpdate(int id);

    @Select("SELECT max_stock FROM products WHERE id = #{id}")
    Integer getProductStockById(int id);
}
