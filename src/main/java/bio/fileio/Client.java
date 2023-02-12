package bio.fileio;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        try (InputStream is = new FileInputStream("/Users/zhaoxiang/WechatIMG325.png");
        ) {
            Socket socket = new Socket("127.0.0.1", 8082);
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            dos.writeUTF(".png");
            byte[] buffer = new byte[1024];
            int len;
            while ((len = is.read(buffer)) > 0) {
                dos.write(buffer, 0, len);
            }
            dos.flush();
            socket.shutdownOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
