package io.samples.cache;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/19
 */
@Slf4j
@Service
public class SomeService {

    @Cacheable(key="#name")
    public String trainingCache(String name) {
        log.info("training-cache, name: {}", name);

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        return "Hello, ".concat(name);
    }

}
