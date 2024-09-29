package io.samples.spi.service;

import java.util.List;

/**
 * @author: baoxin.zhao
 * @date: 2024/9/29
 */
public interface Search {
    List<String> searchDoc(String keyword);
}
