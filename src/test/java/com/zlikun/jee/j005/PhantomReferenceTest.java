package com.zlikun.jee.j005;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;

/**
 * 虚引用测试
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/3 19:19
 */
public class PhantomReferenceTest {

    static class MyType {
        @Override
        protected void finalize() throws Throwable {
            super.finalize();
            System.out.println("--finalize--");
        }

        @Override
        public String toString() {
            return "--string--";
        }
    }

    @Test
    public void test() throws InterruptedException {

        // 构造虚引用必须传入引用队列
        ReferenceQueue<MyType> queue = new ReferenceQueue<>();
        PhantomReference<MyType> ref = new PhantomReference<>(new MyType(), queue);

        // 这种引用跟没有引用几乎没有分别，任何时候通过get方法取得的都是空值
        // phantom_ref_1 = null
        System.out.println("phantom_ref_1 = " + ref.get());

        // 执行GC，第一次：通过观察输出日志发现，JVM找到了垃圾对象，并调用finalize方法回收内存，但没有立即加入回收队列
        System.gc();
        System.out.println("GC - 1");
        // --finalize--
        // null
        System.out.println(queue.remove(200L));

        // 执行GC，第二次，通过观察输出日志发现，第二次GC后JVM才真正清除垃圾对象，并将其加入引用队列（所以才能从队列中弹出值）
        System.gc();
        System.out.println("GC - 2");
        // java.lang.ref.PhantomReference@32a1bec0
        System.out.println(queue.remove(200L));

        // 执行GC，第三次，后面的GC与该对象已经无关
        System.gc();
        System.out.println("GC - 3");
        // null
        System.out.println(queue.remove(200L));

    }

}
