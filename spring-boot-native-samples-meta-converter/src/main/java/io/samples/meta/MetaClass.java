package io.samples.meta;

import java.util.List;

import lombok.Data;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
@Data
public class MetaClass {
    private String name;
    private List<MetaProperty> properties;
}
