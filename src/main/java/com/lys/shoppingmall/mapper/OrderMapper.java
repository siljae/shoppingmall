package com.lys.shoppingmall.mapper;

import com.lys.shoppingmall.model.order.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders (product_id, order_date) VALUES (#{productId}, #{orderDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertOrder(Order order);

    @Select("SELECT * FROM orders WHERE id = #{id}")
    Order getOrderById(int id);
}
