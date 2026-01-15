package io.samples.dynamic.controller;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/api/dynamic")
public class DynamicUrlController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private DynamicUrlHandler dynamicUrlHandler;

    private final Map<String, String> urlCache = new ConcurrentHashMap<>();

    /**
     * 动态注册URL接口
     */
    @PostMapping("/register")
    public ResponseEntity<String> registerUrl(@RequestBody UrlRegistrationRequest request) {
        try {
            String path = request.getPath();
            RequestMethod httpMethod = request.getMethod();

            // 构建RequestMappingInfo
            RequestMappingInfo requestMappingInfo = RequestMappingInfo
                    .paths(path)
                    .methods(httpMethod)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .build();

            // 获取处理方法
            Method handlerMethod = ReflectionUtils.findMethod(
                    DynamicUrlHandler.class,
                    "handleDynamicRequest",
                    Map.class
            );

            // 注册映射
            requestMappingHandlerMapping.registerMapping(
                    requestMappingInfo,
                    dynamicUrlHandler,
                    handlerMethod
            );

            // 缓存URL信息（可根据需要存储业务数据）
            urlCache.put(path, request.getBusinessData());

            return ResponseEntity.ok("URL注册成功: " + path);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("URL注册失败: " + e.getMessage());
        }
    }

    /**
     * 动态删除URL接口
     */
    @PostMapping("/unregister")
    public ResponseEntity<String> unregisterUrl(@RequestBody UrlUnregistrationRequest request) {
        try {
            String path = request.getPath();
            RequestMethod httpMethod = request.getMethod();

            // 构建RequestMappingInfo用于查找和删除
            RequestMappingInfo requestMappingInfo = RequestMappingInfo
                    .paths(path)
                    .methods(httpMethod)
                    .build();

            // 删除映射
            requestMappingHandlerMapping.unregisterMapping(requestMappingInfo);

            // 清理缓存
            urlCache.remove(path);

            return ResponseEntity.ok("URL删除成功: " + path);

        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("URL删除失败: " + e.getMessage());
        }
    }

    /**
     * 获取所有已注册的动态URL
     */
    @GetMapping("/urls")
    public ResponseEntity<Map<String, String>> getRegisteredUrls() {
        return ResponseEntity.ok(urlCache);
    }

    /**
     * 应用启动时注册一些初始动态URL
     */
    @PostConstruct
    public void initDynamicUrls() {
        try {
            // 示例：应用启动时注册一个默认的动态URL
            RequestMappingInfo initialMapping = RequestMappingInfo
                    .paths("/api/dynamic/default")
                    .methods(RequestMethod.GET)
                    .produces(MediaType.APPLICATION_JSON_VALUE)
                    .build();

            Method handlerMethod = ReflectionUtils.findMethod(
                    DynamicUrlHandler.class,
                    "handleDynamicRequest",
                    Map.class
            );

            requestMappingHandlerMapping.registerMapping(
                    initialMapping,
                    dynamicUrlHandler,
                    handlerMethod
            );

        } catch (Exception e) {
            throw new RuntimeException("初始化动态URL失败", e);
        }
    }
}
