package io.samples.web.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/jvm")
    public String jvm() {
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        //OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();

        String name = runtime.getVmName();
        String vendor = runtime.getVmVendor();
        String version = runtime.getVmVersion();
        // native: Substrate VM
        log.info("jvm info: name:{}, vendor:{}, version:{}", name, vendor, version);
        return "jvm info, name: "
                .concat(name)
                .concat("; vendor: ")
                .concat(vendor)
                .concat("; version: ")
                .concat(version)
                .concat("!");
    }

    @GetMapping("/test-rest-template")
    public String testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        log.info("=================status:{}, content:{}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity.getBody();
    }
}
