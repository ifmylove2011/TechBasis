package com.xter.design.proxy.origin;

public class ProxyClient implements IBusiness {

	private IBusiness client;

	public ProxyClient(IBusiness client) {
		this.client = client;
	}

	@Override
	public void doSomeThing() {
		client.doSomeThing();
	}

	@Override
	public void doAnyThing() {
		client.doAnyThing();
	}
}
