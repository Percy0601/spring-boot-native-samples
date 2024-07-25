package io.samples.mybatis.repo;

import org.springframework.data.repository.ListCrudRepository;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.entity.Book;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
public interface BookRepository extends ListCrudRepository<Book, Long> {

}
