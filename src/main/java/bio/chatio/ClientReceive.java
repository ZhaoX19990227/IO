package bio.chatio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReceive {
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("127.0.0.1", 8086);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println("client收到消息:" + msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
