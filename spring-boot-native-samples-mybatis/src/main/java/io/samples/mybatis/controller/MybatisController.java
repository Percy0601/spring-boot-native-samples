package io.samples.mybatis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.entity.Book;
import io.samples.mybatis.mapper.BookMapper;
import io.samples.mybatis.repo.AuthorRepository;
import io.samples.mybatis.repo.BookRepository;
import io.samples.mybatis.vo.BookVO;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@RestController
@RequestMapping("/mybatis")
public class MybatisController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;
    @GetMapping("/findAll")
    public List<Author> findAll() {
        List<Author> authors = authorRepository.findAll();
        log.info("find-all author:{}", authors);
        return authors;
    }

    @GetMapping("/find-all-book")
    public List<Book> findAllBooks() {
        List<Book> books = bookMapper.findAll();
        log.info("find-all book:{}", books);
        return books;
    }

    @GetMapping("/find-book-id")
    public BookVO findBookById(@RequestParam Long id) {
        BookVO book = bookMapper.selectById(id);
        log.info("find book id:{}, book: {}", id, book);
        return book;
    }

    @GetMapping("/create-book")
    public Long createBook(@RequestParam String title) {
        Book book = new Book();
        book.setTitle(title);
        bookRepository.save(book);
        return book.getId();
    }
}
