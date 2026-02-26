package io.samples.meta;

import java.util.List;

import lombok.Data;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
@Data
public class MetaCollection {
    private String name;
    private List<MetaClass> metas;
}
