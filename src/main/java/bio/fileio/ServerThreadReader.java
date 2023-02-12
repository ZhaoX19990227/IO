package bio.fileio;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.UUID;

public class ServerThreadReader extends Thread {
    Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            //读取客户端发来文件的类型
            String suffix = dis.readUTF();
            System.out.println("服务器已接收到文件类型为：" + suffix);
            //定义一个字节输出管，文件写出去
            OutputStream ots = new FileOutputStream("/Users/zhaoxiang/serverPath/" + UUID.randomUUID().toString() + suffix);
            //从数据输入流中读取数据，输出到字节输出流中
            byte[] buffer = new byte[1024];
            int len;
            while((len = dis.read(buffer)) > 0) {
                ots.write(buffer,0,len);
            }
            ots.close();
            System.out.println("服务端接收文件保存成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
