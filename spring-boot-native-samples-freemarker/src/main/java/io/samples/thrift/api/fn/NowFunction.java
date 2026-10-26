package io.samples.thrift.api.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 自定义函数 #1：now() —— 返回当前时间字符串。
 * <p>
 * 用法（FTL）：当前时间是 ${now()}
 *
 * @author baoxin.zhao
 */
public class NowFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date());
    }
}
