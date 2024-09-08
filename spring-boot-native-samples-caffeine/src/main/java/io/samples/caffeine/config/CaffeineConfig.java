package io.samples.caffeine.config;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/8
 */
@Configuration
public class CaffeineConfig {

    @Primary
    @Bean(name = "customCacheManager")
    public CacheManager cacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List<Cache> cacheList = new ArrayList<>();
        cacheList.add(caffeine());
        cacheManager.setCaches(cacheList);
        return cacheManager;
    }

    @Bean
    public Cache caffeine() {
        return new CaffeineCache("customCache",
                Caffeine.newBuilder()
                        .maximumSize(10000)
                        .expireAfterWrite(3, TimeUnit.MINUTES)
                        .build(),
                true);
    }

}
