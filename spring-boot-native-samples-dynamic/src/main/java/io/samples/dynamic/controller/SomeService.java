package io.samples.dynamic.controller;

import org.springframework.stereotype.Service;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
@Service
public class SomeService {

    public String hello(String name) {
        //log.info("from some-service, hello name: {}", name);
        return "from some-service, hello name:".concat(name);
    }


}
