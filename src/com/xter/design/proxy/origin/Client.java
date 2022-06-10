package com.xter.design.proxy.origin;

public class Client implements IBusiness {

	@Override
	public void doSomeThing(int code1, int code2) {
		System.out.println("code1="+code1+",code2="+code2);
	}

	@Override
	public void doAnyThing(String result) {
		System.out.println("result="+result);

	}
}
