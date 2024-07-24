package io.samples.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@MapperScan(basePackages = "io.samples.mybatis.mapper", sqlSessionTemplateRef = "sqlSessionTemplate")
@SpringBootApplication
public class MybatisBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(MybatisBootstrap.class, args);
    }
}
