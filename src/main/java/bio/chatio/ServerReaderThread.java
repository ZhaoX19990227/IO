package bio.chatio;

import java.io.*;
import java.net.Socket;

public class ServerReaderThread extends Thread {
    Socket socket;

    public ServerReaderThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while((msg = br.readLine()) != null) {
                sendMsgToAllClient(msg);
            }
        } catch (IOException e) {
            System.out.println("有人下线");
            Server.allOnlineClientSockets.remove(socket);
        }
    }

    private void sendMsgToAllClient(String msg) throws IOException {
        for (Socket onlineClientSocket : Server.allOnlineClientSockets) {
            //通过socket流出给client
            PrintStream ps = new PrintStream(onlineClientSocket.getOutputStream());
            ps.println(msg);
            ps.flush();
        }
    }
}
