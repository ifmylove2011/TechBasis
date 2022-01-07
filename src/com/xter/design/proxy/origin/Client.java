package com.xter.design.proxy.origin;

public class Client implements IBusiness {
	@Override
	public void doSomeThing() {
		System.out.println("client do some");
	}

	@Override
	public void doAnyThing() {
		System.out.println("client do any");
	}

}
