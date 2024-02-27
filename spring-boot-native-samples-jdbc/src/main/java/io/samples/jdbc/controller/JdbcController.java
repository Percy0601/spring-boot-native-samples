package io.samples.jdbc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.jdbc.entity.Author;
import io.samples.jdbc.repo.AuthorRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Slf4j
@RestController
@RequestMapping("/jdbc")
public class JdbcController {
    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/findAll")
    public List<Author> findAll() {
        List<Author> authors = authorRepository.findAll();
        log.info("find-all author:{}", authors);
        return authors;
    }

}
