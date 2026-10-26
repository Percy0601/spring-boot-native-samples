package io.samples.thrift.api.controller;

import io.samples.thrift.api.fn.FreemarkerFnException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常处理器：捕获 Controller 方法执行阶段抛出的 FreemarkerFnException。
 * <p>
 * 注意：视图渲染阶段（controller 方法已 return、正在渲染模板）抛出的异常
 * 不会经过这里，而是走 Spring Boot 默认 /error 页面。
 *
 * @author baoxin.zhao
 */
@ControllerAdvice
public class FreemarkerExceptionAdvice {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerExceptionAdvice.class);

    @ExceptionHandler(FreemarkerFnException.class)
    @ResponseBody
    public String handleFreemarkerFnException(FreemarkerFnException e) {
        log.warn("全局异常处理器(@ControllerAdvice)捕获到: {}", e.getMessage());
        return "⚠️ @ControllerAdvice 全局捕获到自定义异常:\n" +
                "  异常类型: " + e.getClass().getName() + "\n" +
                "  异常信息: " + e.getMessage();
    }
}
