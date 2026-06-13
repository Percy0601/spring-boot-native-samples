package io.samples.meta.controller;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@RestController
@RequestMapping("/meta")
public class MetaController {
    private Logger log = LoggerFactory.getLogger(this.getClass());


    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = UUID.randomUUID().toString();
        }
        return "Hello: ".concat(name);
    }

}
