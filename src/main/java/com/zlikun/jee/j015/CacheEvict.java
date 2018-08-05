package com.zlikun.jee.j015;

import java.lang.annotation.*;

/**
 * 表示从缓存中删除
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/5 10:59
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CacheEvict {

    /**
     * 缓存前缀
     * @return
     */
    String prefix() default "";

    /**
     * 缓存前缀，相当于prefix的别名，value表示是一个默认属性（当只有这一个属性时，可以省略属性名）
     * @return
     */
    String value() default "";

    /**
     * 缓存版本
     * @return
     */
    int version() default 0;

}
