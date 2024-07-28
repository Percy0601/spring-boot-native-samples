package io.samples.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.minio.ObjectWriteResponse;
import io.samples.web.MinioService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {
    @Autowired
    MinioService minioService;

    @GetMapping("/hello")
    public String hello(String name) {
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/put-string")
    public String putString(@RequestParam String name, @RequestParam String content) {
        ObjectWriteResponse response = minioService.upload(name, content);
        return response.toString();
    }

    @GetMapping("/get-string")
    public String getString(@RequestParam String name) {
        String response = minioService.getObject(name);
        return response;
    }
}
