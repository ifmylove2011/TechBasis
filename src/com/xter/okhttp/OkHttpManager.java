package com.xter.okhttp;


import java.io.File;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

/**
 * Created by XTER on 2017/12/26.
 * 网络管理，基于OkHttp
 */
public class OkHttpManager {
	public static final MediaType FORM_DATA_TYPE  = MediaType.parse("multipart/form-data");

	public static final MediaType FORM_CONTENT_TYPE
			= MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
	private static final int TIME_OUT = 60;
	private final OkHttpClient okHttpClient;
	private WebSocket webSocket;

	private CookieJar cookieJar = new CookieJar() {
		private final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

		@Override
		public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
			cookieStore.put(url.host(), cookies);
		}

		@Override
		public List<Cookie> loadForRequest(HttpUrl url) {
			List<Cookie> cookies = cookieStore.get(url.host());
			return cookies != null ? cookies : new ArrayList<Cookie>();
		}
	};

	private OkHttpManager() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder()
				.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
				.writeTimeout(TIME_OUT, TimeUnit.SECONDS)
				.readTimeout(TIME_OUT, TimeUnit.SECONDS)
				.callTimeout(TIME_OUT, TimeUnit.SECONDS);

		okHttpClient = builder.build();
	}

	private static class OkHttpHolder {
		private static final OkHttpManager INSTANCE = new OkHttpManager();
	}

	public static OkHttpManager get() {
		return OkHttpHolder.INSTANCE;
	}

	private static SSLSocketFactory createSSLSocketFactory() {
		SSLSocketFactory ssfFactory = null;
		try {
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
			ssfFactory = sc.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return ssfFactory;
	}

	public static class MyTrustManager implements X509TrustManager {
		@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
		}

		@Override
		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}
	}

	/*-----------------内部调用-----------------*/

	/**
	 * 以GET方式请求
	 *
	 * @param url        目标地址
	 * @param okRequest  请求接口
	 * @param okResponse 应答接口
	 */
	private void getAsyn(String url, final IOKRequest okRequest, final IOKResponse okResponse) {
		String requestUrl = url + okRequest.getRequestUrl();
		final Request request = new Request.Builder().url(requestUrl).build();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				okResponse.onError(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				okResponse.onResponse(response.body().string());
			}
		});
	}

	/**
	 * 以Multi格式POST请求
	 *
	 * @param url      目标地址
	 * @param map      参数
	 * @param file     文件
	 * @param callback 回调
	 */
	private void postAsyn(String url, Map<String, String> map, File file, Callback callback) {
		MultipartBody.Builder requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM);
		if (file != null) {
			RequestBody body = RequestBody.create(MediaType.parse("image/*"), file);
			requestBody.addFormDataPart("file", file.getName(), body);
		}
		if (map != null) {
			for (Map.Entry entry : map.entrySet()) {
				requestBody.addFormDataPart(String.valueOf(entry.getKey()), null, RequestBody.create(FORM_CONTENT_TYPE, String.valueOf(entry.getValue())));
			}
		}
		Request request = new Request.Builder()
				.url(url)
				.post(requestBody.build())
				.build();
		okHttpClient.newCall(request).enqueue(callback);
	}

	/**
	 * 以POST方式请求
	 *
	 * @param url        目标地址
	 * @param okRequest  请求接口
	 * @param okResponse 应答接口
	 */
	private void postAsyn(String url, final IOKRequest okRequest, final IOKResponse okResponse) {
		Request request = new Request.Builder()
				.url(url)
				.post(okRequest.getRequestBody())
				.build();
		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				okResponse.onError(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				okResponse.onResponse(response.body().string());
			}
		});
	}

	/**
	 * POST请求
	 *
	 * @param okRequest  请求信息
	 * @param okResponse 应答信息
	 */
	private void postAsyn(final IOKRequest okRequest, final IOKResponse okResponse) {
		Request request = new Request.Builder()
				.url(okRequest.getRequestUrl())
				.headers(okRequest.getHeaders())
				.post(okRequest.getRequestBody())
				.build();

		okHttpClient.newCall(request).enqueue(new Callback() {
			@Override
			public void onFailure(Call call, IOException e) {
				okResponse.onError(e);
			}

			@Override
			public void onResponse(Call call, Response response) throws IOException {
				try {
					okResponse.onResponse(response.body().string());
				} catch (Exception e) {
					System.out.println(call.request().url() + "," + e.getMessage());
					okResponse.onError(e);
				}
			}
		});
	}

	/*-----------------websocket-----------------*/
	WebSocket createWebSocket(String url, WebSocketListener listener) {
		if (webSocket == null) {
			Request request = new Request.Builder().url(url).build();
			webSocket = okHttpClient.newWebSocket(request, listener);
		}
		return webSocket;
	}

	/*-----------------外部调用-----------------*/

	public static void getAsync(String url, IOKRequest req, IOKResponse res) {
		get().getAsyn(url, req, res);
	}

	public static void postAsync(String url, Map<String, String> map, File file, Callback callback) {
		get().postAsyn(url, map, file, callback);
	}

	public static void postAsync(String url, IOKRequest req, IOKResponse res) {
		get().postAsyn(url, req, res);
	}

	public static void postAsync(IOKRequest req, IOKResponse res) {
		get().postAsyn(req, res);
	}

}
