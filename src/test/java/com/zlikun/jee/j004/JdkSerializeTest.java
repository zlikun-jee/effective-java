package com.zlikun.jee.j004;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * JDK默认序列化测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 13:05
 */
public class JdkSerializeTest {

    private Data data;

    @Before
    public void init() {
        data = new Data();
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
    public void test() throws IOException, ClassNotFoundException {

        // 执行序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(baos);
        output.writeObject(data);
        baos.close();
        output.close();
        byte[] buf = baos.toByteArray();

        assertEquals(947, buf.length);

        // 执行反序列化
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        ObjectInputStream input = new ObjectInputStream(bais);
        Data data2 = (Data) input.readObject();
        bais.close();
        input.close();

        assertFalse(data == data2);
        assertEquals(data.getA(), data2.getA());
        assertEquals(data.getI(), data2.getI());
        assertEquals(data.getJ(), data2.getJ());

    }

    /**
     * 序列化、反序列化性能测试
     *
     * @throws IOException
     * @throws ClassNotFoundException
     */
    @Test
    public void performance() throws IOException, ClassNotFoundException {

        final int loop = 10_000;

        long time = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream output = new ObjectOutputStream(baos);
            output.writeObject(data);
            baos.close();
            output.close();
            byte[] buf = baos.toByteArray();

            // 执行反序列化
            ByteArrayInputStream bais = new ByteArrayInputStream(buf);
            ObjectInputStream input = new ObjectInputStream(bais);
            input.readObject();
            bais.close();
            input.close();
        }

        // loop = 10,000 -> 程序执行耗时：1037 毫秒!
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));

    }


}
