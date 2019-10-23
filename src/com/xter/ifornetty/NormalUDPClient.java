package com.xter.ifornetty;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class NormalUDPClient {

	public static void main(String[] args) {
		EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
		try{
			Bootstrap bootstrap = new Bootstrap()
					.group(eventLoopGroup)
					.channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST,true)
					.handler(new NormalUDPClientHandler())
					;

			bootstrap.bind(9999).sync().channel().closeFuture().await();
			System.out.println("~~~~~~~~~");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			eventLoopGroup.shutdownGracefully();
		}
	}
}
