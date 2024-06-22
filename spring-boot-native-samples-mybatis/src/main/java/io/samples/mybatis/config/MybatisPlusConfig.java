package io.samples.mybatis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;

/**
 * @author: baoxin.zhao
 * @date: 2024/6/22
 */
@Configuration
public class MybatisPlusConfig {
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor(){
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.H2));
        //TODO 3.5.3.1 以下插件多的话,可能会报异常,这个在最新版本修改.  https://github.com/baomidou/mybatis-plus/issues/5532
        //        plusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        //        plusInterceptor.addInnerInterceptor(new IllegalSQLInnerInterceptor());
        //        plusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return plusInterceptor;
    }
}
