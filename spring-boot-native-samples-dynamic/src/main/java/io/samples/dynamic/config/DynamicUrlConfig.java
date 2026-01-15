package io.samples.dynamic.config;

import java.lang.reflect.Method;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import io.samples.dynamic.controller.DynamicUrlController;
import io.samples.dynamic.controller.DynamicUrlHandler;
import io.samples.dynamic.controller.SomeService;
import jakarta.annotation.PostConstruct;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
@Configuration
class DynamicUrlConfig {
    @Bean
    public DynamicUrlHandler dynamicUrlHandler(SomeService someService) {
        return new DynamicUrlHandler(someService);
    }
}
