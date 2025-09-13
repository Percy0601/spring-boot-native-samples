package io.samples.thrift.api.actuator.controller;

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
    public String hello() {
        log.info("hello world!");
        return "Hello World!";
    }


}
