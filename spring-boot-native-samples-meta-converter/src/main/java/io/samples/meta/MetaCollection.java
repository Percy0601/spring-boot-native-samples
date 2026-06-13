package io.samples.meta;

import java.util.List;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */

public class MetaCollection {
    private String name;
    private List<MetaClass> metas;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MetaClass> getMetas() {
        return metas;
    }

    public void setMetas(List<MetaClass> metas) {
        this.metas = metas;
    }
}
