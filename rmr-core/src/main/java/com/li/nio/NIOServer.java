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
	// ����buffer
	private Charset cs = Charset.forName("gbk");
	private static Selector selector;
	public NIOServer() {
	}

	/**
	 * �����������ˣ�����Ϊ���������󶨶˿ڣ�ע��accept�¼�ACCEPT�¼�����������յ��ͻ�����������ʱ���������¼�
	 * @throws IOException
	 */
	private void initchannel(){
		try{
			//��ʼ��SOCKETͨ��
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
	 * ����������ѯ������select������һֱ����ֱ��������¼�������ʱ
	 */
	private void listen() {
		//��ʼ��SOCKETͨ��
		initchannel();
		while (true) {
			try {
				selector.select();// ����ֵΪ���δ������¼���
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				for (SelectionKey key : selectionKeys) {
					//����ͻ�������
					handle(key);
				}
				selectionKeys.clear();// �����������¼�
			} catch (Exception e) {
				System.out.println("Exit listen port:"+this.port+" error:"+e.getMessage());
			}

		}
	}

	/**
	 * ����ͬ���¼�
	 */
	private void handle(SelectionKey selectionKey) throws IOException {
		ServerSocketChannel server = null;
		SocketChannel client = null;
		if (selectionKey.isAcceptable()) {
			/*
			 * �ͻ������������¼� serversocketΪ�ÿͻ��˽���socket���ӣ�����socketע��READ�¼��������ͻ�������
			 * READ�¼������ͻ��˷������ݣ����ѱ������������߳���ȷ��ȡʱ���������¼�
			 */
			server = (ServerSocketChannel) selectionKey.channel();
			client = server.accept();
			//System.out.println("Client IP:"+client.getRemoteAddress().toString());
			client.configureBlocking(false);
			client.register(selector, SelectionKey.OP_READ);
		} else if (selectionKey.isReadable()) {
			ByteBuffer sBuffer = ByteBuffer.allocate(1024);
			/*
			 * READ�¼����յ��ͻ��˷������ݣ���ȡ���ݺ����ע������ͻ���
			 */
			client = (SocketChannel) selectionKey.channel();
			sBuffer.clear();

			int n = -1;
			try {
				n = client.read(sBuffer);
				//log.info("�����ֽ�����" + n);
			} catch (Exception e) {
				n=-1;
				client.close();
				selectionKey.cancel();
			}
			if (n > 0) {
				sBuffer.flip();
				String receiveText = String.valueOf(cs.decode(sBuffer).array());
				//ʵ��ҵ�����ֺ���
				channelWriteBytes(client,"Server:" +receiveText);
			}
		}
	}

	@Override
	public void run() {
		//�߳̿���SOCKET����
		listen();
	}
	/**
     * �����д
     */
    public void channelWriteBytes(SocketChannel socketChannel,String result){
    	ByteBuffer byBuffer = ByteBuffer.allocate(10240);
    	byBuffer.put((result).getBytes(Charset.forName("gbk")));
		byBuffer.flip();
		// �����ͨ��
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