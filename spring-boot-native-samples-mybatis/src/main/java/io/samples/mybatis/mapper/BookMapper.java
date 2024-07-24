package io.samples.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import io.samples.mybatis.entity.Book;

/**
 * @author: baoxin.zhao
 * @date: 2024/7/24
 */

@Mapper
public interface BookMapper {

    @Select("select id, title from book ")
    List<Book> findAll();
}
