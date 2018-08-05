package com.zlikun.jee.j004;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 测试Hadoop中实现的序列化机制
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 17:12
 */
public class HadoopSerializeTest {

    private Data2 data;

    @Before
    public void init() {
        data = new Data2();
        data.setA(1);
        data.setA0(1);
        data.setB(1L);
        data.setB0(1L);
        data.setC(1.0F);
        data.setC0(1.0F);
        data.setD(1.0D);
        data.setD0(1.0D);
        data.setE(Boolean.TRUE);
        data.setE0(true);
        data.setF('A');
        data.setF0('A');
        data.setG(Byte.MAX_VALUE);
        data.setG0(Byte.MAX_VALUE);
        data.setH(Short.MAX_VALUE);
        data.setH0(Short.MAX_VALUE);
        data.setI("林花谢了春红 / 太匆匆 / 无奈朝来寒雨 / 晚来风 / 胭脂泪 / 相留醉 / 几时重 / 自是人生长恨 / 水长东");
        data.setJ(new Date());
    }

    @Test
    public void test() throws IOException {

        // 序列化的数据参考 JdkSerializeTest 中的Data对象
        // 序列化、反序列化的过程都是一个字段一个字段的实现，虽然繁琐，但序列化后的大小和性能都比JDK原生序列化API强很多
        byte[] buf = data.serialize();

        // 测试序列化大小：JDK序列化后是947，这里只有204
        assertEquals(204, buf.length);

        // 执行反序列化，注意读取的顺序与写入的顺序要一致
        Data2 data2 = Data2.deserialize(buf);
        assertFalse(data == data2);
        assertEquals(data.getA(), data2.getA());
        assertEquals(data.getA0(), data2.getA0());
        // 由于浮点数在计算时会有误差，这里第三个参数用于控制误差
        assertEquals(data.getC(), data2.getC(), 0.0);
        assertEquals(data.getC0(), data2.getC0(), 0.0);
        assertEquals(data.getE(), data2.getE());
        assertEquals(data.isE0(), data2.isE0());
        assertEquals(data.getF(), data2.getF());
        assertEquals(data.getF0(), data2.getF0());
        assertEquals(data.getG(), data2.getG());
        assertEquals(data.getG0(), data2.getG0());
        assertEquals(data.getH(), data2.getH());
        assertEquals(data.getH0(), data2.getH0());
        assertEquals(data.getI(), data2.getI());
        assertEquals(data.getJ(), data2.getJ());

    }

    /**
     * 序列化、反序列化性能测试
     *
     * @throws IOException
     */
    @Test
    public void performance() throws IOException {

        final int loop = 10_000;

        long time = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            // 执行序列化
            byte[] buf = data.serialize();

            // 执行反序列化
            Data2.deserialize(buf);
        }

        // loop = 10,000 -> 程序执行耗时：75 毫秒!
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));

    }


}
