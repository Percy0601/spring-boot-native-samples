package io.samples.custom;

/**
 * 自定义 Starter 对外暴露的核心 API：问候服务。
 */
public interface GreetingService {

    /**
     * 根据名字生成一句问候语。
     *
     * @param name 名字
     * @return 问候语
     */
    String greeting(String name);
}
