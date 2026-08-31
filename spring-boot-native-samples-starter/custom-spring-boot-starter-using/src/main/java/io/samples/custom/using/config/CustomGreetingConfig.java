package io.samples.custom.using.config;

import io.samples.custom.GreetingService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 使用方自定义配置：演示自动配置的 {@code @ConditionalOnMissingBean} 回退机制。
 *
 * <p>当 {@code custom.greeting.custom-service-enabled=true} 时，注册使用方自己的 GreetingService，
 * 自动配置里的 {@code DefaultGreetingService} 会因 {@code @ConditionalOnMissingBean(GreetingService.class)}
 * 而回退（back off），从而被本 Bean 替换。
 */
@Configuration(proxyBeanMethods = false)
public class CustomGreetingConfig {

    @Bean
    @ConditionalOnProperty(prefix = "custom.greeting", name = "custom-service-enabled", havingValue = "true")
    public GreetingService customGreetingService() {
        return name -> "[Custom] Hola, " + name;
    }
}
