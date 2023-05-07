package com.xter.design.proxy.extension;

import java.lang.reflect.Proxy;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/11/9
 * 描述:
 */
public class Main {
	public static void main(String[] args) throws ClassNotFoundException {
		Class<?> clazzA = Class.forName("com.xter.design.proxy.extension.ServerA");
		IServer serverA = (IServer) Proxy.newProxyInstance(clazzA.getClassLoader(),clazzA.getInterfaces(),ServerProxy.getInstance());
		serverA.startServer(120);
		serverA.stopServer();

		Class<?> clazzB = Class.forName("com.xter.design.proxy.extension.ServerB");
		IServer serverB = (IServer) Proxy.newProxyInstance(clazzB.getClassLoader(),clazzB.getInterfaces(),ServerProxy.getInstance());
		serverB.startServer(121);
		serverB.stopServer();
	}
}
