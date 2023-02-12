package nio;


import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class ChannelReadTest {
    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("data01.txt1111");
            FileChannel channel = fileInputStream.getChannel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            buffer.flip();
            String result = new String(buffer.array(), 0, buffer.remaining());
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
