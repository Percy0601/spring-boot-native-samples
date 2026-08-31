package io.samples.custom;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBooleanProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * 自定义 Greeting Starter 的自动配置类，演示 Spring Boot 4 条件注解组合：
 *
 * <ul>
 *     <li>{@link ConditionalOnClass}：类路径上存在 {@link GreetingService} 才生效（ASM 解析，不加载类）</li>
 *     <li>{@link ConditionalOnBooleanProperty}：{@code custom.greeting.enabled=false} 时整体关闭（Boot 4 新增注解）</li>
 *     <li>{@link ConditionalOnMissingBean}：使用方自定义了 GreetingService / GreetingDecorator 时自动回退（back off）</li>
 *     <li>{@link ConditionalOnProperty}：{@code custom.greeting.uppercase=true} 时额外注册大写装饰器</li>
 * </ul>
 *
 * <p>必须通过 {@code META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports}
 * 注册，且不能放在使用方的组件扫描包路径下。
 */
@AutoConfiguration
@EnableConfigurationProperties(GreetingProperties.class)
@ConditionalOnClass(GreetingService.class)
@ConditionalOnBooleanProperty(prefix = "custom.greeting", name = "enabled", matchIfMissing = true)
public class GreetingServiceAutoConfiguration {

    /**
     * 默认问候服务；使用方若已注册任意 {@link GreetingService}，则本 Bean 回退不创建。
     */
    @Bean
    @ConditionalOnMissingBean(GreetingService.class)
    public DefaultGreetingService greetingService(GreetingProperties properties,
                                                  ObjectProvider<GreetingDecorator> decoratorProvider) {
        return new DefaultGreetingService(properties, decoratorProvider.getIfAvailable());
    }

    /**
     * 大写装饰器；仅在 {@code custom.greeting.uppercase=true} 时注册，且使用方未提供装饰器时回退。
     */
    @Bean
    @ConditionalOnProperty(prefix = "custom.greeting", name = "uppercase", havingValue = "true")
    @ConditionalOnMissingBean(GreetingDecorator.class)
    public UppercaseGreetingDecorator uppercaseGreetingDecorator() {
        return new UppercaseGreetingDecorator();
    }
}
