package com.xter.ifornetty.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UDPBroadCastServer {
	private EventLoopGroup bossLoop;

	private Bootstrap bootstrap;

	private ChannelHandler handler;

	private int port;

	public UDPBroadCastServer() {
		bootstrap = new Bootstrap();
	}

	public void bind() {
		bootstrap.bind();
	}

	public void shut() {
		bossLoop.shutdownGracefully();
	}

	public static class Builder {
		private UDPBroadCastServer target = new UDPBroadCastServer();

		public Builder assign(int bossThreads) {
			target.bossLoop = new NioEventLoopGroup(bossThreads);
			return this;
		}

		public Builder handler(ChannelHandler handler) {
			target.handler = handler;
			return this;
		}

		public UDPBroadCastServer build() {
			if (target.bossLoop == null) {
				target.bossLoop = new NioEventLoopGroup();
			}
			target.bootstrap
					.group(target.bossLoop)
					.channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST,true)
			;
			if (target.handler != null) {
				target.bootstrap.handler(target.handler);
			}
			if (target.port != 0) {
				target.bootstrap.localAddress(target.port);
			}
			return target;
		}
	}
}
