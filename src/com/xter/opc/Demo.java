package com.xter.opc;

import java.util.ArrayList;
import java.util.List;

public class Demo {
	public static void main(String[] args) {
		// server
		String host = "172.16.150.142";
		// domain
		String domain = "";
		// server上的访问用户
		String user = "OpcServer";
		// 访问用户的密码
		String password = "123456";
		// clsid
		String clsid = "f8582cf2-88fb-11d0-b850-00c0f0104305";

		OpcClient opcClient = new OpcClient();
		// 1.显示server上的opc server应用列表
		opcClient.showAllOPCServer(host, user, password, domain);

		// 2.连接指定的opc server
		boolean ret = opcClient.connectServer(host, clsid, user, password, domain);
		if (!ret) {
			System.out.println("connect opc server fail");
			return;
		}
		// 3.检查opc server上的检测点
		List<String> itemIdList = new ArrayList<>();
		itemIdList.add("TEST.FA");
		itemIdList.add("TEST.FB");
		ret = opcClient.checkItemList(itemIdList);
		if (!ret) {
			System.out.println("not contain item list");
			return;
		}

		// 4.注册回调
		opcClient.subscribe((observable, arg) -> {
			Result result = (Result) arg;
			System.out.println("update result=" + result);
		});

		// 5.添加监听检测点的数据
		// client和server在不同网段，可以访问
		opcClient.syncReadObject("TEST.FA", 500);

		// client和server在不同网段，访问失败，比如：server为10.1.1.132，该网段下面又连接了扩展路由器，192.168.1.x，client为192.168.1.100
		opcClient.asyncReadObject("TEST.FB", 500);

		// 延迟
		delay();
	}

	/**
	 * 延迟
	 */
	private static void delay() {
		try {
			Thread.sleep(300000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
