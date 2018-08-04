package com.zlikun.jee.j007;

import java.lang.reflect.Proxy;

/**
 * 日志动态代理工场
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:37
 */
public class JdkLogProxyFactory {

    public static final <T> T createProxyInstance(T t) {
        return (T) Proxy.newProxyInstance(t.getClass().getClassLoader(),
                t.getClass().getInterfaces(),
                new LogProxyInvocationHandler(t));
    }

}
