package com.xter.design.proxy.dynamic;

import com.xter.design.proxy.DBM;
import com.xter.design.proxy.IDBM;

import java.lang.reflect.Proxy;

public class Main {
	public static void main(String[] args) {
		DBM dbm = new DBM();
		IDBM idbm = (IDBM) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{IDBM.class}, new DBMProxyHandler<>(dbm));
		idbm.quest();
	}
}
