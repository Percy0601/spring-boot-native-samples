package io.samples.web.controller;

import java.io.IOException;

import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.util.Timeout;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {

    @GetMapping("/hello")
    public String hello() {
        log.info("hello world!");
        return "Hello World!";
    }

    @GetMapping("/test-rest-template")
    public String testRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);
        log.info("=================status:{}, content:{}", responseEntity.getStatusCode(), responseEntity.getBody());
        return responseEntity.getBody();
    }

    @GetMapping("/test-httpclient5")
    public String testHttpclient5() {
        String result = get("https://www.baidu.com");
        return result;
    }

    private String get(String url) {
        String resultContent = null;
        // 设置超时时间
        RequestConfig config = RequestConfig.custom().setConnectTimeout(Timeout.ofMilliseconds(5000L)).setConnectionRequestTimeout(Timeout.ofMilliseconds(5000L)).setResponseTimeout(Timeout.ofMilliseconds(5000L)).build();
        // 请求级别的超时
        HttpGet httpGet = new HttpGet(url);
        //httpGet.setConfig(config);
        //try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
        // 客户端级别的超时
        try (CloseableHttpClient httpclient = HttpClients.custom().setDefaultRequestConfig(config).build()) {
            try (CloseableHttpResponse response = httpclient.execute(httpGet)) {
                // 获取状态码
//                System.out.println(response.getVersion()); // HTTP/1.1
//                System.out.println(response.getCode()); // 200
//                System.out.println(response.getReasonPhrase()); // OK
                HttpEntity entity = response.getEntity();
                // 获取响应信息
                resultContent = EntityUtils.toString(entity);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return resultContent;

    }
}