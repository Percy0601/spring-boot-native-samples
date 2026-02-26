package io.samples.dynamic.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
public class DynamicUrlHandler {
    private SomeService someService;
    public DynamicUrlHandler() {

    }

    public DynamicUrlHandler(SomeService someService) {
        this.someService = someService;
    }

    @ResponseBody
    public ResponseEntity<Object> handleDynamicRequest(@RequestBody(required = false) Map<String, Object> requestBody) {

        // 这里处理动态URL的业务逻辑
        // 可以根据需要访问数据库、调用服务等
        String result = "处理动态请求成功，请求数据: " + requestBody;
        String message = someService.hello("abc");
        result = result.concat(message);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "status", "success",
                        "message", result,
                        "timestamp", System.currentTimeMillis()
                ));
    }


    @ResponseBody
    public ResponseEntity<Object> handleDynamicPathValue(@PathVariable(name="name") String name, HttpServletRequest request) {
        String message = "Hello: ".concat(name);
        Map<String, String> pathVariables = (Map) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        String name2 = pathVariables.get("name");
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Map.of(
                        "status", "success",
                        "message", message,
                        "timestamp", System.currentTimeMillis()
                ));
    }
}
