package com.zlikun.jee.j007;

/**
 * 业务接口
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 17:00
 */
public interface HelloService {

    /**
     * 业务：用户说一句话
     * @param name
     * @param message
     * @return
     */
    String say(String name, String message);


}
