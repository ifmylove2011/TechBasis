package com.xter.design.proxy.dynamic;

import com.xter.design.proxy.DBM;
import com.xter.design.proxy.IDBM;

import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) {
		DBM dbm = new DBM();
		IDBM idbm = (IDBM) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IDBM.class}, new DBMProxyHandler<>(dbm));
		idbm.quest();

		System.out.println(just(1));
	}

	public static boolean just(int num){
		return isOne(num)||isTwo(num);
	}

	public static boolean isOne(int num){
		System.out.println("isone");
		return num ==1;
	}

	public static boolean isTwo(int num){
		System.out.println("istwo");
		return num ==2;
	}
}
