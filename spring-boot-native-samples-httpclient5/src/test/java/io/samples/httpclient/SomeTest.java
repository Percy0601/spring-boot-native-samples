package io.samples.httpclient;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/25
 */
//@Slf4j
public class SomeTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Test
    void test() {
        log.info("=======================Test=======================");

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://www.baidu.com", String.class);

        log.info("=================status:{}, content:{}", responseEntity.getStatusCode(), responseEntity.getBody());

    }


}
