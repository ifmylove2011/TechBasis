package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientDecoder;
import com.xter.ifornetty.common.TCPServer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

public class CommonServer1 {

	public static void main(String[] args) {
		TCPServer tcpServer = new TCPServer.Builder()
				.childHandler(new ChannelInitializer<Channel>() {
					@Override
					protected void initChannel(Channel channel) throws Exception {
//						channel.pipeline().addLast(new LengthFieldBasedFrameDecoder(1024, 4, 4, 0, 8));
						channel.pipeline().addLast(new NormalClientDecoder());
						channel.pipeline().addLast(new NormalServerHandler());
					}
				})
				.setPort(8000)
				.build();
		tcpServer.bind();
	}
}
