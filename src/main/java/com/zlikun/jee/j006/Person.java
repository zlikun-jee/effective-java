package com.zlikun.jee.j006;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 10:40
 */
public class Person {

    private String name;

    public Person() {
    }

    public Person(String name) {
        this.name = name;
    }

    private void fart() {
        System.out.printf("%s偷偷放了一个屁（反正我是私有的，别人看不见^_^）。%n", this.name);
    }

    protected void walk() {
        System.out.printf("%s正在步行.%n", this.name);
    }

    protected void walk(int speed) {
        System.out.printf("%s正在以%d的速度步行.%n", speed, this.name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
