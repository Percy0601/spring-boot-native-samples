package io.samples.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author: baoxin.zhao
 * @date: 2024/8/19
 */
@Service
@CacheConfig(cacheNames = "some-key")
public class SomeService {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Cacheable
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
