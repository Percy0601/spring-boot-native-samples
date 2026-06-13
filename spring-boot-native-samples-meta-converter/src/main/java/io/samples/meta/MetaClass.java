package io.samples.meta;

import java.util.List;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */

public class MetaClass {
    private String name;
    private String namespace;
    private MetaType metaType;
    private List<MetaProperty> properties;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public MetaType getMetaType() {
        return metaType;
    }

    public void setMetaType(MetaType metaType) {
        this.metaType = metaType;
    }

    public List<MetaProperty> getProperties() {
        return properties;
    }

    public void setProperties(List<MetaProperty> properties) {
        this.properties = properties;
    }

    @Override
    public String toString() {
        return "MetaClass{" + "name='" + name + '\'' + ", namespace='" + namespace + '\'' + ", metaType=" + metaType + ", properties=" + properties + '}';
    }
}
