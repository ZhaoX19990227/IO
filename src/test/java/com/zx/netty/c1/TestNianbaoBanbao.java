package com.zx.netty.c1;

import java.nio.ByteBuffer;

import static com.zx.netty.c1.ByteBufferUtil.debugAll;

public class TestNianbaoBanbao {
    /* public static void main(String[] args) {
         ByteBuffer source = ByteBuffer.allocate(32);
         //                     11            24
         source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
         split(source);

         source.put("w are you?\nhaha!\n".getBytes());
         split(source);
     }

     private static void split(ByteBuffer source) {
         source.flip();

         for (int i = 0; i < source.limit(); i++) {
             if (source.get(i) == '\n') {
                 int length = i - source.position() + 1;
                 ByteBuffer target = ByteBuffer.allocate(length);

                 for (int j = 0; j < length; j++) {
                     target.put(source.get(i));
                 }
                 debugAll(target);
             }
         }
         source.compact();
     }*/



    /*
	拆分source为完整的一句话。
*/
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        //                     11            24
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);

        source.put("w are you?\nhaha!\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        //将buffer转换成读模式
        source.flip();
        int oldLimit = source.limit();
        for (int i = 0; i < oldLimit; i++) {
            if (source.get(i) == '\n') {
                //  i + 1 - source.position()就是获取完成的一句话的长度。
                //  从 H 到 \n 的长度，此时读模式下position是指向第一个字符的。
                ByteBuffer target = ByteBuffer.allocate(i + 1 - source.position());
                // 0 ~ limit
                source.limit(i + 1);
                target.put(source); // 从source 读，向 target 写
                debugAll(target);
                source.limit(oldLimit);
            }
        }
  /*
    这边不能使用clear，因为会clear会从来开始写，会导致 Ho 字符被覆盖。
  	compact会将第一次没有读完的数据向前移动，和下一次要读的数据结合在一起。
  */
        source.compact();
    }
}
