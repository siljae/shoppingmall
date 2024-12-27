package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductStockRedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private final RedissonClient redissonClient;
    private final ProductMapper productMapper;

    public void reduceStock(int productId, int quantity) {
        String productStockKey = "productStock:" + productId;
        String productSalesKey = "productSales:" + productId;
        RLock lock = redissonClient.getLock("lock:" + productStockKey);

        try {
            if (!lock.tryLock(2, 3, TimeUnit.SECONDS)) {
                throw new RuntimeException("Could not acquire lock");
            }
            Integer productStock = fetchProductStockOrInitialize(productId, productStockKey);
            if (productStock == null) {
                throw new ProductNotFoundException(productId);
            }

            Integer productSales = fetchProductSalesOrInitialize(productSalesKey);
            int currentStock = productStock - productSales;

            if (currentStock < quantity) {
                redisTemplate.opsForValue().decrement(productSalesKey, productSales - productStock);
                throw new OutOfStockException(productId);
            }
            redisTemplate.opsForValue().increment(productSalesKey, quantity);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
    }

    public Integer fetchProductStockOrInitialize(int productId, String productStockKey) {
        Integer productStock = (Integer) redisTemplate.opsForValue().get(productStockKey);
        if (productStock == null) {
            productStock = productMapper.getProductStockById(productId);
            redisTemplate.opsForValue().set(productStockKey, productStock);
        }
        return productStock;
    }

    public Integer fetchProductSalesOrInitialize(String productSSales) {
        Integer productSales = (Integer) redisTemplate.opsForValue().get(productSSales);
        if (productSales == null) {
            productSales = 0;
            redisTemplate.opsForValue().set(productSSales, productSales);
        }
        return productSales;
    }


}
