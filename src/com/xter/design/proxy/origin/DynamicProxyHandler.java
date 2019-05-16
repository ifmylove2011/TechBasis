package com.xter.design.proxy.origin;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicProxyHandler implements InvocationHandler {

	private IBusiness client;

	public DynamicProxyHandler(IBusiness client) {
		this.client = client;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(client, args);
	}
}
