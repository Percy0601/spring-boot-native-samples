package io.samples.thrift.provider;

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
@RequestMapping("/provider")
public class ProviderController {

    @GetMapping("/hello")
    public String hello(String name) {
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

}
