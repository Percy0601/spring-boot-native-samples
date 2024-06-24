package io.samples.redis.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.redis.config.RedisUtil;
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
    RedisUtil redisUtil;

    @GetMapping("/key/put")
    public String putKey(String user) {
        redisUtil.set(user, UUID.randomUUID(), 10000);
        return "OK";
    }

    @GetMapping("/key/get")
    public String getKey(String user) {
        String result = redisUtil.get(user).toString();
        return result;
    }

}
