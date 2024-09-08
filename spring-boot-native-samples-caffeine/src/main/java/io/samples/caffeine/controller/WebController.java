package io.samples.caffeine.controller;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.samples.caffeine.entity.User;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/caffeine")
public class WebController {

    @Resource(name = "customCacheManager")
    CacheManager cacheManager;

    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = "Percy";
        }

        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/user")
    @Cacheable(cacheManager = "customCacheManager", value = "customCache", key = "#username")
    public User user(@RequestParam(name = "username") String username) {
        User user = new User();
        user.setName(username);
        log.info("===================web-controller-user: {}", username);
        return user;
    }

    @GetMapping("/show")
    public String show() {
        Collection<String> names = cacheManager.getCacheNames();
        log.info("==============names: {}", names);
        return names.toString();
    }


}
