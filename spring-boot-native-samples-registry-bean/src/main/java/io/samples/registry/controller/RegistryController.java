package io.samples.registry.controller;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

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
@RequestMapping("/registry")
public class RegistryController {

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

}
