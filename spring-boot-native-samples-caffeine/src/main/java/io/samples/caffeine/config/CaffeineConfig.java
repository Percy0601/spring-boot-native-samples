package io.samples.caffeine.config;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.Caffeine;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/8
 */
@Configuration
public class CaffeineConfig {

    @Bean(name = "localCacheManager")
    public CacheManager cacheManager() {
        CaffeineCacheManager caffeineCacheManager = new CaffeineCacheManager();
        Caffeine<Object, Object> caffeine = caffeine();
        caffeineCacheManager.setCaffeine(caffeine);
        return caffeineCacheManager;
    }


    @Bean
    public Caffeine<Object, Object> caffeine() {
        return Caffeine.newBuilder()
                .maximumSize(10000)
                .expireAfterWrite(3, TimeUnit.MINUTES);
    }

}
