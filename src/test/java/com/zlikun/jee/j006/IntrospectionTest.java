package com.zlikun.jee.j006;

import org.junit.Test;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Enumeration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * 自省API测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 11:51
 */
public class IntrospectionTest {

    @Test
    public void testBeanDescriptor() {
        BeanDescriptor descriptor = new BeanDescriptor(Employee.class);

        assertEquals(Employee.class, descriptor.getBeanClass());
        assertEquals(null, descriptor.getCustomizerClass());
        assertEquals("Employee", descriptor.getDisplayName());
        assertEquals("Employee", descriptor.getName());
        assertEquals("Employee", descriptor.getShortDescription());
        assertFalse(descriptor.isHidden());
        System.out.println(descriptor.hashCode());
        Enumeration<String> enums = descriptor.attributeNames();
        while (enums.hasMoreElements()) {
            System.out.println(enums.nextElement());
        }
    }

    @Test
    public void testPropertyDescriptor() throws IntrospectionException, InvocationTargetException, IllegalAccessException {


        // PropertyDescriptor 类表示JavaBean类通过存储器导出一个属性
        PropertyDescriptor descriptor = new PropertyDescriptor("name", Employee.class);

        // name
        System.out.println(descriptor.getName());
        // name
        System.out.println(descriptor.getDisplayName());
        // class java.lang.String
        System.out.println(descriptor.getPropertyType());
        // public java.lang.String com.zlikun.jee.j006.Person.getName()
        System.out.println(descriptor.getReadMethod());
        // public void com.zlikun.jee.j006.Person.setName(java.lang.String)
        System.out.println(descriptor.getWriteMethod());
        // 541214437
        System.out.println(descriptor.hashCode());
//        descriptor.setReadMethod(null);
//        descriptor.setWriteMethod(null);


        // 传入一个实例
        Employee employee = new Employee("Ashe", 180000L);

        // 操作类属性
        // setter
        Method writeMethod = descriptor.getWriteMethod();
        // 如果setter是不可见的，设置其可见性
        writeMethod.setAccessible(true);
        // 执行setter方法
        writeMethod.invoke(employee, "Peter");

        // getter
        Method readMethod = descriptor.getReadMethod();
        readMethod.setAccessible(true);
        Object result = readMethod.invoke(employee);
        // 测试其返回结果是否与类实例一致
        assertEquals(result, employee.getName());

    }

    @Test
    public void testBeanInfo() throws IntrospectionException {

        // 这是一个工具类，用于获取Bean信息实例
        BeanInfo info = Introspector.getBeanInfo(Employee.class);

        BeanDescriptor beanDescriptor = info.getBeanDescriptor();
        System.out.println(beanDescriptor);

        PropertyDescriptor[] propertyDescriptors = info.getPropertyDescriptors();
        System.out.println(propertyDescriptors);

        MethodDescriptor[] methodDescriptors = info.getMethodDescriptors();
        System.out.println(methodDescriptors);

    }


}
