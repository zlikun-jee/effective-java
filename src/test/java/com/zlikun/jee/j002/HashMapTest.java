package com.zlikun.jee.j002;

import org.junit.Ignore;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.Assert.*;

/**
 * HashMap源码解读
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 16:01
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

    /**
     * 多线程下使用HashMap测试，通过多次测试发现在添加阶段（添加阶段会先查找）和查询阶段都可能死循环
     */
    @Test @Ignore
    public void thread() {

        final Map<String, Integer> map = new HashMap<>();

        System.out.println("开始运行测试程序");
        ExecutorService exec = Executors.newFixedThreadPool(16);
        for (int i = 0; i < 1000; i++) {
            final int index = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {
                    map.put("key-" + index, index);
                }
            });
        }

        exec.shutdown();
        while (!exec.isTerminated()) ;

        System.out.printf("插入数量量：%d%n", map.size());
        System.out.println("添加数据完成，开始查询");

        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            assertNotNull(entry.getValue());
        }

        System.out.println("数据查询完成，程序退出");

    }

}
