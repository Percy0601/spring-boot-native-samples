package io.samples.jaxb.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.samples.jaxb.vo.Book;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

/**
 * @author: baoxin.zhao
 * @date: 2024/02/24
 */
//@Slf4j
@RestController
@RequestMapping("/jaxb")
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
            inputStream = this.getClass().getResourceAsStream("/resources/r.html");
            byte[] bytes = inputStream.readAllBytes();
            content = new String(bytes, Charset.forName("UTF-8"));
        } catch (IOException e) {
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

    @GetMapping("/marshal")
    public String marshal() {
        Book book = new Book("Effective Java", "Joshua Bloch", "978-0134685991");
        log.info("原始对象: {}" + book);

        // 2. 创建 JAXBContext (使用 EclipseLink MOXy 实现)
        JAXBContext context = null;
        StringWriter writer = null;
        String content = null;
        try {
            context = JAXBContext.newInstance(Book.class);
            // 3. 序列化为 XML
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            writer = new StringWriter();
            marshaller.marshal(book, writer);
            writer.flush();
            content = writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } finally {
            if(null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        log.info("序列号后的内容:{}", content);
        return content;

//        String xml = writer.toString();
//        System.out.println("\n生成的 XML:");
//        System.out.println(xml);
//
//        // 4. 反序列化回对象
//        Unmarshaller unmarshaller = context.createUnmarshaller();
//        StringReader reader = new StringReader(xml);
//        Book unmarshalledBook = (Book) unmarshaller.unmarshal(reader);
//
//        System.out.println("\n反序列化的对象: " + unmarshalledBook);
//        System.out.println("对象相等: " + book.toString().equals(unmarshalledBook.toString()));toString
    }

    @GetMapping("/unmarshal")
    public Book unmarshaller() {
        JAXBContext context = null;
        Unmarshaller unmarshaller = null;
        InputStream inputStream = null;
        String xml = "";
        StringReader reader = null;
        Book unmarshalledBook = null;
        try {
            context = JAXBContext.newInstance(Book.class);
            unmarshaller = context.createUnmarshaller();
            inputStream = this.getClass().getResourceAsStream("/xml/123.xml");
            byte[] bytes = inputStream.readAllBytes();
            xml = new String(bytes, Charset.forName("UTF-8"));
            reader = new StringReader(xml);
            // 创建 StreamSource
            Source source = new StreamSource(reader);
            unmarshalledBook = unmarshaller.unmarshal(source, Book.class).getValue();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if(null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if(reader != null) {
                reader.close();
            }
        }
        log.info("#####, {}", unmarshalledBook);
        return unmarshalledBook;
    }
}
