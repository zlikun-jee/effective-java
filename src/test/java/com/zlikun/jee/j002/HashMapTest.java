package com.zlikun.jee.j002;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 阅读HashMap源码
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 17:02
 */
public class HashMapTest {

    @Test
    public void test() {

        HashMap<String, String> map = new HashMap<>();

        assertTrue(map.isEmpty());

        map.put("name", "zlikun");
        map.put("name", "Ashe");

        assertEquals(1, map.size());

        assertEquals("Ashe", map.get("name"));
        assertEquals(null, map.get("age"));

        String value = map.remove("name");
        assertEquals("Ashe", value);

        assertTrue(map.isEmpty());

        map.clear();

    }

}
