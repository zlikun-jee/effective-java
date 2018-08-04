package com.zlikun.jee.j006;

import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射性能测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 13:24
 */
public class ReflectivePerformanceTest {

    @Test
    public void test() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        // 2,147,483,647
        int loop = Integer.MAX_VALUE;

        Employee employee = new Employee("Ashe", 120000L);

        long time = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            employee.setName("Peter");
        }
        // 不用反射程序执行耗时：5455毫秒
        System.out.printf("不用反射程序执行耗时：%d毫秒%n", System.currentTimeMillis() - time);

        time = System.currentTimeMillis();
        Class<Employee> clazz = Employee.class;
        Method method = clazz.getMethod("setName", String.class);
        for (int i = 0; i < loop; i++) {
            method.invoke(employee, "Peter");
        }
        // 不用反射程序执行耗时：9555毫秒
        System.out.printf("不用反射程序执行耗时：%d毫秒%n", System.currentTimeMillis() - time);


    }

}
