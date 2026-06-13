package io.samples.thrift.api.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.thrift.api.util.FreemarkerUtil;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@RestController
@RequestMapping("/web")
public class WebController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
