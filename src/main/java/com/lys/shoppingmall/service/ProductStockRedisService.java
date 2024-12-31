package com.lys.shoppingmall.service;

import com.lys.shoppingmall.exception.LockNotAcquireException;
import com.lys.shoppingmall.exception.OutOfStockException;
import com.lys.shoppingmall.exception.ProductNotFoundException;
import com.lys.shoppingmall.mapper.OrderMapper;
import com.lys.shoppingmall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RAtomicLong;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ProductStockRedisService {
    private final RedissonClient redissonClient;
    private final ProductMapper productMapper;
    private final OrderMapper orderMapper;

    private static final int LOCK_WAIT_TIMEOUT = 10;
    private static final int LOCK_ACQUIRE_TIME = 5;


    private String getProductKey(int productId) {
        return "productSale:" + productId;
    }

    public void reduceStock(int productId, int quantity) {
        // 최대 재고 조회
        Integer maxStock = productMapper.getProductStockById(productId);
        if (maxStock == null) {
            throw new ProductNotFoundException(productId);
        }

        // 판매 수량
        Long currentSaleCount = fetchProductSaleOrInitialize(productId);
        if ((currentSaleCount + quantity) > maxStock) {
            throw new OutOfStockException(productId);
        }

        RAtomicLong rProductSale = redissonClient.getAtomicLong(getProductKey(productId));
        long currentSale = rProductSale.addAndGet(quantity);

        // 검증
        if (currentSale > maxStock) {
            rProductSale.addAndGet(-quantity);
            throw new OutOfStockException(productId);
        }
    }

    private Long fetchProductSaleOrInitialize(int productId) {
        String productSaleKey = getProductKey(productId);
        boolean isExists = redissonClient.getAtomicLong(productSaleKey).isExists();
        if (!isExists) {
            updateRedisProductSaleCount(productId);
        }
        RAtomicLong rSaleCount = redissonClient.getAtomicLong(productSaleKey);
        return rSaleCount.get();
    }

    public void updateRedisProductSaleCount(int productId) {
        String productSaleKey = getProductKey(productId);
        RLock lock = redissonClient.getLock("lock:" + productSaleKey);
        try {
            if (!lock.tryLock(LOCK_WAIT_TIMEOUT, LOCK_ACQUIRE_TIME, TimeUnit.SECONDS)) {
                throw new LockNotAcquireException(productId);
            }
            boolean isExists = redissonClient.getAtomicLong(productSaleKey).isExists();
            if (!isExists) {
                long saleCount = orderMapper.getOrderCountByProductId(productId);
                RAtomicLong rSaleCount = redissonClient.getAtomicLong(productSaleKey);
                rSaleCount.set(saleCount);
            }
        } catch (InterruptedException e) {
            throw new LockNotAcquireException(productId, e);
        } finally {
            lock.unlock();
        }
    }


}
