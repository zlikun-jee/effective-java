package com.zlikun.jee.j015;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 11:02
 */
public class UserServiceTest {

    private UserService service;

    @Before
    public void init() {
        service = new UserServiceImpl();
    }

    @Test
    public void get() throws Exception {
        long time = System.currentTimeMillis();
        String name = service.get(10086L);
        System.out.println(String.format("程序执行耗时：%d毫秒", System.currentTimeMillis() - time));
        assertEquals("user-10086", name);
    }

    /**
     * 下面是手动实现注解使用过程（不具备实用价值，后面会演示使用动态代理实现）
     *
     * @throws NoSuchMethodException
     */
    @Test
    public void cache() throws NoSuchMethodException {
        // 假设HashMap是我们的缓存
        HashMap<String, Object> cache = new HashMap<>();

        // 假设我们调用是像下面这样的
        // String name = service.get(10086L);
        String name = null;

        // 使用反射获取方法上的注解
        Method method = service.getClass().getDeclaredMethod("get", long.class);
        Cacheable cacheable = method.getAnnotation(Cacheable.class);
        if (cacheable != null) {
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
                if (cache.containsKey(key)) {
                    name = (String) cache.get(key);
                } else {
                    name = service.get(10086L);
                    cache.put(key, name);
                }
            }

        } else {
            name = service.get(10086L);
        }

        assertEquals("user-10086", name);

    }

    /**
     * 从执行效果可以看出缓存和驱逐缓存都生效了
     */
    @Test
    public void proxy() {

        // 生成代理
        service = CacheProxyFactory.createProxyInstance(service);

        System.out.println("-- 1 --");
        long time = System.currentTimeMillis();
        String name = service.get(10086L);
        // 程序执行耗时：112毫秒
        System.out.println(String.format("程序执行耗时：%d毫秒", System.currentTimeMillis() - time));
        assertEquals("user-10086", name);

        System.out.println("-- 2 --");
        time = System.currentTimeMillis();
        name = service.get(10086L);
        // 程序执行耗时：0毫秒
        System.out.println(String.format("程序执行耗时：%d毫秒", System.currentTimeMillis() - time));
        assertEquals("user-10086", name);

        // 执行更新方法移除缓存(请忽略实际更新逻辑)
        service.update(10086L, "Peter");

        System.out.println("-- 3 --");
        time = System.currentTimeMillis();
        name = service.get(10086L);
        // 程序执行耗时：243毫秒
        System.out.println(String.format("程序执行耗时：%d毫秒", System.currentTimeMillis() - time));
        assertEquals("user-10086", name);
    }

}