package io.samples.spi.controller;

import java.util.Iterator;
import java.util.ServiceLoader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.spi.service.Search;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@RestController
@RequestMapping("/spi")
public class WebController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
