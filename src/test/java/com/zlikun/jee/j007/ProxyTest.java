package com.zlikun.jee.j007;

import org.junit.Test;

import java.lang.reflect.Proxy;

import static org.junit.Assert.assertTrue;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:05
 */
public class ProxyTest {

    private HelloService service;

    @Test
    public void proxy() {

        // 模拟业务接口实现类实例注入
        service = new HelloServiceImpl();
        // I、业务接口调用
        String r = service.say("Ashe", "今天没吃早饭");
        // Ashe : 今天没吃早饭
        System.out.println(r);

        // II、需求微调，接口调用都必须加日志，记录接口调用的时间以及执行耗时
        // 如果直接在业务代码中实现日志逻辑，那么耦合就太重了，而且如果其它业务有同样需求，不利用代码复用
        // 设计模式中的代理模式刚好应对这个场景，实现一个日志代理来增强业务接口，这样就不用修改业务逻辑了
        // 模拟代理类实例注入
        service = new HelloServiceLogProxy(new HelloServiceImpl());
        // 调用了HelloService#say(Peter, 我一天都没吃饭了)方法！
        // 程序执行耗时：194毫秒！
        r = service.say("Peter", "我一天都没吃饭了");
        // Peter : 我一天都没吃饭了
        System.out.println(r);

    }

    @Test
    public void dynamicProxy() {

        // III、上例看似完美，但实际上存在一个问题，如果其它业务也需要实现需求二，那么日志代理因为实现了HelloService接口，
        // 所以是无法直接给其它业务使用的，也就是说无法代码复用
        // 解决这一问题的办法是使用动态代理，代理类实现需求二，但不绑定固定接口，也就是可以和任意接口配合使用，从而实现代码复用
        HelloServiceImpl logic = new HelloServiceImpl();
        service = (HelloService) Proxy.newProxyInstance(logic.getClass().getClassLoader(),
                logic.getClass().getInterfaces(),
                new LogProxyInvocationHandler(logic));
        // HelloServiceImpl#say方法！
        // 程序执行耗时：211毫秒！
        String r = service.say("Peter", "我一天都没吃饭了");
        // Peter : 我一天都没吃饭了
        System.out.println(r);

        // 检查一个对象是否是代理对象
        assertTrue(Proxy.isProxyClass(service.getClass()));
        // class com.sun.proxy.$Proxy4
        System.out.println(service.getClass());
    }

    @Test
    public void dynamicProxy2() {
        // IV、上面的动态代理还需要封装一下，否则实际应用过程中写这么一大段代码并不合适
        // 定义一个动态代理对象生成工厂，隐藏动态代理创建过程
        service = JdkLogProxyFactory.createProxyInstance(new HelloServiceImpl());
        // HelloServiceImpl#say方法！
        // 程序执行耗时：193毫秒！
        String r = service.say("Peter", "我一天都没吃饭了");
        // Peter : 我一天都没吃饭了
        System.out.println(r);

    }

    @Test
    public void dynamicProxy3() {
        // V、以上动态代理实现是JDK提供实现，存在的问题是只能代理接口，如果要代理类，需要使用CGLib库
        // 实际测试中发现即使方法使用protected和默认访问权限也能成功被代理
        UserService service = CglibLogProxyFactory.createProxyInstance(new UserService());
        // UserService#create方法！
        // 创建一个用户：account = Peter, password = 123456
        // 程序执行耗时：0毫秒！
        Long id = service.create("Peter", "123456");
        // 1
        System.out.println(id);

        // CGLib的代理类不能使用Proxy.isProxyClass()方法检测
        // class com.zlikun.jee.j007.UserService$$EnhancerByCGLIB$$7947b053
        System.out.println(service.getClass());
    }

}