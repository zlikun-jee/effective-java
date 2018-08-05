package com.zlikun.jee.j004;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 测试Hessian序列化
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 18:27
 */
public class HessianSerializeTest {

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
    public void test() throws IOException {

        // 执行序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output hessian2Output = new Hessian2Output(baos);
        hessian2Output.writeObject(data);
        hessian2Output.close();
        // 获取字节数组前，必须先关闭Hessian2Output，否则取得字节数组长度为0(原因暂不清楚)
        byte[] buf = baos.toByteArray();
        baos.close();
        // 测试断言
        Assert.assertNotNull(buf);
        Assert.assertEquals(373, buf.length);
        System.out.println(new String(buf));

        // 执行反序列化
        Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(buf));
        Data data2 = (Data) hessian2Input.readObject();
        hessian2Input.close();
        // 测试断言
        assertFalse(data == data2);
        assertEquals(data.getA(), data2.getA());
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
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Hessian2Output hessian2Output = new Hessian2Output(baos);
            hessian2Output.writeObject(data);
            hessian2Output.close();
            byte[] buf = baos.toByteArray();

            // 执行反序列化
            Hessian2Input hessian2Input = new Hessian2Input(new ByteArrayInputStream(buf));
            hessian2Input.readObject();
            hessian2Input.close();
        }

        // loop = 10,000 -> 程序执行耗时：300 毫秒!
        System.out.println(String.format("程序执行耗时：%d 毫秒!", System.currentTimeMillis() - time));

    }

}
