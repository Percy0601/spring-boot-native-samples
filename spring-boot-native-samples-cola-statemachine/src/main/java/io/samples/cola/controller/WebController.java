package io.samples.cola.controller;

import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    private Condition<Order> checkCondition() {
        return order -> {
            System.out.println("Check condition : " + order);
            return true;
        };
    }
}
