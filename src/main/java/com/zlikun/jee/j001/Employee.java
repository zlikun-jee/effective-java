package com.zlikun.jee.j001;

import java.util.Date;
import java.util.Objects;

/**
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 10:13
 */
public class Employee {

    private String name;
    private Double salary;
    private Date joinDate;

    @Override
    public boolean equals(Object obj) {
        // 这里使用==显示判断比较对象是否是同一对象
        if (this == obj) {
            return true;
        }
        // 对于任何非null的引用值x，x.equals(null)必须返回false
        if (obj == null) {
            return false;
        }
        // 通过 instanceof 判断比较对象类型是否合法
        if (!(obj instanceof Employee)) {
            return false;
        }
        // 对象类型强制转换，如果核心域比较相等，则返回true，否则返回false
        Employee other = (Employee) obj;
        // 如果两者相等，返回true（含两者皆空的情形），否则比较两者值是否相等
        return Objects.equals(this.name, other.name)
                && Objects.equals(this.salary, other.salary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.salary);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Date getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(Date joinDate) {
        this.joinDate = joinDate;
    }

}
