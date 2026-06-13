package io.samples.mybatis.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.repo.AuthorRepository;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@RestController
@RequestMapping("/jdbc")
public class JdbcController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AuthorRepository authorRepository;

    @GetMapping("/findAll")
    public List<Author> findAll() {
        List<Author> authors = authorRepository.findAll();
        log.info("find-all author:{}", authors);
        return authors;
    }

}
