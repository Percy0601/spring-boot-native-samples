package io.samples.mybatis.repo;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import io.samples.mybatis.entity.Author;

/**
 * @author: baoxin.zhao
 * @date: 2024/6/22
 */
@Mapper
public interface AuthorMapper {

    Integer count();

}
