package io.samples.meta;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

/**
 * 元模型驱动的 JSON 转换器。
 *
 * <p>元模型定义外部化在 classpath:/meta-model.json 中，类加载时一次性载入内存缓存；
 * 解析时以深拷贝出的元类副本为模板，按 JSON 数据递归填充属性值，模板本身保持纯净、可复用、可并发。</p>
 *
 * <p>属性类别约定（对应 MetaProperty.type 全限定名）：
 * <ul>
 *     <li>{@link MetaClass} 全限定名 = 嵌套对象，MetaProperty.typeName 指向目标元类名；</li>
 *     <li>{@link MetaCollection} 全限定名 = 对象集合，MetaProperty.typeName 指向元素元类名；</li>
 *     <li>其余为基础类型（Integer/Long/Double/String/BigDecimal 等）。</li>
 * </ul>
 * </p>
 *
 * @author: baoxin.zhao
 * @date: 2026/2/26
 */
public class MetaUtil {
    private static final String META_MODEL_RESOURCE = "/meta-model.json";
    private static final String DEFAULT_NAMESPACE = "default";
    private static final ConcurrentHashMap<String, MetaClass> MAPPING = new ConcurrentHashMap<>();

    static {
        loadMetaModel();
    }

    private MetaUtil() {
    }

    /**
     * 将单个 JSON 对象按元模型转换为元类对象树。
     *
     * @param type 元类名，支持简单名（Market）与命名空间限定名（com.foo.Market，namespace 未命中时回退 default）
     * @param json JSON 对象文本
     */
    public static MetaClass metaClass(String type, String json) {
        NameParts parts = parseType(type);
        MetaClass template = resolveClass(parts.namespace, parts.name);
        MetaClass metaClass = deepCopy(template);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        handleMetaClass(metaClass, document);
        return metaClass;
    }

    /**
     * 将 JSON 对象数组按元模型转换为元集合。
     *
     * @param type 元素元类名
     * @param json JSON 数组文本（顶层必须是数组，否则抛出 IllegalArgumentException）
     */
    public static MetaCollection metaCollection(String type, String json) {
        NameParts parts = parseType(type);
        MetaClass template = resolveClass(parts.namespace, parts.name);
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
        MetaCollection mc = new MetaCollection();
        mc.setName(parts.name);
        List<Map<String, Object>> values = castList(document, parts.name);
        List<MetaClass> mcs = new ArrayList<>(values.size());
        mc.setMetas(mcs);
        for (Map<String, Object> v : values) {
            MetaClass m = deepCopy(template);
            mcs.add(m);
            handleMetaClass(m, v);
        }
        return mc;
    }

    // ------------------------------------------------------------------
    // 递归填充
    // ------------------------------------------------------------------

    private static void handleMetaClass(MetaClass metaClass, Object document) {
        List<MetaProperty> properties = metaClass.getProperties();
        if (properties == null) {
            return;
        }
        for (MetaProperty metaProperty : properties) {
            String name = metaProperty.getName();
            Object value = readValue(document, name);
            if (value == null) {
                metaProperty.setValue(null);
                continue;
            }
            if (MetaClass.class.getName().equals(metaProperty.getType())) {
                MetaClass mc = deepCopy(resolveType(metaProperty));
                metaProperty.setValue(mc);
                handleMetaClass(mc, value);
                continue;
            }
            if (MetaCollection.class.getName().equals(metaProperty.getType())) {
                List<Map<String, Object>> vs = castList(value, name);
                List<MetaClass> mcs = new ArrayList<>(vs.size());
                for (Map<String, Object> v : vs) {
                    MetaClass m = deepCopy(resolveType(metaProperty));
                    mcs.add(m);
                    handleMetaClass(m, v);
                }
                metaProperty.setValue(mcs);
                continue;
            }
            handleProperties(value, metaProperty);
        }
    }

    private static void handleProperties(Object value, MetaProperty metaProperty) {
        String type = metaProperty.getType();
        if (Integer.class.getName().equals(type)) {
            metaProperty.setValue(value instanceof Integer ? value : Integer.valueOf(value.toString()));
            return;
        }
        if (Long.class.getName().equals(type)) {
            metaProperty.setValue(value instanceof Long ? value : Long.valueOf(value.toString()));
            return;
        }
        if (Double.class.getName().equals(type)) {
            metaProperty.setValue(value instanceof Double ? value : Double.valueOf(value.toString()));
            return;
        }
        if (String.class.getName().equals(type)) {
            metaProperty.setValue(value);
            return;
        }
        if (BigDecimal.class.getName().equals(type)) {
            metaProperty.setValue(new BigDecimal(value.toString()));
            return;
        }
        // 未注册的基础类型：宽容处理，原样保留，避免静默丢值
        metaProperty.setValue(value);
    }

    /**
     * 读取 JSON 中指定字段；字段缺失时返回 null 而非抛 PathNotFoundException。
     */
    private static Object readValue(Object document, String name) {
        try {
            return JsonPath.read(document, "$.".concat(name));
        } catch (PathNotFoundException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private static List<Map<String, Object>> castList(Object value, String context) {
        if (!(value instanceof List)) {
            throw new IllegalArgumentException("字段不是 JSON 数组，无法解析为元集合: " + context);
        }
        List<?> raw = (List<?>) value;
        for (Object item : raw) {
            if (!(item instanceof Map)) {
                throw new IllegalArgumentException("集合元素不是 JSON 对象: " + context + " -> " + item);
            }
        }
        return (List<Map<String, Object>>) raw;
    }

    // ------------------------------------------------------------------
    // 元模型加载与模板解析
    // ------------------------------------------------------------------

    private static void loadMetaModel() {
        try (InputStream in = MetaUtil.class.getResourceAsStream(META_MODEL_RESOURCE)) {
            if (in == null) {
                throw new IllegalStateException("元模型资源未找到: " + META_MODEL_RESOURCE);
            }
            String json = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
            List<Map<String, Object>> classes = castList(((Map<String, Object>) document).get("metaClasses"), "metaClasses");
            for (Map<String, Object> c : classes) {
                MetaClass mc = new MetaClass();
                mc.setName(asString(c.get("name"), "metaClass.name"));
                mc.setNamespace(asString(c.get("namespace"), "metaClass.namespace"));
                String metaTypeCode = asString(c.get("metaType"), "metaClass.metaType");
                if (metaTypeCode != null) {
                    mc.setMetaType(MetaType.of(metaTypeCode));
                }
                List<MetaProperty> properties = new ArrayList<>();
                Object rawProps = c.get("properties");
                if (rawProps != null) {
                    List<Map<String, Object>> props = castList(rawProps, mc.getName() + ".properties");
                    for (Map<String, Object> p : props) {
                        MetaProperty mp = new MetaProperty();
                        mp.setName(asString(p.get("name"), mc.getName() + ".property.name"));
                        mp.setType(asString(p.get("type"), mc.getName() + ".property.type"));
                        mp.setTypeName(asString(p.get("typeName"), null));
                        properties.add(mp);
                    }
                }
                mc.setProperties(properties);
                String key = keyOf(mc.getNamespace(), mc.getName());
                if (MAPPING.putIfAbsent(key, mc) != null) {
                    throw new IllegalStateException("元模型重复定义: " + key);
                }
            }
        } catch (IOException e) {
            ExceptionInInitializerError error = new ExceptionInInitializerError("加载元模型失败: " + META_MODEL_RESOURCE);
            error.initCause(e);
            throw error;
        }
    }

    private static String asString(Object value, String field) {
        if (value == null) {
            if (field != null) {
                throw new IllegalArgumentException("元模型配置缺失字段: " + field);
            }
            return null;
        }
        return String.valueOf(value);
    }

    /**
     * 解析元类引用，返回模板（共享、不可变语义，调用方必须深拷贝后再填充）。
     */
    private static MetaClass resolveType(MetaProperty metaProperty) {
        String typeName = metaProperty.getTypeName();
        if (typeName == null || typeName.isEmpty()) {
            throw new IllegalArgumentException("嵌套属性缺少 typeName，无法解析目标元类: " + metaProperty.getName());
        }
        NameParts parts = parseType(typeName);
        return resolveClass(parts.namespace, parts.name);
    }

    private static MetaClass resolveClass(String namespace, String name) {
        MetaClass template = MAPPING.get(keyOf(namespace, name));
        if (template == null && !DEFAULT_NAMESPACE.equals(namespace)) {
            // 宽容匹配：命名空间未命中时回退 default 命名空间
            template = MAPPING.get(keyOf(DEFAULT_NAMESPACE, name));
        }
        if (template == null) {
            throw new IllegalArgumentException("未定义的元类: " + keyOf(namespace, name));
        }
        return template;
    }

    private static String keyOf(String namespace, String name) {
        return namespace.concat(".").concat(name);
    }

    /**
     * 按最后一个 '.' 切分命名空间与类名，避免 String.split 的正则陷阱。
     */
    private static NameParts parseType(String type) {
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("元类名不能为空");
        }
        int idx = type.lastIndexOf('.');
        if (idx < 0) {
            return new NameParts(DEFAULT_NAMESPACE, type);
        }
        if (idx == type.length() - 1) {
            throw new IllegalArgumentException("非法元类名（以 . 结尾）: " + type);
        }
        return new NameParts(type.substring(0, idx), type.substring(idx + 1));
    }

    // ------------------------------------------------------------------
    // 深拷贝：模板 -> 实例
    // ------------------------------------------------------------------

    /**
     * 深拷贝元类模板。properties 列表与每个 MetaProperty 均为新对象，
     * 保证实例填值不会写回模板缓存；value 属于运行时数据，不参与拷贝。
     */
    private static MetaClass deepCopy(MetaClass template) {
        MetaClass copy = new MetaClass();
        copy.setName(template.getName());
        copy.setNamespace(template.getNamespace());
        copy.setMetaType(template.getMetaType());
        List<MetaProperty> properties = template.getProperties();
        if (properties == null) {
            copy.setProperties(null);
            return copy;
        }
        List<MetaProperty> copied = new ArrayList<>(properties.size());
        for (MetaProperty p : properties) {
            MetaProperty np = new MetaProperty();
            np.setName(p.getName());
            np.setType(p.getType());
            np.setTypeName(p.getTypeName());
            copied.add(np);
        }
        copy.setProperties(copied);
        return copy;
    }

    private static final class NameParts {
        private final String namespace;
        private final String name;

        private NameParts(String namespace, String name) {
            this.namespace = namespace;
            this.name = name;
        }
    }
}
