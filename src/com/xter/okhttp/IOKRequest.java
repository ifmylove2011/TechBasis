package com.xter.okhttp;

import okhttp3.Headers;
import okhttp3.RequestBody;

/**
 * Created by XTER on 2017/8/3.
 * OKHttp专用
 */
public interface IOKRequest {

	String getRequestUrl();

	RequestBody getRequestBody();

	Headers getHeaders();
}
