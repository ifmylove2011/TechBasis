package com.xter.websocket;

import com.google.gson.JsonObject;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class WebClient extends WebSocketClient {
	public WebClient(String url) {
		super(URI.create(url));
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {
		System.out.println("--------");
		System.out.println(serverHandshake.getHttpStatusMessage());
		System.out.println("--------");
		JsonObject joRoot = new JsonObject();
		JsonObject joBody = new JsonObject();
		joBody.addProperty("clientType",7);
		joBody.addProperty("id","XTER-WORK");
		joRoot.add("req",joBody);
		joRoot.addProperty("action","register");
	}

	@Override
	public void onMessage(String s) {
		System.out.println("message received");
		System.out.println(s);
	}

	@Override
	public void onClose(int i, String s, boolean b) {
		System.out.println(s);
	}

	@Override
	public void onError(Exception e) {
		e.printStackTrace();
	}
}
