package io.samples.registry.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.registry.bean.MyBean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/registry")
public class RegistryController {

    @Autowired
    ConfigurableApplicationContext context;

    @GetMapping("/hello")
    public String hello(String name) {
        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    @GetMapping("/context")
    public String context() {
        Boolean status = (context == null);

        if(!context.containsBean("myBean")) {
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(MyBean.class);
            beanDefinitionBuilder.addPropertyValue("date", new Date());
            BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

            BeanDefinitionRegistry beanFactory = (BeanDefinitionRegistry) context.getBeanFactory();
            beanFactory.registerBeanDefinition("myBean", beanDefinition);
        }

        MyBean myBean = context.getBean(MyBean.class);
        return myBean.doSomething();
    }


}
