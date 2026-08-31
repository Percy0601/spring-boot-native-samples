package io.samples.custom;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 自定义 Greeting Starter 的配置项，前缀 {@code custom.greeting}。
 *
 * @param prefix    问候语前缀，默认 {@code Hello}
 * @param suffix    问候语后缀，默认 {@code !}
 * @param enabled   whether the custom greeting starter is enabled
 * @param uppercase whether to register the uppercase {@link GreetingDecorator}
 */
@ConfigurationProperties(prefix = "custom.greeting")
public record GreetingProperties(String prefix, String suffix, boolean enabled, boolean uppercase) {

    public GreetingProperties {
        if (prefix == null || prefix.isBlank()) {
            prefix = "Hello";
        }
        if (suffix == null) {
            suffix = "!";
        }
    }
}
