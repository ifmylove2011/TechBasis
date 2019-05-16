package com.xter.design.proxy.origin;

import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) {
		Client client = new Client();
//
//		ProxyClient proxyClient = new ProxyClient(client);
//
//		proxyClient.doSomeThing();

//		IBusiness business = new ProxyClient(new Client());
//		business.doSomeThing();

		IBusiness proxyClient = (IBusiness) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), new DynamicProxyHandler(client));
//		ProxyClient proxyClient = (ProxyClient) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), new DynamicProxyHandler(client));
//		Client proxyClient = (Client) Proxy.newProxyInstance(client.getClass().getClassLoader(), client.getClass().getInterfaces(), new DynamicProxyHandler(client));
		proxyClient.doSomeThing();
	}
}
