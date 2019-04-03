package com.xter.ifornetty.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UDPBroadCastClient {
	private EventLoopGroup workerLoop;

	private Bootstrap bootstrap;

	private ChannelHandler handler;

	private int port;

	public UDPBroadCastClient() {
		bootstrap = new Bootstrap();
	}

	public void bind() {
		bootstrap.bind();
	}

	public void shut() {
		workerLoop.shutdownGracefully();
	}

	public static class Builder {
		private UDPBroadCastClient target = new UDPBroadCastClient();

		public Builder assign(int bossThreads) {
			target.workerLoop = new NioEventLoopGroup(bossThreads);
			return this;
		}

		public Builder handler(ChannelHandler handler) {
			target.handler = handler;
			return this;
		}

		public UDPBroadCastClient build() {
			if (target.workerLoop == null) {
				target.workerLoop = new NioEventLoopGroup();
			}
			target.bootstrap
					.group(target.workerLoop)
					.channel(NioDatagramChannel.class)
					.option(ChannelOption.SO_BROADCAST,true)
			;
			if (target.handler != null) {
				target.bootstrap.handler(target.handler);
			}
			//广播发起者默认设置端口为0
			target.bootstrap.localAddress(target.port);
			return target;
		}
	}
}
