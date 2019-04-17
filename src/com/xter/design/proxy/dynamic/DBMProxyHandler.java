package com.xter.design.proxy.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DBMProxyHandler<T> implements InvocationHandler {
	private T origin;

	public DBMProxyHandler(T origin) {
		this.origin = origin;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		method.invoke(this.origin, args);
		return null;
	}
}
