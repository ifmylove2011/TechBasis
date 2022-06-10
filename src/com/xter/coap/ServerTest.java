package com.xter.coap;


import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.core.server.resources.Resource;

public class ServerTest {
	public static void main(String[] args) {

		new Thread(new Runnable() {
			@Override
			public void run() {
				CoapServer coapServer = new CoapServer(5683);
				Resource root = new CoapResource("qlink");
				root.add(new CoapResource("success"){
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println("--------"+getURI());
						System.out.println(exchange.getRequestText());
						Response response = new Response(CoAP.ResponseCode.CONTENT);
						response.setPayload("{\"result\":1}");
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				root.add(new CoapResource("regist"){
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println("--------"+getURI());
						System.out.println(exchange.getRequestText());
						Response response = new Response(CoAP.ResponseCode.CONTENT);
						response.setPayload("{\"result\":1}");
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				coapServer.add(root);

				coapServer.start();
			}
		}).start();
	}

}
