package io.samples.thrift.api.fn;

import freemarker.template.TemplateMethodModelEx;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;

import java.util.List;

/**
 * 自定义函数 #2：divide(a, b) —— 浮点除法，除数为 0 时抛出自定义异常。
 * <p>
 * 用法（FTL）：${divide(10, 3)}
 *
 * @author baoxin.zhao
 */
public class DivideFunction implements TemplateMethodModelEx {

    @Override
    public Object exec(List arguments) throws TemplateModelException {
        if (arguments.size() != 2) {
            throw new FreemarkerFnException("divide(a, b) 需要恰好 2 个参数，实际收到 " + arguments.size() + " 个");
        }

        double a = asNumber(arguments.get(0), "a");
        double b = asNumber(arguments.get(1), "b");

        if (b == 0.0d) {
            // 关键：在这里抛出自定义业务异常
            throw new FreemarkerFnException("除数不能为 0: a=" + a + ", b=" + b);
        }

        return a / b;
    }

    private double asNumber(Object arg, String name) throws TemplateModelException {
        if (arg instanceof TemplateNumberModel numModel) {
            return numModel.getAsNumber().doubleValue();
        }
        throw new FreemarkerFnException("参数 '" + name + "' 必须是数字，实际类型: " + arg.getClass().getName());
    }
}
