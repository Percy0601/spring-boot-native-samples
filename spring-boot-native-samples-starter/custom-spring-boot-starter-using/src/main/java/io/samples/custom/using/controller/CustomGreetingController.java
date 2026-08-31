package io.samples.custom.using.controller;

import java.util.Map;

import io.samples.custom.GreetingDecorator;
import io.samples.custom.GreetingService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 使用方控制器：验证自动配置注入的 Bean。
 */
@RestController
@RequestMapping("/custom")
public class CustomGreetingController {

    private final GreetingService greetingService;

    private final ObjectProvider<GreetingDecorator> decoratorProvider;

    public CustomGreetingController(GreetingService greetingService,
                                    ObjectProvider<GreetingDecorator> decoratorProvider) {
        this.greetingService = greetingService;
        this.decoratorProvider = decoratorProvider;
    }

    @GetMapping("/greeting")
    public String greeting(@RequestParam(defaultValue = "World") String name) {
        return greetingService.greeting(name);
    }

    /**
     * 展示当前生效的实现类，便于观察条件注解的效果。
     */
    @GetMapping("/info")
    public Map<String, String> info() {
        GreetingDecorator decorator = decoratorProvider.getIfAvailable();
        return Map.of(
                "greetingService", greetingService.getClass().getSimpleName(),
                "greetingDecorator", decorator != null ? decorator.getClass().getSimpleName() : "none");
    }
}
