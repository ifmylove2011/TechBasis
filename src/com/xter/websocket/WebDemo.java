package com.xter.websocket;

import com.google.gson.JsonObject;

import org.java_websocket.client.WebSocketClient;

import java.util.concurrent.TimeUnit;


public class WebDemo {


	public static void main(String[] args)throws Exception{
		String uri ="wss://nb.lonbon.com/locationCare/webSocketServer";

		WebSocketClient client = new WebClient(uri);
		client.connect();

	}
}
