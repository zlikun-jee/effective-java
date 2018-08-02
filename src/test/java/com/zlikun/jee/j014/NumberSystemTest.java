package com.zlikun.jee.j014;

import org.junit.Test;

import java.util.Stack;

import static org.junit.Assert.assertEquals;

/**
 * 进制转换测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 17:32
 */
public class NumberSystemTest {

    @Test
    public void test() {


        // 以十进制数字 1024 为例，测试进制间转换
        int number = 1024;

        // 使用短除法转换为二进制，短除法转换时除数为进制值，这里是二进制，所以是2
        // 当整数被除尽后，余数倒排序即为转换后的二进制数字，因为要倒排，这里采用栈结构存储余数
        Stack<Integer> stack = new Stack<>();
        int m = number;
        while (m != 0) {
            // 整除后的余数
            stack.push(m % 2);
            // 整除后的结果
            m /= 2;
        }
        // 将数据取出，组成二进制序列
        // 10000000000
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
        // 测试结果是否正确(后面的项全是0，所以省略了)
        assertEquals(number, 1 * (int) (Math.pow(2, 10)));

        // 使用短除法转换为八进制
        stack = new Stack<>();
        m = number;
        while (m != 0) {
            // 整除后的余数
            stack.push(m % 8);
            // 整除后的结果
            m /= 8;
        }
        // 将数据取出，组成八进制序列
        // 2000
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
        // 测试结果是否正确(位数-1次幂)
        assertEquals(number, 2 * (int) (Math.pow(8, 3)));

        // 使用短除法转换为十六进制
        stack = new Stack<>();
        m = number;
        while (m != 0) {
            // 整除后的余数
            stack.push(m % 16);
            // 整除后的结果
            m /= 16;
        }
        // 将数据取出，组成八进制序列
        // 400
        while (!stack.isEmpty()) {
            System.out.print(stack.pop());
        }
        System.out.println();
        // 测试结果是否正确(位数-1次幂)
        assertEquals(number, 4 * (int) (Math.pow(16, 2)));

        // 将二进制数字转换为十进制
        // 二进制数101 == 十进制数5
        assertEquals(5, 1 * (int) (Math.pow(2, 2)) + 0 * (int) (Math.pow(2, 1)) + 1 * (int) (Math.pow(2, 0)));

        // 将二进制数字转换为八进制和十六进制，一般先转换为十进制，再由十进制向各个进制进行转换
        // 二进制数1101 == 十进制数13
        assertEquals(13, 1 * (int) (Math.pow(2, 3)) + 1 * (int) (Math.pow(2, 2)) + 0 * (int) (Math.pow(2, 1)) + 1 * (int) (Math.pow(2, 0)));
        // 十进制数13 == 八进制数15
        assertEquals(13, 1 * (int) (Math.pow(8, 1)) + 5 * (int) (Math.pow(8, 0)));

        // 八进制与十六进制互转

    }

    /**
     * 关于进制转换，实现JDK已提供了相关方法
     */
    @Test
    public void test2() {

        // 十进制转换二进制
        assertEquals("10000000000", Integer.toBinaryString(1024));
        // 十进制转换八进制
        assertEquals("2000", Integer.toOctalString(1024));
        // 十进制转换十六进制
        assertEquals("400", Integer.toHexString(1024));
        assertEquals("40c", Integer.toHexString(1036));

        // 其它进制转换为十进制
        assertEquals(1024, Integer.parseInt("10000000000", 2));
        assertEquals(1024, Integer.parseInt("2000", 8));
        assertEquals(1024, Integer.parseInt("400", 16));

    }

    /**
     * 另外还可以以格式化字符串方式进行转换
     */
    @Test
    public void test3() {
        // 1024, 2000, 400，没有二进制格式化方法
        System.out.println(String.format("%d, %o, %x", 1024, 1024, 1024, 1024));
        // 1024, 02000, 0x400
        System.out.println(String.format("%d, %#o, %#x", 1024, 1024, 1024, 1024));
    }


}
