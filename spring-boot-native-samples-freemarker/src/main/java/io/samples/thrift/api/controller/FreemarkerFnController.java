package io.samples.thrift.api.controller;

import io.samples.thrift.api.fn.FreemarkerFnException;
import io.samples.thrift.api.util.FreemarkerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * FreeMarker 自定义函数 & 异常机制演示。
 *
 * @author baoxin.zhao
 */
@Controller
@RequestMapping("/fn")
public class FreemarkerFnController {

    private static final Logger log = LoggerFactory.getLogger(FreemarkerFnController.class);

    /**
     * 场景 1：调用无参自定义函数 now()。
     */
    @GetMapping("/now")
    public String now() {
        return "fn-now";
    }

    /**
     * 场景 2a：Spring MVC 视图渲染阶段调用 divide(a, b)。
     * 当 b=0 时，异常发生在【视图渲染阶段】（controller 方法已 return），
     * @ExceptionHandler 不会捕获，会走 Spring Boot 默认 /error 页面。
     */
    @GetMapping("/divide")
    public String divide(@RequestParam(defaultValue = "10") double a,
                         @RequestParam(defaultValue = "2") double b,
                         Model model) {
        model.addAttribute("a", a);
        model.addAttribute("b", b);
        return "fn-divide";
    }

    /**
     * 场景 2b：直接用 FreemarkerUtil 渲染字符串，在 Controller 里 try/catch 捕获异常。
     * 异常发生在【controller 方法内部】，可以直接 catch。
     */
    @GetMapping("/divide-caught")
    @ResponseBody
    public String divideCaught(@RequestParam(defaultValue = "10") double a,
                               @RequestParam(defaultValue = "0") double b) {
        Map<String, Object> params = new HashMap<>();
        params.put("a", a);
        params.put("b", b);
        try {
            String result = FreemarkerUtil.getInstance().format("fn-divide-direct.ftl", params);
            return "✅ 渲染成功: " + result;
        } catch (FreemarkerFnException e) {
            log.warn("Controller 内捕获到自定义异常: {}", e.getMessage());
            return "✅ Controller try/catch 捕获到自定义异常:\n" +
                    "  异常类型: " + e.getClass().getName() + "\n" +
                    "  异常信息: " + e.getMessage();
        } catch (Exception e) {
            return "❌ 其他异常: " + e.getClass().getName() + " - " + e.getMessage();
        }
    }

    /**
     * 场景 2c：直接用 FreemarkerUtil 渲染字符串，不在 Controller 里 catch，
     * 让异常向上抛，由 @ControllerAdvice 全局异常处理器接管。
     */
    @GetMapping("/divide-advice")
    @ResponseBody
    public String divideAdvice(@RequestParam(defaultValue = "10") double a,
                               @RequestParam(defaultValue = "0") double b) {
        Map<String, Object> params = new HashMap<>();
        params.put("a", a);
        params.put("b", b);
        // 不 try/catch，直接抛出去
        String result = FreemarkerUtil.getInstance().format("fn-divide-direct.ftl", params);
        return "✅ 渲染成功: " + result;
    }
}
