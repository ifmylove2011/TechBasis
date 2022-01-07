package com.xter.design.proxy.extension;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/11/9
 * 描述:
 */
public class ServerB implements IServer {
	@Override
	public int startServer(int port) {
		System.out.println("Server B start");
		return 1;
	}

	@Override
	public int stopServer() {
		System.out.println("Server B stop");
		return 0;
	}
}
