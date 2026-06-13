package io.samples.jaxb.vo;
import jakarta.xml.bind.*;
import jakarta.xml.bind.annotation.*;
import java.io.StringReader;
import java.io.StringWriter;

// 定义 JAXB 注解的实体类
@XmlRootElement(name = "book")
@XmlAccessorType(XmlAccessType.FIELD)
public class Book {
    @XmlElement
    private String title;

    @XmlElement
    private String author;

    @XmlAttribute
    private String isbn;

    public Book() {}

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    @Override
    public String toString() {
        return "Book{title='" + title + "', author='" + author + "', isbn='" + isbn + "'}";
    }
}

