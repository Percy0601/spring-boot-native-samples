package io.samples.meta.test;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@SpringBootTest
public class MetaRepoTest {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    void test() {
        log.info("======");
    }
}
