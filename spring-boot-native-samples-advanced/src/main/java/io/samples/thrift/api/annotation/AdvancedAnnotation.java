package io.samples.thrift.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.annotation.Resource;

/**
 * @author: baoxin.zhao
 * @date: 2024/11/17
 */
@Inherited
@Target({ ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Resource
@Documented
public @interface AdvancedAnnotation {
}
