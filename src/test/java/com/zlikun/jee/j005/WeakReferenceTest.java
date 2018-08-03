package com.zlikun.jee.j005;

import org.junit.Test;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * 弱引用测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/3 18:56
 */
public class WeakReferenceTest {

    /**
     * -ea -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xmx5m
     */
    @Test
    public void test() throws InterruptedException {

        ReferenceQueue<Object> queue = new ReferenceQueue<>();

        // 构造一个弱引用
//        WeakReference<Object> ref = new WeakReference<>(new Object());
        WeakReference<Object> ref = new WeakReference<>(new Object(), queue);

        // weak_ref_1 = java.lang.Object@5702b3b1
        System.out.println("weak_ref_1 = " + ref.get());
        // null
        System.out.println(queue.remove(200L));

        // 此时对象只有一个软件引用了，执行GC
        System.gc();

        // 即使在内存充足的情况下，只要发生GC就会回收弱引用对象
        // weak_ref_2 = null
        System.out.println("weak_ref_2 = " + ref.get());
        // 当垃圾对象被回收后，弱引用被加入到引用队列中
        // java.lang.ref.WeakReference@22927a81
        System.out.println(queue.remove(200L));

        System.gc();
        // null
        System.out.println(queue.remove(200L));

    }

}
