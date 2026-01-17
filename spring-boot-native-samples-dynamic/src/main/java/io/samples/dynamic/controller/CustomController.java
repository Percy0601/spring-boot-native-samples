package io.samples.dynamic.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/1/15
 */
@Slf4j
@RestController
@RequestMapping("/api/custom")
public class CustomController {

    /**
     * Headers: {host=localhost:8080, user-agent=Mozilla/5.0, accept-language=en-US,en;q=0.9}
     * @param userAgent
     * @param acceptLanguage
     * @return
     */
    @GetMapping("/multiple-headers")
    public String getMultipleHeaders(
            @RequestHeader("User-Agent") String userAgent,
            @RequestHeader("Accept-Language") String acceptLanguage) {
        return "User-Agent: " + userAgent + ", Accept-Language: " + acceptLanguage;
    }

    @GetMapping("/all-headers")
    public String getAllHeaders(@RequestHeader Map<String, Object> headers) {
        return "Headers: " + headers.toString();
    }

    @GetMapping("/all-headers-values")
    public String getAllMultiHeadersValues(@RequestHeader MultiValueMap<String, String> headers) {
        //  Accept-Encoding
        log.info("==============Accept: {}", headers.get("Accept-Encoding"));
        return "Headers: " + headers.toString();
    }

    @GetMapping("/all-params")
    public String getModelAttribute(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        return "all-params: " + params.toString();
    }

}
