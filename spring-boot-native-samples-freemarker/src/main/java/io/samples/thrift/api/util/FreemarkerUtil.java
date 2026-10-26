package io.samples.thrift.api.util;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.util.ClassUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.samples.thrift.api.fn.DivideFunction;
import io.samples.thrift.api.fn.NowFunction;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/27
 */
public class FreemarkerUtil {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    private static FreemarkerUtil instance;
    private Resource resource;
    private Configuration configuration;

    private FreemarkerUtil() {

    }

    public static FreemarkerUtil getInstance() {
        if(Objects.nonNull(instance)) {
            return instance;
        }
        instance = new FreemarkerUtil();
        instance.init();
        return instance;
    }

    private void init() {
        configuration = new Configuration(Configuration.getVersion());
        configuration.setClassLoaderForTemplateLoading(ClassUtils.getDefaultClassLoader(), "templates");
        configuration.setDefaultEncoding("utf-8");
        // 注册自定义函数（与 Spring 管理的 Configuration 保持一致）
        configuration.setSharedVariable("now", new NowFunction());
        configuration.setSharedVariable("divide", new DivideFunction());
    }

    public String format(String templateName, Map<String, Object> params) {
        try {
            Template template = configuration.getTemplate(templateName);
            Writer out = new StringWriter();
            template.process(params, out);
            String result = out.toString();
            out.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            // FreeMarker 会把函数抛出的业务异常包在 TemplateException 里。
            // 这里解包，让上层（Controller）能直接 catch 到我们的 FreemarkerFnException。
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException re) {
                throw re;
            }
            throw new RuntimeException(e);
        }
    }
}
