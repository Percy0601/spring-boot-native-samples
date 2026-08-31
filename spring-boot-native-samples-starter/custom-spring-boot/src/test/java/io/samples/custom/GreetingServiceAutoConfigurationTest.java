package io.samples.custom;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * 使用 {@link ApplicationContextRunner} 验证自动配置的各条件注解行为。
 */
class GreetingServiceAutoConfigurationTest {

    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(GreetingServiceAutoConfiguration.class));

    @Test
    void defaultConfigurationCreatesService() {
        contextRunner.run(context -> {
            assertThat(context).hasSingleBean(GreetingService.class);
            assertThat(context.getBean(GreetingService.class).greeting("World")).isEqualTo("Hello, World!");
        });
    }

    @Test
    void disabledPropertyBacksOff() {
        contextRunner.withPropertyValues("custom.greeting.enabled=false")
                .run(context -> assertThat(context).doesNotHaveBean(GreetingService.class));
    }

    @Test
    void userDefinedServiceBacksOff() {
        contextRunner.withUserConfiguration(UserGreetingServiceConfig.class)
                .run(context -> {
                    assertThat(context).doesNotHaveBean(DefaultGreetingService.class);
                    assertThat(context).hasSingleBean(GreetingService.class);
                    assertThat(context.getBean(GreetingService.class).greeting("World")).isEqualTo("[Custom] Hola, World");
                });
    }

    @Test
    void uppercasePropertyRegistersDecorator() {
        contextRunner.withPropertyValues("custom.greeting.uppercase=true")
                .run(context -> {
                    assertThat(context).hasSingleBean(GreetingDecorator.class);
                    assertThat(context.getBean(GreetingService.class).greeting("World")).isEqualTo("HELLO, WORLD!");
                });
    }

    @Configuration(proxyBeanMethods = false)
    static class UserGreetingServiceConfig {

        @Bean
        GreetingService customGreetingService() {
            return name -> "[Custom] Hola, " + name;
        }
    }
}
