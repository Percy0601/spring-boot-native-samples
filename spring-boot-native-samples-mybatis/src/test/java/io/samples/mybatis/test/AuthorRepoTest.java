package io.samples.mybatis.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.entity.Book;
import io.samples.mybatis.repo.AuthorRepository;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Slf4j
@SpringBootTest
public class AuthorRepoTest {

    @Autowired
    AuthorRepository authorRepository;

    @Test
    void test() {
        log.info("=================={}", (null == authorRepository));
    }

    private void deleteAll() {
        this.authorRepository.deleteAll();
        long count = this.authorRepository.count();
        System.out.printf("deleteAll(): count = %d%n", count);
    }

    private void queryFindByName() {
        Author author1 = this.authorRepository.queryFindByName("Josh Long").orElse(null);
        Author author2 = this.authorRepository.queryFindByName("Martin Kleppmann").orElse(null);

        System.out.printf("queryFindByName(): author1 = %s%n", author1);
        System.out.printf("queryFindByName(): author2 = %s%n", author2);
    }

    private void findByPartialName() {
        Author author1 = this.authorRepository.findByNameContainingIgnoreCase("sh lo").orElse(null);
        Author author2 = this.authorRepository.findByNameContainingIgnoreCase("in kl").orElse(null);

        System.out.printf("findByPartialName(): author1 = %s%n", author1);
        System.out.printf("findByPartialName(): author2 = %s%n", author2);
    }

    private void findById() {
        Author author1 = this.authorRepository.findById(1L).orElse(null);
        Author author2 = this.authorRepository.findById(2L).orElse(null);

        System.out.printf("findById(): author1 = %s%n", author1);
        System.out.printf("findById(): author2 = %s%n", author2);
    }

    private void listAllAuthors() {
        List<Author> authors = this.authorRepository.findAll();
        for (Author author : authors) {
            System.out.printf("listAllAuthors(): author = %s%n", author);
            for (Book book : author.getBooks()) {
                System.out.printf("\t%s%n", book);
            }
        }
    }



}
