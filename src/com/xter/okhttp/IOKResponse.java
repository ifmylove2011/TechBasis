package com.xter.okhttp;

/**
 * Created by XTER on 2018/1/4.
 * 解析回应
 */
public interface IOKResponse {
	void onResponse(String response);

	void onError(Exception ex);
}
