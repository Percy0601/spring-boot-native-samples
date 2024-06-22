package io.samples.redis.controller;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.redisson.api.RedissonClient;
import org.redisson.client.RedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    RedisTemplate redisTemplate;

    @GetMapping("/key/put")
    public String putKey(String user) {
        redisTemplate.opsForValue().set(user, UUID.randomUUID(), 1, TimeUnit.DAYS);
        return "OK";
    }

    @GetMapping("/key/get")
    public String getKey(String user) {
        String result = redisTemplate.opsForValue().get(user).toString();
        return result;
    }

}
