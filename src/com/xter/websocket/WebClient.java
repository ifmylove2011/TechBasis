package com.xter.websocket;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/11/3
 * 描述:
 */
public class WebClient extends WebSocketClient {
	public WebClient(URI serverUri) {
		super(serverUri);
	}

	@Override
	public void onOpen(ServerHandshake serverHandshake) {

	}

	@Override
	public void onMessage(String s) {

	}

	@Override
	public void onClose(int i, String s, boolean b) {

	}

	@Override
	public void onError(Exception e) {

	}
}
