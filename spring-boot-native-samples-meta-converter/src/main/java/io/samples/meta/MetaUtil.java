package io.samples.meta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
public class MetaUtil {

    private MetaUtil() {

    }

    public static MetaClass metaClass(String type, String json) {
        MetaClass metaClass = loadDefinition();

        return null;
    }

    public static MetaCollection metaCollection(String type, String json) {
        return null;
    }

    private static void handleProperties(MetaClass metaClass, MetaProperty metaProperty) {
        // handle Integer type
        if(metaProperty.getType().equals(Integer.class.getName())) {
            metaProperty.getName();


        }

        if(metaProperty.getType().equals(Long.class.getName())) {

        }

        if(metaProperty.getType().equals(String.class.getName())) {

        }

        if(metaProperty.getType().equals(BigDecimal.class.getName())) {

        }

        if(metaProperty.getType().equals(MetaClass.class.getName())) {

        }

        if(metaProperty.getType().equals(MetaCollection.class.getName())) {

        }


    }



    private static MetaClass loadDefinition() {
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

    private static MetaClass handlePath(MetaClass metaClass, MetaProperty metaProperty) {
//        JsonPath.parse("").r


        return null;
    }



}
