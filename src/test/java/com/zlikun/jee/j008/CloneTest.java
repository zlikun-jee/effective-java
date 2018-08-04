package com.zlikun.jee.j008;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * 对象克隆测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 19:01
 */
public class CloneTest {

    static class FullName {
        private String firstName;
        private String lastName;

        public FullName(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }
    }

    static class User implements Cloneable {
        private FullName name;
        private int age;

        @Override
        public User clone() throws CloneNotSupportedException {
            return (User) super.clone();
        }

        /**
         * 注意这不是一个继承的方法，只是为了测试深拷贝
         *
         * @return
         */
        public User deepClone() throws CloneNotSupportedException {
            // 先用Object中的克隆方法（底层方法，性能相对更好）
            User other = (User) super.clone();
            // 将引用类型再克隆一次（注意如果引用类型中还有引用类型，那么同样需要克隆）
            // 多层克隆实现会比较麻烦，简单的做法是先序列化再反序列化一次即可（缺点是序列化的性能通常较差）
            if (other.name != null) {
                // 由于FullName类没有实现克隆方法，所以这里直接重新new一个
                other.name = new FullName(this.name.firstName, this.name.lastName);
            }
            return other;
        }

    }

    @Test
    public void test() throws CloneNotSupportedException {

        // 构造一个对象
        User user = new User();
        user.age = 17;
        user.name = new FullName("Jack", "Jones");

        // 克隆对象
        User other = user.clone();

        // 检查克隆结果
        // 克隆的对象非空
        assertNotNull(other);
        // 克隆的对象与原对象不再是同一个对象
        assertFalse(user == other);
        // 克隆后基本类型被克隆，所以原对象修改该属性值不影响非克隆后的对象属性
        user.age = 18;
        assertEquals(17, other.age);
        // 克隆后引用类型仅克隆了引用，而引用对象没有被克隆，所以两者指向同一个对象
        assertTrue(user.name == other.name);

        // 所以Object类中的默认实现是浅拷贝

        // 如果要实现深拷贝，需要将引用的对象也拷贝
        other = user.deepClone();

        // 测试深拷贝效果
        assertFalse(user == other);
        assertEquals(user.age, other.age);
        assertTrue(user.name != other.name);
    }

}
