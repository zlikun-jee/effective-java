package com.zlikun.jee.j007;

/**
 * 一个没有实现任何接口的类，用于测试CGLib动态代理
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 18:00
 */
public class UserService {

    Long create(String account, String password) {
        System.out.printf("创建一个用户：account = %s, password = %s%n", account, password);
        return 1L;
    }

}
