package io.samples.meta.test;

import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import io.samples.meta.MetaClass;
import io.samples.meta.MetaCollection;
import io.samples.meta.MetaProperty;
import io.samples.meta.MetaUtil;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * MetaUtil 改造验证：4 个 P0 修复 + 深拷贝 + 元模型外部化。
 *
 * @author: baoxin.zhao
 * @date: 2026/10/31
 */
public class MetaUtilTest {

    private String readJson(String path) {
        try {
            return Files.readString(new ClassPathResource(path).getFilePath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private MetaProperty findProperty(MetaClass metaClass, String name) {
        return metaClass.getProperties().stream()
                .filter(p -> name.equals(p.getName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError("属性不存在: " + name + " in " + metaClass.getName()));
    }

    @SuppressWarnings("unchecked")
    private List<MetaClass> asMetas(Object value) {
        return (List<MetaClass>) value;
    }

    @Test
    void metaClass_should_fill_nested_object_and_collection() {
        MetaClass market = MetaUtil.metaClass("Market", readJson("./222.json"));

        // 基础类型
        assertEquals(10, findProperty(market, "expensive").getValue());

        // 嵌套对象 store
        MetaClass store = (MetaClass) findProperty(market, "store").getValue();
        assertNotNull(store);

        // 集合 book：4 本
        List<MetaClass> books = asMetas(findProperty(store, "book").getValue());
        assertEquals(4, books.size());
        assertEquals("reference", findProperty(books.get(0), "category").getValue());
        assertEquals("Nigel Rees", findProperty(books.get(0), "author").getValue());
        assertEquals(8.95, findProperty(books.get(0), "price").getValue());
        // 集合元素互不共享实例
        assertEquals("fiction", findProperty(books.get(1), "category").getValue());

        // 嵌套对象 bicycle
        MetaClass bicycle = (MetaClass) findProperty(store, "bicycle").getValue();
        assertEquals("red", findProperty(bicycle, "color").getValue());
        assertEquals(3, findProperty(bicycle, "count").getValue());
    }

    @Test
    void metaClass_should_not_pollute_template_between_calls() {
        MetaClass first = MetaUtil.metaClass("Market", readJson("./222.json"));

        // 第二次调用：换一批数据
        String json2 = "{\"store\":{\"book\":[{\"category\":\"changed\",\"author\":\"A2\",\"title\":\"T2\",\"price\":1.0}],"
                + "\"bicycle\":{\"color\":\"blue\",\"price\":1.0,\"count\":1}},\"expensive\":999}";
        MetaClass second = MetaUtil.metaClass("Market", json2);

        // 第二次的结果正确
        assertEquals(999, findProperty(second, "expensive").getValue());
        MetaClass store2 = (MetaClass) findProperty(second, "store").getValue();
        List<MetaClass> books2 = asMetas(findProperty(store2, "book").getValue());
        assertEquals("changed", findProperty(books2.get(0), "category").getValue());

        // 第一次的结果必须保持原值（深拷贝，模板未被污染）
        assertEquals(10, findProperty(first, "expensive").getValue());
        MetaClass store1 = (MetaClass) findProperty(first, "store").getValue();
        List<MetaClass> books1 = asMetas(findProperty(store1, "book").getValue());
        assertEquals("reference", findProperty(books1.get(0), "category").getValue());
        assertEquals("red", findProperty((MetaClass) findProperty(store1, "bicycle").getValue(), "color").getValue());
    }

    @Test
    void metaClass_should_tolerate_missing_fields() {
        // 缺 bicycle.price / bicycle.count / book 数组为空 / expensive 显式 null
        String json = "{\"store\":{\"book\":[],\"bicycle\":{\"color\":\"blue\"}},\"expensive\":null}";
        MetaClass market = MetaUtil.metaClass("Market", json);

        MetaClass bicycle = (MetaClass) findProperty((MetaClass) findProperty(market, "store").getValue(), "bicycle").getValue();
        assertEquals("blue", findProperty(bicycle, "color").getValue());
        assertNull(findProperty(bicycle, "price").getValue());
        assertNull(findProperty(bicycle, "count").getValue());
        // 显式 null 字段
        assertNull(findProperty(market, "expensive").getValue());
    }

    @Test
    void metaClass_should_accept_qualified_name_with_default_fallback() {
        // 全限定名不再触发 split 正则陷阱，namespace 未命中回退 default
        MetaClass market = MetaUtil.metaClass("com.example.Market", readJson("./222.json"));
        assertEquals("Market", market.getName());
        assertEquals(10, findProperty(market, "expensive").getValue());
    }

    @Test
    void metaCollection_should_parse_array() {
        MetaCollection mc = MetaUtil.metaCollection("Market", readJson("./333.json"));
        assertEquals("Market", mc.getName());
        List<MetaClass> metas = mc.getMetas();
        assertNotNull(metas);
        assertEquals(2, metas.size());

        assertEquals(10, findProperty(metas.get(0), "expensive").getValue());
        assertEquals(9, findProperty(metas.get(1), "expensive").getValue());

        MetaClass store2 = (MetaClass) findProperty(metas.get(1), "store").getValue();
        List<MetaClass> books2 = asMetas(findProperty(store2, "book").getValue());
        assertEquals("reference3", findProperty(books2.get(0), "category").getValue());
    }

    @Test
    void metaCollection_should_reject_non_array_root() {
        assertThrows(IllegalArgumentException.class,
                () -> MetaUtil.metaCollection("Market", "{\"a\":1}"),
                "顶层非数组应抛出清晰异常而非 ClassCastException");
    }

    @Test
    void metaClass_should_reject_undefined_meta_class() {
        assertThrows(IllegalArgumentException.class,
                () -> MetaUtil.metaClass("NotExist", "{}"),
                "未定义元类应抛出清晰异常而非 NPE");
    }
}
