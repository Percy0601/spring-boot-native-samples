package io.samples.html.controller;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
//@Slf4j
@RestController
@RequestMapping("/web")
public class WebController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @GetMapping("/hello")
    public String hello(String name) {
        if(!StringUtils.hasText(name)) {
            name = "Percy";
        }

        log.info("hello world, {}", name);
        return "Hello World, ".concat(name).concat("!");
    }

    /**
     * 加载磁盘绝对路径
     * @return
     */
    @GetMapping("/load-html")
    public String loadHtml() {
        String content = "";
        try {
            Path path = Path.of("/data/html/static.html");
            content = Files.readString(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return content;
    }

    /**
     * 加载native-image路径
     * @return
     */
    @GetMapping("/load-native-html")
    public String loadNativeHtml() {
        String content = "";
        InputStream inputStream = null;
        try {
            URI uri = this.getClass().getResource("/resources/r.html").toURI();
            inputStream = this.getClass().getResourceAsStream("/resources/r.html");
            byte[] bytes = inputStream.readAllBytes();
            content = new String(bytes, Charset.forName("UTF-8"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            if(null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return content;
    }
}
