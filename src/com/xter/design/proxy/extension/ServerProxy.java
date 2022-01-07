package com.xter.design.proxy.extension;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2021/11/9
 * 描述:
 */
public class ServerProxy implements InvocationHandler {

	private static class Holder{
		private static ServerProxy INSTANCE = new ServerProxy();
	}

	public static ServerProxy getInstance(){
		return Holder.INSTANCE;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(proxy.getClass().getName());
		System.out.println(method.getName());
		System.out.println(Arrays.toString(args));
		return 0;
	}
}
