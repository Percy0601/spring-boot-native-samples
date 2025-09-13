package io.samples.thrift.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.samples.thrift.api.advanced.AdvancedApplicationListener;
import io.samples.thrift.api.advanced.AdvancedBeanPostProcessor;

/**
 * @author: baoxin.zhao
 * @date: 2024/11/10
 */

@Configuration
public class AdvanceConfig {

    @Bean
    public AdvancedBeanPostProcessor advancedBeanPostProcessor() {
        return new AdvancedBeanPostProcessor();
    }

    @Bean
    public AdvancedApplicationListener advancedApplicationListener() {
        return new AdvancedApplicationListener();
    }



}
