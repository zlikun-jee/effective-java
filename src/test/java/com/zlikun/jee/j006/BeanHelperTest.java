package com.zlikun.jee.j006;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 13:10
 */
public class BeanHelperTest {
    @Test
    public void copyProperties() throws Exception {

        Employee a = new Employee("Ashe", 120000L);
        Employee b = new Employee();

        // 复制属性
        BeanHelper.copyProperties(a, b);

        // 测试效果
        assertEquals("Ashe", b.getName());

    }

}