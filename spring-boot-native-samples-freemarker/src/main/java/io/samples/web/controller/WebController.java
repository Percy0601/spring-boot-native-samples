package io.samples.web.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.web.util.FreemarkerUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = "Percy";
        }
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/format")
    public String format() {
        FreemarkerUtil instance = FreemarkerUtil.getInstance();
        Map<String, Object> params = new HashMap<>();
        params.put("welcome", UUID.randomUUID());

        String result = instance.format("some.ftl", params);
        return result;
    }
}
