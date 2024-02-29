package io.samples.web.actuator;

import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/25
 */
@Slf4j
public class SomeTest {

    @Test
    void test() {
        log.info("=======================Test=======================");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);

        log.info("=================status:{}, content:{}", responseEntity.getStatusCode(), responseEntity.getBody());

    }


}
