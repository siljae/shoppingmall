package com.lys.shoppingmall.redis;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RedisPingTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void testPing() {
        // Given & When
        String response = redisTemplate.execute((RedisCallback<String>) RedisConnection::ping);

        // Then
        assertThat(response).isEqualTo("PONG");
    }

    @Test
    public void testWriteAndRead() {
        // Given
        String key = "squat";
        String value = "fifteen";

        // When
        redisTemplate.opsForValue().set(key, value);
        String receiveValue = (String) redisTemplate.opsForValue().get(key);

        // Then
        assertThat(receiveValue).isEqualTo(value);
    }
}
