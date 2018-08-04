package com.zlikun.jee.j007;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:27
 */
public class LogProxyInvocationHandler implements InvocationHandler {
    private Object target;

    public LogProxyInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 动态代理核心逻辑
     * @param proxy     不知道有啥用，不能使用
     * @param method    代理方法对象
     * @param args      代理方法参数
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 需求一：打印调用日志(简化逻辑，忽略参数日志)
        System.out.printf("%s#%s方法！%n", target.getClass().getSimpleName(), method.getName());
        // 需求二：计算程序执行耗时
        long time = System.currentTimeMillis();
        Object r = method.invoke(target, args);
        System.out.printf("程序执行耗时：%d毫秒！%n", System.currentTimeMillis() - time);
        return r;
    }
}
