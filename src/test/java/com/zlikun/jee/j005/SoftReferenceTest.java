package com.zlikun.jee.j005;

import org.junit.Test;

import java.lang.ref.SoftReference;

/**
 * 软引用测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/3 18:56
 */
public class SoftReferenceTest {

    /**
     * -ea -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx5m
     */
    @Test
    public void test() {

//        // 构造一个强引用
//        Object obj = new Object();
        // 将这个对象赋值给软引用
        SoftReference<Object> sr = new SoftReference<>(new Object());
//        // 删除强引用
//        obj = null;

        // soft_ref_1 = java.lang.Object@155ec9f4
        System.out.println("soft_ref_1 = " + sr.get());

        // 此时对象只有一个软件引用了，执行GC
        System.gc();

        // 说明在内存充足的情况下，GC不会回收软引用对象
        // soft_ref_2 = java.lang.Object@5a6d6fc5
        System.out.println("soft_ref_2 = " + sr.get());

        // 分配一块大内存(5M)，此时JVM内存耗尽，实际会抛出OOM错误，这里捕获掉，观察软引用引用的对象是否被回收
        // java.lang.OutOfMemoryError: Java heap space
        try {
            byte[] buf = new byte[5 * 1024 * 1024];
        } catch (Throwable t) {
            // t.printStackTrace();
        }

        // 说明JVM内存耗尽时会回收软引用对象
        // soft_ref_3 = null
        System.out.println("soft_ref_3 = " + sr.get());
    }

}
