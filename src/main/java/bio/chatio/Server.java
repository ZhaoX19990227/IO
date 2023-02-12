package bio.chatio;

import com.google.common.collect.Lists;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

public class Server {
    public static List<Socket> allOnlineClientSockets = Lists.newArrayList();
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8086);
            while (true) {
                Socket socket = ss.accept();
                allOnlineClientSockets.add(socket);
                new ServerReaderThread(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
