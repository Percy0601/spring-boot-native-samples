package io.samples.spi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/29
 */
public class DatabaseSearch implements Search{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public List<String> searchDoc(String keyword) {
        log.info("database search implements search.");
        return List.of("Database Search Result");
    }
}
