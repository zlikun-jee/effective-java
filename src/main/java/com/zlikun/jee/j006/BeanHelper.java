package com.zlikun.jee.j006;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 13:03
 */
public class BeanHelper {

    /**
     * 从Spring源码（org.springframework.beans.BeanUtils）中抄来的（去除了外部组件依赖，和不重要的参数），这里对其进行解读
     *
     * @param source
     * @param target
     * @throws Exception
     */
    public static final void copyProperties(Object source, Object target) throws Exception {


        // 获取目标类型属性描述符列表
        PropertyDescriptor[] targetPds = getPropertyDescriptors(target.getClass());

        // 遍历属性描述符列表
        for (PropertyDescriptor targetPd : targetPds) {

            // 判断其是否包含读方法（getter）
            Method writeMethod = targetPd.getWriteMethod();
            if (writeMethod != null) {
                // 根据属性名，从源类中找到对应的属性及其属性描述符
                PropertyDescriptor sourcePd = getPropertyDescriptor(source.getClass(), targetPd.getName());
                // 如果存在则进行属性复制
                if (sourcePd != null) {
                    // 获取源类型的属性读方法，判断其是否存在
                    Method readMethod = sourcePd.getReadMethod();
                    if (readMethod != null &&
                            isAssignable(writeMethod.getParameterTypes()[0], readMethod.getReturnType())) {
                        try {
                            // 判断、修改读方法可见性
                            if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
                                readMethod.setAccessible(true);
                            }
                            // 调用读方法，返回属性值
                            Object value = readMethod.invoke(source);
                            // 判断、修改写方法可见性
                            if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
                                writeMethod.setAccessible(true);
                            }
                            // 调用写方法，写入属性值
                            writeMethod.invoke(target, value);
                        } catch (Throwable ex) {
                            throw new Exception(
                                    "Could not copy property '" + targetPd.getName() + "' from source to target", ex);
                        }
                    }
                }
            }
        }
    }

    private static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String name) throws IntrospectionException {
        return new PropertyDescriptor(name, clazz);
    }

    private static PropertyDescriptor[] getPropertyDescriptors(Class<?> clazz) throws IntrospectionException {
        return Introspector.getBeanInfo(clazz).getPropertyDescriptors();
    }

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new IdentityHashMap<>(8);
    private static final Map<Class<?>, Class<?>> primitiveTypeToWrapperMap = new IdentityHashMap<>(8);

    private static boolean isAssignable(Class<?> lhsType, Class<?> rhsType) {
        if (lhsType.isAssignableFrom(rhsType)) {
            return true;
        }
        if (lhsType.isPrimitive()) {
            Class<?> resolvedPrimitive = primitiveWrapperTypeMap.get(rhsType);
            if (lhsType == resolvedPrimitive) {
                return true;
            }
        } else {
            Class<?> resolvedWrapper = primitiveTypeToWrapperMap.get(rhsType);
            if (resolvedWrapper != null && lhsType.isAssignableFrom(resolvedWrapper)) {
                return true;
            }
        }
        return false;
    }


}
