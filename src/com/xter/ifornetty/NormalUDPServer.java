package com.xter.ifornetty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NormalUDPServer {
	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap()
					.group(eventLoopGroup)
					.channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST,true)
					.handler(new NormalUDPServerHandler())
					;

			bootstrap.bind(10000).sync().channel().closeFuture().await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
