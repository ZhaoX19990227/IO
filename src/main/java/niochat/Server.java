package niochat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

public class Server {
    private Selector selector;
    private ServerSocketChannel ssChanel;
    private static final int PORT = 9999;

    public Server() {
        try {
            //开启选择器
            selector = Selector.open();
            //开启服务端通道
            ssChanel = ServerSocketChannel.open();
            //绑定端口
            ssChanel.bind(new InetSocketAddress(PORT));
            //服务端通道设置非阻塞
            ssChanel.configureBlocking(false);
            //服务端通道注册到选择器
            ssChanel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 监听客户端
     */
    private void listen() {
        try {
            //如果选择器轮询到了事件
            while (selector.select() > 0) {
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    //提取事件
                    SelectionKey selectionKey = it.next();
                    if (selectionKey.isAcceptable()) {
                        //获取客户端通道
                        SocketChannel socketChannel = ssChanel.accept();
                        socketChannel.configureBlocking(false);
                        //客户端通道注册到选择器上
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        //接受客户端消息并转发给其他所有客户端
                        readClientData(selectionKey);
                    }
                    it.remove();//处理完毕后需要移除当前事件
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 接受当前客户端消息转发给其他所有客户端
     *
     * @param selectionKey
     */
    private void readClientData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            //得到当前客户端通道
            socketChannel = (SocketChannel) selectionKey.channel();
            //创建缓冲区接收客户端消息
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = socketChannel.read(buffer);
            if (count > 0) {
                buffer.flip();
                //提取消息
                String msg = new String(buffer.array(), 0, buffer.remaining());
                System.out.println("接收客户端消息：" + msg);
                //消息转发
                sendMsgToAllClient(msg, socketChannel);
            }
        } catch (IOException e) {
            try {
                System.out.println("有人离线了：" + socketChannel.getRemoteAddress());
                //当前客户端离线
                selectionKey.cancel();
                socketChannel.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

    }

    private void sendMsgToAllClient(String msg, SocketChannel socketChannel) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            //排除自己
            if (channel instanceof SocketChannel && channel != socketChannel) {
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                ((SocketChannel) channel).write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.listen();
    }
}
