package io.samples.redis.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.redis.config.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@RestController
@RequestMapping("/redis")
public class RedisController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
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
