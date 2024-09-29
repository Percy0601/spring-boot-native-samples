package io.samples.spi.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/29
 */
@Slf4j
public class DatabaseSearch implements Search{
    @Override
    public List<String> searchDoc(String keyword) {
        log.info("database search implements search.");
        return List.of("Database Search Result");
    }
}
