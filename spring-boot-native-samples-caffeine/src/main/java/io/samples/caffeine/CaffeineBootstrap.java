package io.samples.caffeine;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CaffeineBootstrap {

    public static void main(String[] args) {
        SpringApplication.run(CaffeineBootstrap.class, args);
    }

}
