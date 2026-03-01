package io.samples.meta.test;

import java.io.IOException;
import java.nio.file.Files;

import com.jayway.jsonpath.DocumentContext;
import io.samples.meta.MetaClass;
import io.samples.meta.MetaUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import tools.jackson.databind.ObjectMapper;

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
        Object book0 = JsonPath.read(document, "$.store.book");
        log.info("book-0:{}", book0);
        Object bicycle0 = JsonPath.read(document, "$.store.bicycle");
        log.info("bicycle-0:{}", bicycle0);
        log.info("bicycle-color-0:{}", JsonPath.read(bicycle0, "$.color").toString());
    }


    @Test
    void testAntMath() {
        PathMatcher matcher = new AntPathMatcher();
        String fullpath = "石家庄以岭药业股份有限公司-营销公司-配方颗粒事业部-营销部-新业务部-业务拓展一部";
        fullpath = fullpath.replaceAll("-", "/");
        boolean result = matcher.match("石家庄以岭药业股份有限公司/营销公司/配方颗粒事业部/营销部/新业务部/**", fullpath);

        log.info("result:{}", result);
    }

    @Test
    void testMetaClass() {
        ClassPathResource classPathResource = new ClassPathResource("./222.json");
        String json = "";
        try {
            json = Files.readString(classPathResource.getFilePath());
            //log.info("========{}", json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        MetaClass metaClass = MetaUtil.metaClass("Market", json);
        ObjectMapper mapper = new ObjectMapper();
        log.info("=========={}", mapper.writeValueAsString(metaClass));
    }
}
