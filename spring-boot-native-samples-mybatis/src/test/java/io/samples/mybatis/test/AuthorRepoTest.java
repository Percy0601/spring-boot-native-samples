package io.samples.mybatis.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.repo.AuthorMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Slf4j
@SpringBootTest
public class AuthorRepoTest {

    @Autowired
    AuthorMapper authorMapper;

    @Test
    void test() {
        log.info("=================={}", (null == authorMapper));
        insertAuthors();
    }

    @Test
    void insertAuthors() {
        Author author = new Author();
        author.setName("abc");
        authorMapper.insert(author);
        System.out.printf("insertAuthors, author:{} ", author);
    }



}
