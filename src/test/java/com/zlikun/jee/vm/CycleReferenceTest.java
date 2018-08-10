package com.zlikun.jee.vm;

import org.junit.Test;

/**
 * JVM中循环引用的GC测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/10 16:13
 */
public class CycleReferenceTest {

    static class Data {
        Object instance;
        // 64MB数据，目的是为了让内存开销看得更清楚
        byte[] data = new byte[64 * 1 << 20];
    }

    /**
     * 在JVM中增加：-verbose:gc -XX:+PrintGCDetails -XX:SurvivorRatio=8
     */
    @Test
    public void test() {

        // 构造两个对象
        Data m = new Data();
        Data n = new Data();
        // 它们相互引用对方
        m.instance = n;
        n.instance = m;

        // 删除引用
        m = null;
        n = null;

        // 手动GC一次
        System.gc();

        // 下面是GC日志，可以看出并没有因为相互引用就不GC（如果是用计数器实现则会存在无法GC的问题）
        //  [ParOldGen: 131080K->1295K(175104K)]，回收129785K内存，差不多是两个对象中的data字段的内存占用
        /* ------------------------------------------------------------------------
        [GC (System.gc()) [PSYoungGen: 8365K->1480K(78336K)] 139437K->132560K(253440K), 0.0023310 secs] [Times: user=0.09 sys=0.00, real=0.01 secs]
        [Full GC (System.gc()) [PSYoungGen: 1480K->0K(78336K)] [ParOldGen: 131080K->1295K(175104K)] 132560K->1295K(253440K), [Metaspace: 4883K->4883K(1056768K)], 0.0091159 secs] [Times: user=0.00 sys=0.00, real=0.01 secs]
        Heap
         PSYoungGen      total 78336K, used 1392K [0x000000076af80000, 0x0000000770480000, 0x00000007c0000000)
          eden space 69632K, 2% used [0x000000076af80000,0x000000076b0dc2b0,0x000000076f380000)
          from space 8704K, 0% used [0x000000076f380000,0x000000076f380000,0x000000076fc00000)
          to   space 8704K, 0% used [0x000000076fc00000,0x000000076fc00000,0x0000000770480000)
         ParOldGen       total 175104K, used 1295K [0x00000006c0e00000, 0x00000006cb900000, 0x000000076af80000)
          object space 175104K, 0% used [0x00000006c0e00000,0x00000006c0f43f58,0x00000006cb900000)
         Metaspace       used 4898K, capacity 5264K, committed 5504K, reserved 1056768K
          class space    used 569K, capacity 627K, committed 640K, reserved 1048576K
        ------------------------------------------------------------------------ */

    }

}
