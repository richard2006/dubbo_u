package com.li.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;




/**
 * @author think
 *
 */
public class NIOServer implements Runnable{
	private int port = 7070;
	// 解码buffer
	private Charset cs = Charset.forName("gbk");
	private static Selector selector;
	public NIOServer() {
	}

	/**
	 * 启动服务器端，配置为非阻塞，绑定端口，注册accept事件ACCEPT事件：当服务端收到客户端连接请求时，触发该事件
	 * @throws IOException
	 */
	private void initchannel(){
		try{
			//初始化SOCKET通道
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.configureBlocking(false);
			ServerSocket serverSocket = serverSocketChannel.socket();
			serverSocket.bind(new InetSocketAddress(port));
			selector = Selector.open();
//			new Thread(new ReactorTask()).start();

			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			System.out.println("Init NIOServer listening port:" + port);
		} catch (Exception e) {
			System.out.println("Init NIOServer failed! error:"+e.getMessage());
		}
		
	}

	/**
	 * 服务器端轮询监听，select方法会一直阻塞直到有相关事件发生或超时
	 */
	private void listen() {
		//初始化SOCKET通道
		initchannel();
		while (true) {
			try {
				selector.select();// 返回值为本次触发的事件数
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for (SelectionKey key : selectionKeys) {
					//处理客户端请求
					handle(key);
				}
				selectionKeys.clear();// 清除处理过的事件
			} catch (Exception e) {
				System.out.println("Exit listen port:"+this.port+" error:"+e.getMessage());
			}

		}
	}

	/**
	 * 处理不同的事件
	 */
	private void handle(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		if (selectionKey.isAcceptable()) {
			/*
			 * 客户端请求连接事件 serversocket为该客户端建立socket连接，将此socket注册READ事件，监听客户端输入
			 * READ事件：当客户端发来数据，并已被服务器控制线程正确读取时，触发该事件
			 */
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			//System.out.println("Client IP:"+client.getRemoteAddress().toString());
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			ByteBuffer sBuffer = ByteBuffer.allocate(1024);
			/*
			 * READ事件，收到客户端发送数据，读取数据后继续注册监听客户端
			 */
			client = (SocketChannel) selectionKey.channel();
			sBuffer.clear();

			int n = -1;
			try {
				n = client.read(sBuffer);
				//log.info("输入字节数：" + n);
			} catch (Exception e) {
				n=-1;
				client.close();
				selectionKey.cancel();
			}
			if (n > 0) {
				sBuffer.flip();
				String receiveText = String.valueOf(cs.decode(sBuffer).array());
				//实际业务处理部分忽略
				channelWriteBytes(client,"Server:" +receiveText);
			}
		}
	}

	@Override
	public void run() {
		//线程开启SOCKET监听
		listen();
	}
	/**
     * 结果回写
     */
    public void channelWriteBytes(SocketChannel socketChannel,String result){
    	ByteBuffer byBuffer = ByteBuffer.allocate(10240);
    	byBuffer.put((result).getBytes(Charset.forName("gbk")));
		byBuffer.flip();
		// 输出到通道
		try {
			socketChannel.write(byBuffer);
		} catch (IOException e) {
			System.out.println("NIOServer write back failed:"+e.getMessage());
		}
    }
    
	public static void main(String[] args) throws IOException {
		NIOServer server = new NIOServer();
		server.listen();
	}

}