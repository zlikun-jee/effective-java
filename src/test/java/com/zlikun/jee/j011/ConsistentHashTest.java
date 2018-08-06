package com.zlikun.jee.j011;

import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 一致性哈希测试，遗留问题：当物理节点失效或者增加物理节点时，重新映射虚拟节点与物理节点，因为使用取模运算，
 * 导致会映射关系会大面积发生变化，那么对应缓存服务就会大面积数据漂移，从而造成雪崩，要怎样来映射虚拟节点与物理节点才能避免这一点呢？
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/6 12:53
 */
public class ConsistentHashTest {

    /**
     * 表示物理节点（以缓存为例）
     */
    static class Node {
        private String name;

        public Node(String name) {
            this.name = name;
        }

        public void set(String key, Object value) {
            // 输出日志，模拟写入缓存操作
            System.out.printf("[%s] key = %s, value = %s %n", this.name, key, value);
        }
    }

    static class VirtualNode {
        private int code;
        private Node node;

        public void set(String key, Object value) {
            // 虚拟节点实际执行的物理节点的方法
            this.node.set(key, value);
        }
    }

    // 虚拟节点数量（建议是2的整数次幂，如：2^10 == 1024）
    private int size = 1 << 10;
    // 注：2^32 == Integer.MAX_VALUE
    // 单个虚拟节点在哈希环中负责哈希值个数
    private int step = Integer.MAX_VALUE / size;

    private List<Node> nodes;
    private VirtualNode[] virtualNodes;

    @Before
    public void init() {
        // 初始化物理节点
        nodes = Stream.of(
                new Node("A"),
                new Node("B"),
                new Node("C")
        ).collect(toList());

        // 初始化虚拟节点
        virtualNodes = new VirtualNode[size];

        // 建立虚拟节点与物理节点映射关系
        mapping();
    }

    /**
     * 映射虚拟节点与物理节点，当物理节点数发生变化后，需要重新执行映射
     */
    private void mapping() {
        // 虚拟节点分散在[0 ~ 2^32 - 1]哈希区间上，并映射到物理节点上
        int nodeSize = nodes.size();
        for (int i = 0; i < size; i++) {
            virtualNodes[i] = new VirtualNode();
            virtualNodes[i].code = i * step;
            // 如果长度为2的整数次幂可以用&计算（提升性能）
            virtualNodes[i].node = nodes.get(i % nodeSize);
        }
    }

    /**
     * 三个物理节点（初始节点）
     */
    @Test
    public void three_physical_nodes() {

        /*  测试写入缓存过程（此时有三个物理节点）
            [C] key = name0, value = zlikun0
            [A] key = name1, value = zlikun1
            [B] key = name2, value = zlikun2
            [C] key = name3, value = zlikun3
            [A] key = name4, value = zlikun4
            [B] key = name5, value = zlikun5
            [C] key = name6, value = zlikun6
            [A] key = name7, value = zlikun7
            [B] key = name8, value = zlikun8
            [C] key = name9, value = zlikun9
         */
        for (int j = 0; j < 10; j++) {
            String key = "name" + j, value = "zlikun" + j;
            int hash = hash(key);
            int i = indexFor(hash);
            this.virtualNodes[i].set(key, value);
        }

    }

    /**
     * 四个物理节点，增加了一台
     */
    @Test
    public void four_physical_nodes() {

        this.nodes.add(new Node("D"));
        this.mapping();

        /*  测试写入缓存过程
            [B] key = name0, value = zlikun0
            [C] key = name1, value = zlikun1
            [D] key = name2, value = zlikun2
            [A] key = name3, value = zlikun3
            [B] key = name4, value = zlikun4
            [C] key = name5, value = zlikun5
            [D] key = name6, value = zlikun6
            [A] key = name7, value = zlikun7
            [B] key = name8, value = zlikun8
            [C] key = name9, value = zlikun9
         */
        for (int j = 0; j < 10; j++) {
            String key = "name" + j, value = "zlikun" + j;
            int hash = hash(key);
            int i = indexFor(hash);
            this.virtualNodes[i].set(key, value);
        }

    }

    /**
     * 两个物理节点，宕机了一台
     */
    @Test
    public void two_physical_nodes() {

        this.nodes.remove(0);
        this.mapping();

        /*  测试写入缓存过程
            [C] key = name0, value = zlikun0
            [B] key = name1, value = zlikun1
            [C] key = name2, value = zlikun2
            [B] key = name3, value = zlikun3
            [C] key = name4, value = zlikun4
            [B] key = name5, value = zlikun5
            [C] key = name6, value = zlikun6
            [B] key = name7, value = zlikun7
            [C] key = name8, value = zlikun8
            [B] key = name9, value = zlikun9
         */
        for (int j = 0; j < 10; j++) {
            String key = "name" + j, value = "zlikun" + j;
            int hash = hash(key);
            int i = indexFor(hash);
            this.virtualNodes[i].set(key, value);
        }

    }

    /**
     * 根据HashCode查找虚拟节点索引
     *
     * @param hash
     * @return
     */
    private int indexFor(int hash) {
        return hash & (size - 1);
    }

    /**
     * 计算数据的哈希值
     *
     * @param data
     * @return
     */
    private int hash(String data) {
        return data.hashCode();
    }

}
