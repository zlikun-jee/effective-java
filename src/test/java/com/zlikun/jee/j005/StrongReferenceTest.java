package com.zlikun.jee.j005;

import org.junit.Test;

/**
 * 强引用测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/3 18:34
 */
public class StrongReferenceTest {

    /**
     * 运行时在VM参数中增加：
     * -XX:+PrintGCDetails，打印GC日志，观察对象GC情况
     * -XX:+PrintGCTimeStamps，打印此次GC距离JVM开始运行的时间差
     * 设置JVM最大内存，用于测试JVM内存满时强引用是否会被回收
     * -Xmx5M
     * <p>
     * -ea -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx5m
     */
    @Test
    public void test() {

        // 通过new的方式生成一个强引用
        Object obj = new Object();

        // java.lang.Object@5702b3b1
        System.out.println(obj);

        // 执行GC
        System.gc();

        // 执行GC后，强引用不会被回收
        // java.lang.Object@5702b3b1
        System.out.println(obj);

        // 分配一块大内存(5M)，此时JVM内存耗尽，实际会抛出OOM错误，这里捕获掉，观察软引用引用的对象是否被回收
        // java.lang.OutOfMemoryError: Java heap space
        try {
            byte[] buf = new byte[5 * 1024 * 1024];
        } catch (Throwable t) {
            System.out.println(t.getMessage());
        }

        // 即使内存溢出，也不回收强引用对象
        // java.lang.Object@5702b3b1
        System.out.println(obj);

    }

}
