package com.zlikun.jee.j007;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * CGLib动态代理工厂
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:52
 */
public class CglibLogProxyFactory {

    public static final <T> T createProxyInstance(final T target) {

        // 这是一个工具类
        Enhancer enhancer = new Enhancer();
        // 设置父类（用于动态生成一个子类）
        enhancer.setSuperclass(target.getClass());
        // 设置回调函数
        enhancer.setCallback(new MethodInterceptor() {
            /**
             * 动态代理核心方法
             * @param o             和JDK中的一样，不知道意义但不能使用
             * @param method        代理方法对象
             * @param args          代理方法参数列表
             * @param methodProxy
             * @return
             * @throws Throwable
             */
            @Override
            public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {

                System.out.println(methodProxy);


                // 需求一：打印调用日志(简化逻辑，忽略参数日志)
                System.out.printf("%s#%s方法！%n", target.getClass().getSimpleName(), method.getName());
                // 需求二：计算程序执行耗时
                long time = System.currentTimeMillis();
                Object r = method.invoke(target, args);
                System.out.printf("程序执行耗时：%d毫秒！%n", System.currentTimeMillis() - time);
                return r;
            }
        });
        // 创建子类（代理对象）
        return (T) enhancer.create();
    }

}
