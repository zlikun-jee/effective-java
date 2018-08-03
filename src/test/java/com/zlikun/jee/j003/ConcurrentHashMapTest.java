package com.zlikun.jee.j003;

import org.junit.Test;

import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * ConcurrentHashMap测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/3 12:32
 */
public class ConcurrentHashMapTest {

    @Test
    public void test() {

        ConcurrentHashMap map = new ConcurrentHashMap();

        assertTrue(map.isEmpty());

        // 添加元素
        map.put("name", "zlikun");
        // 当不存在时添加元素（存在则忽略）
        map.putIfAbsent("name", "Ashe");

        assertEquals(1, map.size());

        // 判断键或值是否存在
        assertTrue(map.containsKey("name"));
        assertTrue(map.containsValue("zlikun"));
        // containsValue 方法的别名
        assertTrue(map.contains("zlikun"));

        // 获取
        assertEquals("zlikun", map.get("name"));

        // 替换并返回旧值
        assertEquals("zlikun", map.replace("name", "Jane"));

        // 删除并返回删除元素值
        assertEquals("Jane", map.remove("name"));

        map.clear();


    }

}
