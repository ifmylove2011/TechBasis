package com.xter.coap;

import com.google.gson.JsonObject;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.coap.MessageObserverAdapter;
import org.eclipse.californium.core.coap.Request;
import org.eclipse.californium.core.coap.Response;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.AddressEndpointContext;
import org.eclipse.californium.elements.util.NetworkInterfacesUtil;

import java.net.Inet4Address;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class CoapManager {

	private static class Holder {
		static final CoapManager INSTANCE = new CoapManager();
	}

	public static CoapManager getInstance() {
		return Holder.INSTANCE;
	}

	static final boolean LOOPBACK = false;
	static final int PORT = 5683;

	private ScheduledExecutorService ses;
	private CoapServer coapServer;

	private CoapManager() {
		ses = Executors.newSingleThreadScheduledExecutor();
	}

	public void post(String url, String content, CoapHandler handler) {
		CoapClient coapClient = new CoapClient(url);
		coapClient.post(handler, content, MediaTypeRegistry.APPLICATION_JSON);
	}

	public void startCoapServer() {
		if (coapServer != null && coapServer.isRunning()) {
			System.out.println("coap服务已开启");
			return;
		}
		ses.execute(new Runnable() {
			@Override
			public void run() {
				coapServer = new CoapServer(5683);
				CoapResource root = new CoapResource("qlink");
				root.add(new CoapResource("guide") {
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println(exchange.getSourceSocketAddress().getHostString());
						System.out.println(getURI());
						System.out.println(exchange.getRequestText());
						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("respCode", 1);
						jsonObject.addProperty("respCont", "");

						Response response = new Response(CoAP.ResponseCode.CONTENT);
						System.out.println(jsonObject.toString());
						response.setPayload(jsonObject.toString());
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				root.add(new CoapResource("request") {
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println(getURI());
						String text = exchange.getRequestText();
						System.out.println(text);
						String host = exchange.getSourceSocketAddress().getHostString();
						System.out.println(host);
						parseDeviceReqeuest(text, host);

						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("respCode", 1);
						jsonObject.addProperty("respCont", "");

						Response response = new Response(CoAP.ResponseCode.CONTENT);
						System.out.println(jsonObject.toString());
						response.setPayload(jsonObject.toString());
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				root.add(new CoapResource("searchRouter") {
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println(exchange.getSourceSocketAddress().getHostString());
						System.out.println(getURI());
						System.out.println(exchange.getRequestText());

						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("searchAck", "ANDLINK-ROUTER");
						jsonObject.addProperty("andlinkVersion", "V3");

						Response response = new Response(CoAP.ResponseCode.CONTENT);
						System.out.println(jsonObject.toString());
						response.setPayload(jsonObject.toString());
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				root.add(new CoapResource("searchgw") {
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println(exchange.getSourceSocketAddress().getHostString());
						System.out.println(getURI());
						System.out.println(exchange.getRequestText());

						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("searchAck", "ANDLINK-GW");
						jsonObject.addProperty("andlinkVersion", "V3");

						Response response = new Response(CoAP.ResponseCode.CONTENT);
						System.out.println(jsonObject.toString());
						response.setPayload(jsonObject.toString());
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});
				//设备成功入网后的广播，需要和设备在同一网段，因此理想场景是手机能同时连接热点和连接WIFI，并先一步在WIFI内开启监听，并向设备发送当前连接的WIFI的配置信息使其来到同一网段
				root.add(new CoapResource("success") {
					@Override
					public void handlePOST(CoapExchange exchange) {
						System.out.println(exchange.getSourceSocketAddress().getHostString());
						System.out.println(getURI());
						String resp = exchange.getRequestText();
						System.out.println(resp);

						JsonObject jsonObject = new JsonObject();
						jsonObject.addProperty("result", 1);

						Response response = new Response(CoAP.ResponseCode.CONTENT);
						System.out.println(jsonObject.toString());
						response.setPayload(jsonObject.toString());
						response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
						exchange.respond(response);
					}
				});

				coapServer.add(root);
				coapServer.start();
				System.out.println("开启coap服务");
			}
		});
	}

	public void broadcast(int port,String path, String content) {
		Inet4Address broadcastAddress = NetworkInterfacesUtil.getBroadcastIpv4();
		String url = String.format(Locale.CHINA, "coap://%s:%d%s", broadcastAddress.getHostAddress(), port, path);

		Request request = new Request(CoAP.Code.POST, CoAP.Type.NON);
		request.setPayload(content);
		request.setURI(url);
		request.setDestinationContext(new AddressEndpointContext(NetworkInterfacesUtil.getBroadcastIpv4(), port));

		request.addMessageObserver(new MessageObserverAdapter() {
			@Override
			public void onResponse(Response response) {
				System.out.println(response.toString());
				System.out.println(response.getPayloadString());
			}
		});
		request.send();
	}

	private void parseDeviceReqeuest(String text, String host) {
//		JsonObject jo = GsonUtil.getGson().fromJson(text, JsonObject.class);
//		String mac = jo.get("iotDeviceMac").getAsString();
//		String deviceType = jo.get("iotDeviceType").getAsString();
	}


}
