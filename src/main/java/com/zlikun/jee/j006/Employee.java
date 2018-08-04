package com.zlikun.jee.j006;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/4 10:41
 */
public class Employee extends Person {

    // 简化代码，使用Long类型，单位：分
    private Long salary = 0L;

    public Employee(String name, Long salary) {
        super(name);
        this.salary = salary;
    }

    private Employee(Long salary) {
        this.salary = salary;
    }

    protected Employee() {

    }

    /**
     * 加薪，返回加薪后的薪资
     *
     * @param amount
     */
    public Long raise(long amount) {
        this.salary += amount;
        return this.salary;
    }

    private long tip(long amount) {
        System.out.printf("%s偷偷跟客户要了小费（反正我是私有的，老板看不到^_^）。%n", this.getName());
        return amount;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
