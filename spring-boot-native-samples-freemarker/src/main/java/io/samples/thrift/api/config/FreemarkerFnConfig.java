package io.samples.thrift.api.config;

import io.samples.thrift.api.fn.DivideFunction;
import io.samples.thrift.api.fn.NowFunction;
import org.springframework.boot.freemarker.autoconfigure.FreeMarkerVariablesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * 注册 FreeMarker 自定义函数到 Spring 管理的 Configuration。
 * <p>
 * 通过 FreeMarkerVariablesCustomizer 把函数放进全局 shared variables，
 * 所有由 Spring MVC 渲染的模板都能直接调用 ${now()}、${divide(a,b)}。
 *
 * @author baoxin.zhao
 */
@Configuration
public class FreemarkerFnConfig {

    @Bean
    public FreeMarkerVariablesCustomizer freemarkerVariablesCustomizer() {
        return variables -> {
            variables.put("now", new NowFunction());
            variables.put("divide", new DivideFunction());
        };
    }
}
