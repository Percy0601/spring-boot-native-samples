package io.samples.mybatis.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/count")
    public Map<String, Integer> count() {
        Integer count = authorMapper.count();
        log.info("count:{}", count);

        Map<String, Integer> result = new HashMap<>();
        result.put("count", count);
        return result;
    }

}
