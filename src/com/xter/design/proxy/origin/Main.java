package com.xter.design.proxy.origin;

import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) throws ClassNotFoundException {
		Client client = new Client();
//
//		ProxyClient proxyClient = new ProxyClient(client);
//
//		proxyClient.doSomeThing();

//		IBusiness business = new ProxyClient(new Client());
//		business.doSomeThing();

		Class<?> clazz = Class.forName("com.xter.design.proxy.origin.Client");
		IBusiness proxyClient = (IBusiness) Proxy.newProxyInstance(clazz.getClassLoader(), clazz.getInterfaces(), new DynamicProxyHandler());
//		ProxyClient proxyClient = (ProxyClient) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), new DynamicProxyHandler(client));
//		Client proxyClient = (Client) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), new DynamicProxyHandler(client));
		proxyClient.doSomeThing(2,3);
		proxyClient.doAnyThing("haha");
	}
}
