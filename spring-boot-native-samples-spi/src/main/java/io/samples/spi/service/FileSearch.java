package io.samples.spi.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/29
 */
@Slf4j
public class FileSearch implements Search{
    public FileSearch() {
        log.info("begin create file search instance.");
    }

    @Override
    public List<String> searchDoc(String keyword) {
        log.info("file search implements search.");
        return List.of("File Search Result");
    }
}
