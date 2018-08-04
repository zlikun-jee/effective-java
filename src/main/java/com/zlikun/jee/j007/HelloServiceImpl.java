package com.zlikun.jee.j007;

import java.util.Random;

/**
 * 业务实现
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:04
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String say(String name, String message) {
        // 使程序休眠[0, 200]毫秒，模拟代码执行过程
        try {
            Thread.sleep(new Random().nextLong() & 200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return String.format("%s : %s", name, message);
    }
}
