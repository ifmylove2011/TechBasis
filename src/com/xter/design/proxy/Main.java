package com.xter.design.proxy;

public class Main {
	public static void main(String[] args) {
		IDBM idbm = new DBMProxy();
		idbm.quest();
	}
}
