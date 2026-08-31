package io.samples.custom;

/**
 * 默认问候服务实现：{@code prefix + ", " + name + suffix}，如配置了装饰器则再做加工。
 */
public class DefaultGreetingService implements GreetingService {

    private final GreetingProperties properties;

    private final GreetingDecorator decorator;

    public DefaultGreetingService(GreetingProperties properties, GreetingDecorator decorator) {
        this.properties = properties;
        this.decorator = decorator;
    }

    @Override
    public String greeting(String name) {
        String message = properties.prefix() + ", " + name + properties.suffix();
        return (decorator != null) ? decorator.decorate(message) : message;
    }
}
