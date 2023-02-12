package com.zx.netty.c1;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestByteBuffer {
    public static void main(String[] args) {
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区  ByteBuffer抽象类不可以new 通过allocate静态方法直接分配缓冲区内存大小
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while (true) {
                //才从channel中读取数据，向buffer中写入
                int len = channel.read(buffer);
                if (len == -1) {
                    break;
                }
                //将buffer模式切换为read读
                buffer.flip();
                //是否还有未读数据
                while (buffer.hasRemaining()) {
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                //切换写模式
                buffer.clear();
            }
        } catch (IOException e) {

        }
    }
}
