package com.zlikun.jee.j006;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 使用反射实现一个通用ToString工具类
 * 只是测试反射API，实际使用Introspection更适合（通常属性都是私有的，这样是无法获取父类中的属性了）
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 12:33
 */
public class ToStringHelper {

    public static final <T> String genericToString(T t) {
        if (t == null) return null;

        // 获取类信息
        Class<?> clazz = t.getClass();

        // 获取所有字段
        Set<Field> fields = new HashSet<>();
        fields.addAll(Arrays.stream(clazz.getDeclaredFields()).collect(Collectors.toSet()));
        fields.addAll(Arrays.stream(clazz.getFields()).collect(Collectors.toSet()));

        // 获取字段，组装字符串
        StringBuilder builder = new StringBuilder(clazz.getCanonicalName());
        builder.append(" : {");
        for (Field field : fields) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            try {
                Object value = field.get(t);
                if (value != null) {
                    builder.append(field.getName()).append(" = ")
                            .append(field.getType().isPrimitive() ? value : value.toString())
                            .append(", ");
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return builder.substring(0, builder.length() - 2) + "}";
    }

    /**
     * 使用Introspection机制实现ToString
     *
     * @param t
     * @param <T>
     * @return
     */
    public static final <T> String genericToString2(T t) {
        try {
            // 获取Bean信息
            BeanInfo info = Introspector.getBeanInfo(t.getClass());

            // 提取全部属性
            PropertyDescriptor[] descriptors = info.getPropertyDescriptors();
            // 遍历属性描述符，取得属性值
            StringBuilder builder = new StringBuilder(t.getClass().getCanonicalName());
            builder.append(" : {");

            for (PropertyDescriptor descriptor : descriptors) {
                if (descriptor.getName().equals("class")) {
                    continue;
                }
                Method method = descriptor.getReadMethod();
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                try {
                    Object value = method.invoke(t);
                    if (value != null) {
                        builder.append(descriptor.getName()).append(" = ")
                                .append(method.getReturnType().isPrimitive() ? value : value.toString())
                                .append(", ");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }

            return builder.substring(0, builder.length() - 2) + "}";
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return null;
    }

}
