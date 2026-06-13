package io.samples.spi.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/29
 */
public class FileSearch implements Search{
    private Logger log = LoggerFactory.getLogger(this.getClass());
    public FileSearch() {
        log.info("begin create file search instance.");
    }

    @Override
    public List<String> searchDoc(String keyword) {
        log.info("file search implements search.");
        return List.of("File Search Result");
    }
}
