package com.zlikun.jee.j001;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 11:58
 */
public class EmployeeTest {

    @Test
    public void equals_1() {

        // 有两个Employee对象，我们假定如果姓名与薪资相等即认为两个对象相等
        Employee x = new Employee();
        x.setName("Jane");
        x.setSalary(3500.0);
        Employee y = new Employee();
        y.setName("Jane");
        y.setSalary(3500.0);

        // 此时我们没有重写equals方法，此时使用的equals方法由Object提供，只简单比较两个对象是否相同
        assertTrue(x.equals(x));
        assertTrue(x.equals(y));

    }

    @Test
    public void equals_2() {

        Employee x = new Employee();
        x.setName("Jane");
        x.setSalary(3500.0);
        Manager y = new Manager();
        y.setName("Jane");
        y.setSalary(3500.0);

        assertTrue(x.equals(y));
        assertTrue(y.equals(x));

    }

    @Test
    public void hashCode_1() {

        Employee x = new Employee();
        x.setName("Jane");
        x.setSalary(3500.0);
        Employee y = new Employee();
        y.setName("Jane");
        y.setSalary(3500.0);

        // HashSet底层由HashMap实现
        HashSet<Employee> sets = new HashSet<>();
        sets.add(x);
        sets.add(y);
        assertEquals(1, sets.size());

    }

}