package io.samples.meta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@SpringBootApplication
public class MetaBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(MetaBootstrap.class, args);
    }
}
