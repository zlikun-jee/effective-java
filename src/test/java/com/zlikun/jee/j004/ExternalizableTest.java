package com.zlikun.jee.j004;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/7 11:11
 */
public class ExternalizableTest {

    private Data3 data;

    @Before
    public void init() {
        data = new Data3();
        data.setId(1024);
        data.setName("Ashe");
        data.setBirthday(new Date());
    }

    @Test
    public void test() throws IOException, ClassNotFoundException {
        // 序列化
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream output = new ObjectOutputStream(baos);
        data.writeExternal(output);

        byte[] buf = baos.toByteArray();
        assertEquals(58, buf.length);

        // 反序列化
        ByteArrayInputStream bais = new ByteArrayInputStream(buf);
        ObjectInputStream input = new ObjectInputStream(bais);
        Data3 data3 = new Data3();
        data3.readExternal(input);

        // 校验
        assertFalse(data == data3);
        assertEquals(data.getId(), data3.getId());
        assertEquals(data.getName(), data3.getName());
        assertEquals(data.getBirthday(), data3.getBirthday());

    }

}
