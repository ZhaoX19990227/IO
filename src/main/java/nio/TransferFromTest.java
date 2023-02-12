package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class TransferFromTest {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("data01.txt");
            FileOutputStream fos = new FileOutputStream("data02.txt");
            FileChannel fisChannel = fis.getChannel();
            FileChannel fosChannel = fos.getChannel();

            //fosChannel.transferFrom(fisChannel,fisChannel.position(),fisChannel.size());
            fisChannel.transferTo(fisChannel.position(), fisChannel.size(),fosChannel);
            fisChannel.close();
            fosChannel.close();
            System.out.println("复制成功！");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
