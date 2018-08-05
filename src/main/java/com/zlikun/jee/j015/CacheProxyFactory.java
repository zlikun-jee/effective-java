package com.zlikun.jee.j015;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 11:27
 */
public class CacheProxyFactory {

    /**
     * 仅作测试，这里不考虑并发情况
     */
    private static final HashMap<String, Object> CACHE_STORAGE = new HashMap<>();

    public static final <T> T createProxyInstance(T target) {

        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new CacheInvocationHandler(target));
    }

    private static class CacheInvocationHandler<T> implements InvocationHandler {

        private final T target;

        public CacheInvocationHandler(T target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object r = null;
            // 从target和method中提取注解信息
            Cacheable cacheable = method.getAnnotation(Cacheable.class);
            CacheEvict cacheEvict = method.getAnnotation(CacheEvict.class);
            if (cacheable != null) {
                r = cache(cacheable, method, args);
            } else if (cacheEvict != null) {
                r = remove(cacheEvict, method, args);
            } else {
                r = method.invoke(target, args);
            }

            return r;
        }

        /**
         * 处理@Cacheable注解
         *
         * @param cacheable
         * @param method
         * @param args
         * @return
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        private Object cache(Cacheable cacheable, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            Object r = null;
            // 解释该注解里的配置项
            String prefix = cacheable.prefix();
            if (prefix.length() == 0) {
                prefix = cacheable.value();
            }
            // 当设置了缓存键
            if (prefix.length() > 0) {
                // 1. 继续取出version等信息，这里简化处理，忽略这两项
                int version = cacheable.version();
                // 2. 设置了缓存键，所以将方法执行结果缓存（如果缓存中未命中）
                String key = String.format("%s:%d:%d", prefix, 10086, version);
                if (CACHE_STORAGE.containsKey(key)) {
                    r = CACHE_STORAGE.get(key);
                } else {
                    r = method.invoke(target, args);
                    CACHE_STORAGE.put(key, r);
                }
            } else {
                // 应该抛出异常（使用该注解，必须配置value或prefix属性）
            }
            return r;
        }

        /**
         * 处理@CacheEvict注解
         *
         * @param cacheEvict
         * @param method
         * @param args
         * @return
         * @throws InvocationTargetException
         * @throws IllegalAccessException
         */
        private Object remove(CacheEvict cacheEvict, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
            // 解释该注解里的配置项
            String prefix = cacheEvict.prefix();
            if (prefix.length() == 0) {
                prefix = cacheEvict.value();
            }
            if (prefix.length() > 0) {
                int version = cacheEvict.version();
                CACHE_STORAGE.remove(String.format("%s:%d:%d", prefix, 10086, version));
            } else {
                // 应该抛出异常（使用该注解，必须配置value或prefix属性）
            }
            return method.invoke(target, args);
        }

    }
}
