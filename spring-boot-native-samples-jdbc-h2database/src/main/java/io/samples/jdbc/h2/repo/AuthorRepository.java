package io.samples.jdbc.h2.repo;

import java.util.Optional;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;

import io.samples.jdbc.h2.entity.Author;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
public interface AuthorRepository extends ListCrudRepository<Author, Long> {

    Optional<Author> findByNameContainingIgnoreCase(String partialName);

    @Query("select * from author a where a.name = :name limit 1")
    Optional<Author> queryFindByName(String name);
}
