package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CopyFileTest {
    public static void main(String[] args) {
        try {
            File srcFile = new File("/Users/zhaoxiang/WechatIMG325.png");
            File destFile = new File("/Users/zhaoxiang/xiaopang.png");
            FileInputStream fis = new FileInputStream(srcFile);
            FileOutputStream fos = new FileOutputStream(destFile);
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while (true) {
                buffer.clear();
                int read = fisChannel.read(buffer);
                if (read == -1) {
                    break;
                }
                buffer.flip();
                fosChannel.write(buffer);
            }
            fisChannel.close();
            fosChannel.close();
            System.out.println("复制成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
