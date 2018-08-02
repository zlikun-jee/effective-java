package com.zlikun.jee.j014;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * 位运算及位运算符测试
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/2 18:25
 */
public class PositionalOperatorTest {

    /**
     * 按位与 &，仅当两个操作数都是1时，输出结果才为1，否则为0
     */
    @Test
    public void bitAnd() {
        // 比较数二进制长度不等时前面补0对齐
        // 12 -> 1100
        //  5 -> 0101
        // 12 & 5 = 0100 0 4
        assertEquals(4, 12 & 5);
        // 使用二进制写法测试
        assertEquals(0b0100, 0b1100 & 0b0101);
    }

    /**
     * 按位或 |，仅当两个操作数都是0时，输出结果才为0，否则为1
     */
    @Test
    public void bitOr() {
        assertEquals(13, 12 | 5);
        assertEquals(0b1101, 0b1100 | 0b0101);
    }

    /**
     * 按位异或 ^，仅当两个操作数不同时，输出结果才为1，否则为0
     */
    @Test
    public void bitXor() {
        assertEquals(9, 12 ^ 5);
        assertEquals(0b1001, 0b1100 ^ 0b0101);
    }

    /**
     * 按位取反 ~，为头单目运算符，用于将全部数字取反：0为1，1为0
     * 将一个二进制数按位取反，等效于-1减去该数，如：~12 == -13
     */
    @Test
    public void bitNot() {
        // 注意，上面都是简写的形式，前面的0未写出来，但取反时要写全，否则结果是错的，int类型是32位
        // 下面目标数字实际是：0000000000000000000000000001100，按位取反：11111111111111111111111111110011
        // 注意二进制左边第一位是0，表示无符号整数（为1表示负数，即有符号整数），要使用Integer.parseUnsignedInt()方法转换
        assertEquals(0b11111111111111111111111111110011, ~0b1100);
        // ~12 == -1 - 12 == -13
        assertEquals(-13, ~12);
        // ~-12 == -1 - (-12) == -1 + 12 == 11
        assertEquals(11, ~-12);
    }

    /**
     * 左移 <<，就是把一个数的全部位都向左移动指定位
     * 所以左移n位就是乘以2的n次方
     */
    @Test
    public void leftShift() {
        // 12 -> 1100：1100 << 0 == 1100 == 12 == 12 * 2 ^ 0
        assertEquals(24, 12 << 1);
        // 12 -> 1100：1100 << 1 == 11000 == 24 == 12 * 2 ^ 1
        assertEquals(24, 12 << 1);
        // 12 -> 1100：1100 << 2 == 110000 == 48 == 12 * 2 ^ 2
        assertEquals(48, 12 << 2);
        // 12 -> 1100：1100 << 3 == 1100000 == 96 == 12 * 2 ^ 3
        assertEquals(96, 12 << 3);
    }

    /**
     * 右移 >>，把一个数的全部位都向右移动指定位
     * 所以右移n位就是除以2的n次方结果取整
     */
    @Test
    public void rightShift() {
        // 1100 >> 1 == 0110 == 6
        assertEquals(6, 12 >> 1);
        // 1100 >> 2 == 0011 == 3
        assertEquals(3, 12 >> 2);
        // 1100 >> 3 == 0001 == 1
        assertEquals(1, 12 >> 3);
        // 1100 >> 4 == 0000 == 0
        assertEquals(0, 12 >> 4);

        // 100000 >> 5 == 000001 == 1
        assertEquals(1, 32 >> 5);
        assertEquals(2, 32 >> 4);
        assertEquals(4, 32 >> 3);
        assertEquals(8, 32 >> 2);
        assertEquals(16, 32 >> 1);
        assertEquals(32, 32 >> 0);
    }

    /**
     * 无符号右移 >>>，与右移类似，但是符号位仍用原值填充
     */
    @Test
    public void unsignedRightShift() {
        // 00000000000000000000000000001100 >>> 1 == 00000000000000000000000000000110
        // => 110 == 6
//        System.out.println(Integer.toBinaryString(12));
        assertEquals(12, 12 >>> 0);
        assertEquals(6, 12 >>> 1);
        assertEquals(3, 12 >>> 2);
        assertEquals(1, 12 >>> 3);
        assertEquals(0, 12 >>> 4);
        assertEquals(0, 12 >>> 5);

        // 11111111111111111111111111110100 >>> 1 == 01111111111111111111111111111010
//        System.out.println(Integer.toBinaryString(-12));
        assertEquals(-12, -12 >>> 0);
        assertEquals(2147483642, -12 >>> 1);
        assertEquals(1073741821, -12 >>> 2);
        assertEquals(536870910, -12 >>> 3);


        // 标志位使用原标志位填充，所以还是1
        // 11111111111111111111111111110100 >> 1 == 11111111111111111111111111111010
        assertEquals(-6, -12 >> 1);
        // 10111111111111111111111111110100 >> 1 == 11011111111111111111111111111010
        assertEquals(-536870918, -1073741836 >> 1);
        assertEquals(-1073741836, 0b10111111111111111111111111110100);
        assertEquals(-536870918, 0b11011111111111111111111111111010);

    }

}
