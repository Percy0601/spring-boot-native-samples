package io.samples.mybatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@SpringBootApplication(proxyBeanMethods = false)
public class MybatisBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(MybatisBootstrap.class, args);
    }
}
