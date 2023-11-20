package com.xter.okhttp;

import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.xter.okhttp.model.AlarmObject;
import com.xter.okhttp.model.DeviceObject;
import com.xter.okhttp.model.HeartDataObject;
import com.xter.okhttp.model.OfflineDeviceObject;

import org.jetbrains.annotations.Nullable;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 先通过http接口获取token，
 * 再连接websocket服务，
 * 并利用token进行注册，
 * 最后一直监听推送的消息。
 *
 * 来邦的pro环境：websocketUri: wss://nb.lonbon.com/locationCare/webSocketServer
 *   appid: 2da601f102714750a53d95e43822d7a2
 *   secret: 82ad526a79c
 *   prefix: https://nb.lonbon.com/locationCare
 *   communityId: 136514345798237071
 *   buildingId: 136514728050327080
 *   careLevelId: 135414333806765755
 *
 * http平台网关地址：
 *
 * http://119.90.39.6:11019/nblot/gateIndex
 * http://119.90.39.6:11019/gateway/getwlog?path=log/gateway11019.log&w=20000
 *
 *
 * 测试环境（前面的pro环境）
 *
 * https://nb.lonbon.com/locationCare
 *
 * appId:2da601f102714750a53d95e43822d7a2
 *
 * appSecret: EwhgpIEhlTncsdlevR71JgrhuT3Ul/i1J3cZR+XtU3vJMk4+B8QJU52rdjqYbtOFQilfbnid1UW2PMzTtympl7iDoOscQ+S8FXz78P7KwKsQQTiEFx4QuVCLyvFICtX+3XHCsPkRKxSX3m0Clv6S/375jTxEaVHD62/2fk9W3dHyo5bwNN1PNLgPZc2p00tA8y2BOfuuwJRDMbUxlSCmJidDsEoKXSA0lZbGDslkx81oU0Z+FVWzWMqWuLs2fvdbgvaPprjAEOvXV+oSPACsWupsGs9ghpYObRUScpvnmeZxugQ2enlkcTrWabU67kKQ480S8bQnaIVuf875p/73dg==
 */
@Slf4j
public class NbLotWebSocketManager {

	private static String TOKEN_URL = "https://nb.lonbon.com";
	private static String TOKEN_REQUEST_URL = "/locationCare/api/v1.0/org/app/token";

	private static String WEB_URI = "wss://nb.lonbon.com/locationCare/webSocketServer";

	private static String APP_ID = "2da601f102714750a53d95e43822d7a2";
	private static String APP_SECRET = "EwhgpIEhlTncsdlevR71JgrhuT3Ul/i1J3cZR+XtU3vJMk4+B8QJU52rdjqYbtOFQilfbnid1UW2PMzTtympl7iDoOscQ+S8FXz78P7KwKsQQTiEFx4QuVCLyvFICtX+3XHCsPkRKxSX3m0Clv6S/375jTxEaVHD62/2fk9W3dHyo5bwNN1PNLgPZc2p00tA8y2BOfuuwJRDMbUxlSCmJidDsEoKXSA0lZbGDslkx81oU0Z+FVWzWMqWuLs2fvdbgvaPprjAEOvXV+oSPACsWupsGs9ghpYObRUScpvnmeZxugQ2enlkcTrWabU67kKQ480S8bQnaIVuf875p/73dg==";

	private static String KEY_ACTION = "action";
	private static String KEY_MSG_TYPE = "msgType";
	private static String KEY_BODY = "body";

	/**
	 * 新报警信息
	 * {@link com.xter.okhttp.model.AlarmObject}
	 */
	private static final int MSG_ALARM_NEW = 2;
	/**
	 * 报警被现场处理
	 * {@link com.xter.okhttp.model.AlarmObject}
	 */
	private static final int MSG_ALARM_HANDLE = 3;
	/**
	 * GPS信息更新
	 * {@link com.xter.okhttp.model.AlarmObject}
	 */
	private static final int MSG_ALARM_GPS = 12;
	/**
	 * 按钮报警被现场处理
	 * {@link com.xter.okhttp.model.AlarmObject}
	 */
	private static final int MSG_ALARM_BUTTON_HANDLE = 21;
	/**
	 * 设备离线
	 * {@link com.xter.okhttp.model.OfflineDeviceObject}
	 */
	private static final int MSG_DEVICE_OFFLINE = 9;
	/**
	 * 设备信息更新
	 * {@link com.xter.okhttp.model.DeviceObject}
	 */
	private static final int MSG_DEVICE_UPDATE = 23;
	/**
	 * 手表/求救定位器心跳数据
	 * {@link com.xter.okhttp.model.HeartDataObject}
	 */
	private static final int MSG_HEART = 53;

	/**
	 * appId异地登录
	 */
	private static final int OTHER_LOGIN = 11;
	/**
	 * 连接返回
	 */
	private static final int MSG_CONNECT = 0;
	/**
	 * 注册返回
	 */
	private static final int MSG_REGISTER = 5;
	/**
	 * 轨迹更新
	 */
	private static final int TRACK_UPDATE = 14;

	/*---------------------- websocket构建 ----------------------*/

	private static class OkWebSocketHolder {
		private static final NbLotWebSocketManager INSTANCE = new NbLotWebSocketManager();
	}

	public static NbLotWebSocketManager get() {
		return OkWebSocketHolder.INSTANCE;
	}

	private NbLotWebSocketManager() {

	}

	private static final int CLOSURE_STATUS = 1000;
	private WebSocket webSocket;
	/**
	 * 需要通过http接口获取
	 */
	private String token;

	/* ---------------------- 基本方法 ----------------------*/

	public void startConnect() {
		webSocket = OkHttpManager.get().createWebSocket(WEB_URI, nbLotListener);
	}

	public synchronized void closeWebSocket() {
		if (webSocket != null) {
			webSocket.close(CLOSURE_STATUS, "bye!");
			webSocket = null;
		}
	}

	public synchronized void resetWebSocket() {
		webSocket = null;
	}

	public void sendMessage(String text) {
		if (webSocket != null) {
			log.info(text);
			System.out.println(text);
			webSocket.send(text);
		}
	}

	/*---------------------- 主要业务 ----------------------*/

	/**
	 * 2.1
	 * 连接后发送注册
	 */
	private void sendRegister() {
		JsonObject joRoot = new JsonObject();

		JsonObject joBody = new JsonObject();
		joBody.addProperty("clientType", 7);
		joBody.addProperty("id", token);

		joRoot.add("req", joBody);
		joRoot.addProperty("action", "register");

		System.out.println(joRoot);
		if (webSocket != null) {
			webSocket.send(joRoot.toString());
		}
	}

	/**
	 * 2.3
	 * 心跳检测
	 */
	private void sendHeart() {
		JsonObject joHeart = new JsonObject();
		joHeart.addProperty(KEY_ACTION, "keepAlive");
		sendMessage(joHeart.toString());
	}

	/**
	 * 2.4
	 * 第三方应用报警接收响应
	 *
	 * @param alarmId 在上报的报警信息中获取
	 */
	private void sendAlarmFeedback(String alarmId) {
		JsonObject joRoot = new JsonObject();

		JsonObject joBody = new JsonObject();
		joBody.addProperty("alarmId", alarmId);

		joRoot.add("req", joBody);
		joRoot.addProperty("action", "receiveAlarm");

		System.out.println(joRoot);
		if (webSocket != null) {
			webSocket.send(joRoot.toString());
		}
	}

	/**
	 * 接收数据心跳回复
	 *
	 * @param recordId
	 */
	private void sendHeartFeedback(String recordId) {
		JsonObject joRoot = new JsonObject();

		JsonObject joBody = new JsonObject();
		joBody.addProperty("clientType", 7);
		joBody.addProperty("recordId", recordId);

		joRoot.add("req", joBody);
		joRoot.addProperty("action", "appConfirm");

		System.out.println(joRoot);
		if (webSocket != null) {
			webSocket.send(joRoot.toString());
		}
	}


	/**
	 * 2.2
	 * 接收推送的消息
	 *
	 * @param text 消息
	 */
	private void checkMessage(String text) {
		JsonObject joMsg = GsonUtil.getGson().fromJson(text, JsonObject.class);
		int msgType = joMsg.get(KEY_MSG_TYPE).getAsInt();
		switch (msgType) {
			case MSG_ALARM_NEW:
			case MSG_ALARM_HANDLE:
			case MSG_ALARM_BUTTON_HANDLE:
			case MSG_ALARM_GPS:
				Message<AlarmObject> msgAlarm = GsonUtil.getGson().fromJson(text, new TypeToken<AlarmObject>() {
				}.getType());
				System.out.println("alarmId:" + msgAlarm.body.alarmId);
				sendAlarmFeedback(msgAlarm.body.alarmId);
				break;
			case MSG_DEVICE_OFFLINE:
				Message<OfflineDeviceObject> msgOffline = GsonUtil.getGson().fromJson(text, new TypeToken<OfflineDeviceObject>() {
				}.getType());
				System.out.println("deviceId:" + msgOffline.body.deviceId);
				System.out.println("deviceDesc:" + msgOffline.body.deviceDesc);
				break;
			case MSG_DEVICE_UPDATE:
				Message<DeviceObject> msgDevice = GsonUtil.getGson().fromJson(text, new TypeToken<DeviceObject>() {
				}.getType());
				System.out.println("deviceId:" + msgDevice.body.deviceId);
				System.out.println("status:" + msgDevice.body.status);
				break;
			case MSG_HEART:
				Message<HeartDataObject> msgHeart = GsonUtil.getGson().fromJson(text, new TypeToken<HeartDataObject>() {
				}.getType());
				System.out.println("iotDeviceId:" + msgHeart.body.iotDeviceId);
				System.out.println("recordId:" + msgHeart.body.recordId);
				break;
			case TRACK_UPDATE:
				JsonObject joTrack = GsonUtil.getGson().fromJson(text, JsonObject.class);
				System.out.println("轨迹更新");
				System.out.println(text);
				break;
			case MSG_CONNECT:
				JsonObject joConnect = GsonUtil.getGson().fromJson(text, JsonObject.class);
				System.out.println("连接返回");
				System.out.println(text);
				break;
			case MSG_REGISTER:
				JsonObject joRegister = GsonUtil.getGson().fromJson(text, JsonObject.class);
				System.out.println("注册返回");
				System.out.println(text);
				sendHeart();
				break;
			case OTHER_LOGIN:
				JsonObject joLoginException = GsonUtil.getGson().fromJson(text, JsonObject.class);
				System.out.println("appId异地登录");
				System.out.println(text);
				break;
			//TODO 也许需要抢登录
//				requestToken(new TokenGetListener() {
//					@Override
//					public void onSuccess() {
//						startConnect();
//					}
//				});
		}
	}

	/*---------------------- 相关方法 ----------------------*/

	/**
	 * 通过http请求TOKEN；
	 * 有appId经常异地登录的问题，因此加个Listener监听，获得token再连接websocket；
	 */
	public void requestToken(TokenGetListener listener) {
		if (token != null) {
			return;
		}
		OkHttpManager.postAsync(new IOKRequest() {
			@Override
			public String getRequestUrl() {
				return TOKEN_URL + TOKEN_REQUEST_URL;
			}

			@Override
			public RequestBody getRequestBody() {
				return new FormBody.Builder()
						.add("appId", APP_ID)
						.add("appSecret", APP_SECRET)
						.build();
			}

			@Override
			public Headers getHeaders() {
				return new Headers.Builder()
						.add("Content-Type", "application/x-www-form-urlencoded; charset=utf-8")
						.add("Accept", "*/*")
						.build();
			}
		}, new IOKResponse() {
			@Override
			public void onResponse(String response) {
				System.out.println(response);
				JsonObject jo = GsonUtil.getGson().fromJson(response, JsonObject.class);
				if (jo.get("status").getAsString().equals("200")) {
					token = jo.get("body").getAsJsonObject().get("token").getAsString();
					if (listener != null) {
						listener.onSuccess();
					}
				}
			}

			@Override
			public void onError(Exception ex) {
				ex.printStackTrace();
			}
		});
	}


	/*---------------------- 工具实例 ----------------------*/

	private WebSocketListener nbLotListener = new WebSocketListener() {
		@Override
		public void onOpen(WebSocket webSocket, Response response) {
			System.out.println("open");
			sendRegister();
		}

		@Override
		public void onMessage(WebSocket webSocket, String text) {
			System.out.println("Received Message");
			checkMessage(text);
		}

		@Override
		public void onMessage(WebSocket webSocket, ByteString bytes) {
			super.onMessage(webSocket, bytes);
		}

		@Override
		public void onClosing(WebSocket webSocket, int code, String reason) {
			System.out.println("onClosing~");
		}

		@Override
		public void onClosed(WebSocket webSocket, int code, String reason) {
			System.out.println("close! reason:" + reason);
		}

		@Override
		public void onFailure(WebSocket webSocket, Throwable t, @Nullable Response response) {
			t.printStackTrace();
		}
	};

	/**
	 * 需要知道token是否拿到
	 */
	public interface TokenGetListener {
		void onSuccess();
	}

	private static class Message<T> {
		String msg;
		int msgType;
		int status;
		T body;

		public Message(String msg, int msgType, int status, T body) {
			this.msg = msg;
			this.msgType = msgType;
			this.status = status;
			this.body = body;
		}

		@Override
		public String toString() {
			return "Message{" +
					"msgType=" + msgType +
					", body=" + body +
					'}';
		}
	}

}
