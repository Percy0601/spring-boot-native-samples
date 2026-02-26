package io.samples.meta;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
public class MetaUtil {
    private MetaUtil() {

    }

    public static MetaClass metaClass(String type, String json) {


        return null;
    }

    public static MetaCollection metaCollection(String type, String json) {
        return null;
    }


    private MetaClass loadDefinition() {
        MetaClass metaClass = new MetaClass();
        metaClass.setName("User");
        metaClass.setNamespace("default");
        List<MetaProperty> properties = new ArrayList<>();
        MetaProperty idProperty = new MetaProperty();
        idProperty.setName("id");
        idProperty.setType(Integer.class.getName());

        MetaProperty nameProperty = new MetaProperty();
        nameProperty.setName("name");
        nameProperty.setType(String.class.getName());

        properties.add(idProperty);
        metaClass.setProperties(properties);
        return metaClass;
    }



}
