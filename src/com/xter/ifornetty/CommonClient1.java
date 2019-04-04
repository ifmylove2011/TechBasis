package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientEncoder;
import com.xter.ifornetty.common.TCPClient;
import com.xter.util.L;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;

public class CommonClient1 {
	public static void main(String[] args) {
		TCPClient tcpClient = new TCPClient.Builder()
				.assign(3)
				.handler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel channel) throws Exception {
						L.d(Thread.currentThread().getName());
						channel.pipeline().addLast(new NormalClientEncoder());
						channel.pipeline().addLast(new IdleStateHandler(20, 10, 20));
						channel.pipeline().addLast(new NormalClientHeartBeatHandler());
						channel.pipeline().addLast(new NormalClientHandler());
					}
				})
				.setRemoteAddress("127.0.0.1", 8000)
				.build();
		tcpClient.connect();
	}
}
