package com.zlikun.jee.j015;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 11:00
 */
public interface UserService {

    @Cacheable(prefix = "user", version = 16)
    String get(long userId);

    @CacheEvict(prefix = "user", version = 16)
    void update(long userId, String name);

}
