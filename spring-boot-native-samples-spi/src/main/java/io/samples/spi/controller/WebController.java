package io.samples.spi.controller;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.spi.service.Search;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/spi")
public class WebController {

    @GetMapping("/hello")
    public String hello(String name) {
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/show")
    public String spi() {
        ServiceLoader<Search> load = ServiceLoader.load(Search.class);
        Iterator<Search> iterator = load.iterator();
        while (iterator.hasNext()){
            Search next = iterator.next();
            next.searchDoc("helloWorld");
        }

        return "OK";
    }
}
