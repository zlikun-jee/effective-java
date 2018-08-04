package com.zlikun.jee.j006;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.assertEquals;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 14:06
 */
public class GenericArrayTest {

    @Test
    public void test() {

        Employee[] employees = createArray(Employee.class, 4);
        assertEquals(4, employees.length);

        employees[0] = new Employee();
        assertEquals(Employee.class, employees[0].getClass());

    }

    /**
     * 构建一个泛型数组，
     * 我们知道在泛型对应的类型不确定之前是无法通过new来直接范围泛型数组的
     * 所以我们这里采用反射API来实现 （跟想象的有点不一样，原来Array类有提供对应方法）
     * @param type
     * @param length
     * @param <T>
     * @return
     */
    private <T> T[] createArray(Class<T> type, int length) {
        return (T[]) Array.newInstance(type, length);
    }

}
