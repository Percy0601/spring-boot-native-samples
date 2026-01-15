package io.samples.dynamic.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.samples.dynamic.controller.DynamicUrlController;
import io.samples.dynamic.controller.DynamicUrlHandler;
import jakarta.annotation.PostConstruct;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
@Configuration
class DynamicUrlConfig {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private DynamicUrlController dynamicUrlController;

    /**
     * 应用启动时注册一些初始动态URL
     */
    @PostConstruct
    public void initDynamicUrls() {
        try {
            // 示例：应用启动时注册一个默认的动态URL
            RequestMappingInfo initialMapping = RequestMappingInfo
                    .paths("/api/dynamic/default")
                    .methods(RequestMethod.GET)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .build();

            Method handlerMethod = ReflectionUtils.findMethod(
                    DynamicUrlHandler.class,
                    "handleDynamicRequest",
                    Map.class
            );

            requestMappingHandlerMapping.registerMapping(
                    initialMapping,
                    dynamicUrlController.dynamicUrlHandler(),
                    handlerMethod
            );

        } catch (Exception e) {
            throw new RuntimeException("初始化动态URL失败", e);
        }
    }
}
