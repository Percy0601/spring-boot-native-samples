package io.samples.custom;

/**
 * 问候语装饰器：对问候语做二次加工（可选能力，由条件注解控制是否注册）。
 */
public interface GreetingDecorator {

    /**
     * 装饰问候语。
     *
     * @param message 原始问候语
     * @return 装饰后的问候语
     */
    String decorate(String message);
}
