package com.zx.netty.c2;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.zx.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class TestWebSocket {
    public static void main(String[] args) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(16);
        //创建服务端
        ServerSocketChannel ssc = ServerSocketChannel.open();
        //绑定端口
        ssc.bind(new InetSocketAddress(9999));
        //非阻塞
        ssc.configureBlocking(false);
        //连接集合
        List<SocketChannel> list = new ArrayList<>();

        while (true) {
            //等待客户端连接
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                log.debug("connected...{}", sc);
                sc.configureBlocking(false);
                list.add(sc);
            }
            for (SocketChannel channel : list) {
                log.debug("before read...{}", channel);
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }
            }
        }
    }
}
