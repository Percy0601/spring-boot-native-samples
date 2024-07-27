package io.samples.web.util;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Objects;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/27
 */
@Slf4j
public class FreemarkerUtil {
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
        resource = new ClassPathResource("templates/");
        configuration = new Configuration(Configuration.getVersion());
    }

    public String format(String templateName, Map<String, Object> params) {
        try {
            configuration.setDirectoryForTemplateLoading(resource.getFile());
            configuration.setDefaultEncoding("utf-8");
            Template template = configuration.getTemplate(templateName);
            Writer out = new StringWriter();
            template.process(params, out);
            String result = out.toString();
            log.info("========={}", result);
            out.close();
            return result;
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        }
    }
}
