package io.samples.custom;

/**
 * 大写装饰器：当 {@code custom.greeting.uppercase=true} 时由自动配置注册。
 */
public class UppercaseGreetingDecorator implements GreetingDecorator {

    @Override
    public String decorate(String message) {
        return message.toUpperCase();
    }
}
