package com.xter.io;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * @author XTER
 * 项目名称: WebDisplay
 * 创建时间: 2020/12/2
 * 描述:
 */
public class UdpReceiver {

	private static class Holder {
		private static UdpReceiver INSTANCE = new UdpReceiver();
	}

	public static UdpReceiver get() {
		return Holder.INSTANCE;
	}

	ScheduledExecutorService ses;

	private UdpReceiver(){
		ses = new ScheduledThreadPoolExecutor(1);
	}

	public static void main(String[] args) {
		UdpReceiver.get().run();
	}

	public void run(){
		ses.execute(new Runnable() {
			@Override
			public void run() {
				start();
			}
		});
	}

	public void start() {
		DatagramSocket ds = null;
		try {
			ds = new DatagramSocket(14001);
			while (true) {
				//接受数据
				byte[] bys = new byte[1024];
				DatagramPacket dp = new DatagramPacket(bys, bys.length);
				//阻塞
				ds.receive(dp);
				//解析数据
				InetAddress address = dp.getAddress();
				//获取接收到的数据
				byte[] data = dp.getData();
				//获取具体收到的数据长度
				int length = dp.getLength();
				//发送人
				System.out.println("sender :" + address.getHostAddress());
				//接收到的内容
				System.out.println(new String(data, 0, length));
				//释放资源
			}
		} catch (IOException e) {
			if (ds != null) {
				ds.close();
			}
		}
	}


}
