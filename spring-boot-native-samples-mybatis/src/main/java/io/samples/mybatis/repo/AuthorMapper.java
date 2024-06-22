package io.samples.mybatis.repo;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import io.samples.mybatis.entity.Author;

/**
 * @author: baoxin.zhao
 * @date: 2024/6/22
 */
@Mapper
public interface AuthorMapper extends BaseMapper<Author> {
}
