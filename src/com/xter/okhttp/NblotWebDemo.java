package com.xter.okhttp;

public class NblotWebDemo {


	public static void main(String[] args) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				NbLotWebSocketManager.get().requestToken(new NbLotWebSocketManager.TokenGetListener() {
					@Override
					public void onSuccess() {
						NbLotWebSocketManager.get().startConnect();
					}
				});
			}
		}).start();
	}

}
