package com.lys.shoppingmall.service;

import com.lys.shoppingmall.model.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PurchaseService {
    private final ProductStockRedisService productStockRedisService;
    private final OrderService orderService;

    @Transactional
    public Order purchase(int productId) {
        productStockRedisService.reduceStock(productId, 1);
        return orderService.makeOrder(productId);
    }
}
