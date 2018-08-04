package com.zlikun.jee.j007;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:15
 */
public class HelloServiceLogProxy implements HelloService {
    private HelloService service;

    public HelloServiceLogProxy(HelloService service) {
        this.service = service;
    }

    @Override
    public String say(String name, String message) {
        // 需求一：打印调用日志
        System.out.printf("调用了HelloService#say(%s, %s)方法！%n", name, message);
        // 需求二：计算程序执行耗时
        long time = System.currentTimeMillis();
        String r = this.service.say(name, message);
        System.out.printf("程序执行耗时：%d毫秒！%n", System.currentTimeMillis() - time);
        return r;
    }
}
