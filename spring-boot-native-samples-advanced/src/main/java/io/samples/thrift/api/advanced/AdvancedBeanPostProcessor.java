package io.samples.thrift.api.advanced;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/11/10
 */

@Slf4j
public class AdvancedBeanPostProcessor implements BeanPostProcessor {

    @Value("${advance.cache.bean.prefix:io.samples}")
    private String prefix;
    private Map<String, Object> services;

    public AdvancedBeanPostProcessor() {
        log.info("1. advanced-bean-post-processor init");
    }

    @PostConstruct
    public void after() {
        log.info("2. advanced-bean-post-processor init finish.");
        services = new ConcurrentHashMap<>();
    }


    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(!bean.getClass().getName().startsWith(prefix)) {
            return bean;
        }

        log.info("advanced bean post process handle provider service, name:{}", bean.getClass().getName());

        if(beanName.endsWith("$AutumnProviderService")) {
            log.info("autumn bean post process handle provider service, name:{}", beanName);
        }

        if(beanName.endsWith("$AutumnConsumerClient")) {
            log.info("autumn bean post process handle consumer client, name:{}", beanName);
        }

        return bean;
    }
}
