package io.samples.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@EnableCaching
@SpringBootApplication
public class CacheBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(CacheBootstrap.class, args);
    }
}
