package com.zlikun.jee.j015;

import java.util.Random;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 11:01
 */
public class UserServiceImpl implements UserService {

    @Override
    public String get(long userId) {
        System.out.println("执行查询逻辑!");
        // 随机休眠[0, 256)毫秒模拟程序实际执行过程
        try {
            Thread.sleep(new Random().nextLong() & 255L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("user-%d", userId);
    }

    @Override
    public void update(long userId, String name) {
        System.out.println("执行更新逻辑!");
    }
}
