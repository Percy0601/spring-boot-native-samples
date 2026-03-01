package io.samples.meta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;
import org.springframework.beans.BeanUtils;

/**
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
public class MetaUtil {
    private static ConcurrentHashMap<String, MetaClass> mapping = new ConcurrentHashMap<>();

    private MetaUtil() {

    }

    public static MetaClass metaClass(String type, String json) {
        String name = "";
        String namespace = "";
        if(type.contains(".")) {
            String[] parts = type.split(".");
            name = parts[parts.length -1];
        } else {
            name = type;
            namespace = "default";
        }

        MetaClass metaClass = loadClass(namespace, name);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        handleMetaClass(metaClass, document);
        return metaClass;
    }

    public static MetaCollection metaCollection(String type, String json) {
        return null;
    }

    private static void handleMetaClass(MetaClass metaClass, Object document) {
        List<MetaProperty> properties = metaClass.getProperties();
        for(MetaProperty metaProperty: properties) {
            if(metaProperty.getType().equals(MetaClass.class.getName())) {
                String name = metaProperty.getName();
                String namePath = "$.".concat(name);
                Object value = JsonPath.read(document, namePath);
                if(null == value) {
                    continue;
                }
                String typeName = metaProperty.getTypeName();
                String typeNameNamespace = "";
                if(typeName.contains(".")) {
                    String[] parts = typeName.split(".");
                    typeName = parts[parts.length -1];
                } else {
                    typeNameNamespace = "default";
                }
                MetaClass mc = loadClass(typeNameNamespace, typeName);
                metaProperty.setValue(mc);

                handleMetaClass(mc, value);
                continue;
            }
            if(metaProperty.getType().equals(MetaCollection.class.getName())) {
                String name = metaProperty.getName();
                String namePath = "$.".concat(name);
                Object value = JsonPath.read(document, namePath);
                if(null == value) {
                    continue;
                }

                String typeName = metaProperty.getTypeName();
                String typeNameNamespace = "";
                if(typeName.contains(".")) {
                    String[] parts = typeName.split(".");
                    typeName = parts[parts.length -1];
                } else {
                    typeNameNamespace = "default";
                }
                MetaClass mc = loadClass(typeNameNamespace, typeName);

                List<Map<String, Object>> vs = (List<Map<String, Object>>)value;
                List<MetaClass> mcs = new ArrayList<>();

                for(Map<String, Object> v: vs) {
                    MetaClass m = new MetaClass();
                    BeanUtils.copyProperties(mc, m);
                    mcs.add(m);
                }
                metaProperty.setValue(mcs);

                int i = 0;
                for(MetaClass m: mcs) {
                    handleMetaClass(m, vs.get(i++));
                }

                continue;
            }
            handleProperties(document, metaProperty);
        }

    }

    private static void handleProperties(Object document, MetaProperty metaProperty) {
        // handle Integer type
        String name = metaProperty.getName();
        String namePath = "$.".concat(name);
        Object value = JsonPath.read(document, namePath);
        if(null == value) {
            metaProperty.setValue(null);
            return;
        }

        if(metaProperty.getType().equals(Integer.class.getName())) {
            if(!(value instanceof Integer)) {
                metaProperty.setValue(Integer.valueOf(value.toString()));
            }
            metaProperty.setValue(value);
            return;
        }

        if(metaProperty.getType().equals(Long.class.getName())) {
            if(!(value instanceof Long)) {
                metaProperty.setValue(Long.valueOf(value.toString()));
            }
            metaProperty.setValue(value);
            return;
        }

        if(metaProperty.getType().equals(Double.class.getName())) {
            if(!(value instanceof Double)) {
                metaProperty.setValue(Double.valueOf(value.toString()));
            }
            metaProperty.setValue(value);
            return;
        }

        if(metaProperty.getType().equals(String.class.getName())) {
            metaProperty.setValue(value);
            return;
        }

        if(metaProperty.getType().equals(BigDecimal.class.getName())) {
            metaProperty.setValue(new BigDecimal(value.toString()));
            return;
        }
//
//        if(metaProperty.getType().equals(MetaClass.class.getName())) {
//
//        }
//
//        if(metaProperty.getType().equals(MetaCollection.class.getName())) {
//
//        }


    }



    private static MetaClass loadClass(String namespace, String name) {
        String fullPath = namespace.concat(".")
                .concat(name);
        if(mapping.contains(fullPath)) {
            return mapping.get(fullPath);
        }
        MetaClass metaClass = new MetaClass();
        mapping.put(fullPath, metaClass);
        if(name.equals("Market")) {
            metaClass.setName("Market");
            metaClass.setNamespace("default");
            List<MetaProperty> properties = new ArrayList<>();
            MetaProperty expensiveProperty = new MetaProperty();
            expensiveProperty.setName("expensive");
            expensiveProperty.setType(Integer.class.getName());
            properties.add(expensiveProperty);

            MetaProperty bicycleProperty = new MetaProperty();
            bicycleProperty.setName("store");
            bicycleProperty.setType(MetaClass.class.getName());
            bicycleProperty.setTypeName("Store");
            properties.add(bicycleProperty);

            metaClass.setProperties(properties);
            return metaClass;
        }

        if(name.equals("Book")) {
            metaClass.setName("Book");
            metaClass.setNamespace("default");
//            "category": "reference",
//                    "author": "Nigel Rees",
//                    "title": "Sayings of the Century",
//                    "price": 8.95
            List<MetaProperty> properties = new ArrayList<>();
            MetaProperty property1 = new MetaProperty();
            property1.setName("category");
            property1.setType(String.class.getName());
            properties.add(property1);

            MetaProperty property2 = new MetaProperty();
            property2.setName("author");
            property2.setType(String.class.getName());
            properties.add(property2);

            MetaProperty property3 = new MetaProperty();
            property3.setName("title");
            property3.setType(String.class.getName());
            properties.add(property3);

            MetaProperty property4 = new MetaProperty();
            property4.setName("price");
            property4.setType(Double.class.getName());
            properties.add(property4);

            metaClass.setProperties(properties);
            return metaClass;
        }

        if(name.equals("Store")) {
            metaClass.setName("Store");
            metaClass.setNamespace("default");
//            "color": "red",
//                    "price": 19.958876567765,
//                    "count": 3
            List<MetaProperty> properties = new ArrayList<>();
            MetaProperty property1 = new MetaProperty();
            property1.setName("book");
            property1.setType(MetaCollection.class.getName());
            property1.setTypeName("Book");
            properties.add(property1);

            MetaProperty property2 = new MetaProperty();
            property2.setName("bicycle");
            property2.setTypeName("Bicycle");
            property2.setType(MetaClass.class.getName());
            properties.add(property2);

            metaClass.setProperties(properties);
            return metaClass;
        }

        if(name.equals("Bicycle")) {
            metaClass.setName("Bicycle");
            metaClass.setNamespace("default");
//            "color": "red",
//                    "price": 19.958876567765,
//                    "count": 3
            List<MetaProperty> properties = new ArrayList<>();
            MetaProperty property1 = new MetaProperty();
            property1.setName("color");
            property1.setType(String.class.getName());
            properties.add(property1);

            MetaProperty property2 = new MetaProperty();
            property2.setName("price");
            property2.setType(Double.class.getName());
            properties.add(property2);

            MetaProperty property3 = new MetaProperty();
            property3.setName("count");
            property3.setType(Integer.class.getName());
            properties.add(property3);

            metaClass.setProperties(properties);
            return metaClass;
        }

        return null;
    }

    private static MetaClass handlePath(MetaClass metaClass, MetaProperty metaProperty) {
//        JsonPath.parse("").r


        return null;
    }



}
