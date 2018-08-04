package com.zlikun.jee.j006;

import org.junit.Test;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 12:43
 */
public class ToStringHelperTest {

    @Test
    public void genericToString() throws Exception {
        String text = ToStringHelper.genericToString(new Employee("Ashe", 180000L));
        // com.zlikun.jee.j006.Employee : {salary = 180000}
        System.out.println(text);

        text = ToStringHelper.genericToString2(new Employee("Peter", 240000L));
        // com.zlikun.jee.j006.Employee : {name = Peter, salary = 240000}
        System.out.println(text);
    }

}