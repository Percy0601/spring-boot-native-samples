package io.samples.thrift.api.actuator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@RestController
@RequestMapping("/web")
public class WebController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @GetMapping("/hello")
    public String hello() {
        log.info("hello world!");
        return "Hello World!";
    }


}
