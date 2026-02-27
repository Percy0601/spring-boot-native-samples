package io.samples.meta.test;

import java.io.IOException;
import java.nio.file.Files;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;

@Slf4j
public class MetaSomeTest {


    @Test
    void test() {
        log.info("======{}", Integer.class.getName());
    }


    @Test
    void testJsonPath() {
        ClassPathResource classPathResource = new ClassPathResource("./222.json");
        String json = "";
        try {
            json = Files.readString(classPathResource.getFilePath());
            //log.info("========{}", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);

        String author0 = JsonPath.read(document, "$.store.book[0].author");
        String author1 = JsonPath.read(document, "$.store.book[1].author");

        log.info("author-0:{}", author0);
        String book0 = JsonPath.read(document, "$.store.book").toString();
        log.info("book-0:{}", book0);
    }
}
