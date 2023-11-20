package com.xter.okhttp;


import com.google.gson.Gson;

/**
 * Created by XTER on 2017/9/13.
 * gson用
 */
public class GsonUtil {

	private static class GsonHolder{

		private static final Gson INSTANCE=new Gson();
	}

	public static Gson getGson(){
		return GsonHolder.INSTANCE;
	}
}
