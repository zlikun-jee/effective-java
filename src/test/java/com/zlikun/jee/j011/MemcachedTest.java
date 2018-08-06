package com.zlikun.jee.j011;

import net.spy.memcached.*;
import net.spy.memcached.internal.OperationFuture;
import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 阅读SpyMemcached客户端一致性哈希实现源码
 *
 * @author zlikun <zlikun-dev@hotmail.com>
 * @date 2018/8/6 13:53
 */
public class MemcachedTest {

    @Test
    public void test() throws IOException, ExecutionException, InterruptedException {

        // 添加新哈希算法，KETAMA_HASH算法本就存在，只作示例用，所说MurmurHash算法也不错，具体待研究
        HashAlgorithmRegistry.registerHashAlgorithm("KETAMA_HASH", DefaultHashAlgorithm.KETAMA_HASH);

        // 构造连接客户端，有三台物理节点
        MemcachedClient client = new MemcachedClient(new ConnectionFactoryBuilder()
                .setHashAlg(DefaultHashAlgorithm.KETAMA_HASH)
                .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                .setFailureMode(FailureMode.Retry)
                .setDaemon(true)
                .setMaxReconnectDelay(1000L)
                .build(),
                Stream.of(
                        new InetSocketAddress("192.168.0.105", 11211),
                        new InetSocketAddress("192.168.0.105", 11212),
                        new InetSocketAddress("192.168.0.105", 11213)
                ).collect(toList()));

        // 执行插入
        OperationFuture<Boolean> future = client.set("name", 3600, "zlikun");
        assertTrue(future.get());

        // 执行读取
        assertEquals("zlikun", client.get("name"));

        // 执行删除
        client.delete("name");
    }

}
