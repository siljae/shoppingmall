package com.lys.shoppingmall.database;

import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.model.order.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class OrderTest {
    @Autowired
    private OrderMapper orderMapper;

    @Test
    public void addOrderTest(){
        Order order = new Order();
        order.setProductId(1);
        order.setQuantity(1);
        order.setTotal(1000);
        order.setOrderDate(LocalDateTime.now());

        System.out.println("order : " + order.toString());
        orderMapper.insertOrder(order);

        Order addedOrder = orderMapper.getOrderById(1);
        if(addedOrder != null)
            System.out.println("addedOrder: " + addedOrder.toString());
        else
            System.out.println("주문 조회 결과가 없습니다");
    }
}
