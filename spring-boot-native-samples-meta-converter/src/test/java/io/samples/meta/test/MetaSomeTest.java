package io.samples.meta.test;

import java.io.IOException;
import java.nio.file.Files;

import com.jayway.jsonpath.DocumentContext;
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
        DocumentContext dc = JsonPath.parse(json);
        Object author0 = JsonPath.read(document, "$.store.book[0].author");
        String author1 = JsonPath.read(document, "$.store.book[1].author");

        log.info("author-0:{}", author0);
        Object book0 = JsonPath.read(document, "$.store.book").toString();
        log.info("book-0:{}", book0);
        Object bicycle0 = JsonPath.read(document, "$.store.bicycle");
        log.info("bicycle-0:{}", bicycle0);
        log.info("bicycle-color-0:{}", JsonPath.read(bicycle0, "$.color").toString());
    }
}
