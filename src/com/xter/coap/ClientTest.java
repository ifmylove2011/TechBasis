package com.xter.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.MessageObserverAdapter;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.elements.AddressEndpointContext;
import org.eclipse.californium.elements.config.Configuration;
import org.eclipse.californium.elements.util.NetworkInterfacesUtil;

import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.util.Locale;

public class ClientTest {

	public static void main(String[] args) {
		post();
	}

	private static void post() {
		CoapClient coapClient = new CoapClient("coap://192.168.21.104:5683/qlink/success");
		coapClient.post(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse coapResponse) {
				System.out.println(coapResponse.getResponseText());
			}

			@Override
			public void onError() {

			}
		}, "{\"deviceMac\":\"ddddddddd\",\"deviceType\":\"ddddddddd\"}", MediaTypeRegistry.APPLICATION_JSON);
	}

	private static void broadcast(int port, String path, String content,MessageObserverAdapter observerAdapter) {
		Inet4Address broadcastAddress = NetworkInterfacesUtil.getBroadcastIpv4();
		String url = String.format(Locale.CHINA, "coap://%s:%d%s", broadcastAddress.getHostAddress(), port, path);

		Request request = new Request(CoAP.Code.POST, CoAP.Type.NON);
		request.setURI(url);
		request.setPayload(content);

		request.addMessageObserver(observerAdapter);
		request.send();
	}
}
