package io.samples.mybatis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import io.samples.mybatis.entity.Author;
import io.samples.mybatis.repo.AuthorMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/2/27
 */
@Slf4j
@RestController
@RequestMapping("/mybatis")
public class MybatisController {
    @Autowired
    AuthorMapper authorMapper;

    @GetMapping("/find")
    public List<Author> find() {
        QueryWrapper<Author> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Author::getName, "abc");
        List<Author> authors = authorMapper.selectList(queryWrapper);
        log.info("find author:{}", authors);
        return authors;
    }

}
