package com.lys.shoppingmall.redis;

import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void isExists_True() {
        // Given
        String key = "productSale:1";

        // When
        boolean isExists = redissonClient.getAtomicLong(key).isExists();

        // Then
        System.out.println(isExists);
        assertThat(isExists).isTrue();
    }

    @Test
    public void isExists_False() {
        // Given
        String key = "productSale:unknown";

        // When
        boolean isExists = redissonClient.getAtomicLong(key).isExists();

        // Then
        System.out.println(isExists);
        assertThat(isExists).isFalse();
    }
}
