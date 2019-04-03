package com.xter.ifornetty.common;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class TCPClient {
	private EventLoopGroup workerLoop;

	private Bootstrap bootstrap;

	private ChannelHandler handler;

	private String host;
	private int port;

	public void connect() {
		bootstrap.connect();
	}

	public void shut() {
		workerLoop.shutdownGracefully();
	}

	public TCPClient() {
		bootstrap = new Bootstrap();
	}

	public static class Builder {
		private TCPClient target = new TCPClient();

		public Builder assign(int workerThreads) {
			target.workerLoop = new NioEventLoopGroup(workerThreads);
			return this;
		}

		public Builder option(ChannelOption channelOption, Object value) {
			target.bootstrap.option(channelOption, value);
			return this;
		}

		public Builder handler(ChannelHandler channelHandler) {
			target.handler = channelHandler;
			return this;
		}

		public Builder setRemoteAddress(String host, int port) {
			target.host = host;
			target.port = port;
			return this;
		}

		public TCPClient build() {
			if (target.workerLoop == null) {
				target.workerLoop = new NioEventLoopGroup();
			}

			target.bootstrap
					.group(target.workerLoop)
					.channel(NioSocketChannel.class)
			;

			if (target.handler != null) {
				target.bootstrap.handler(target.handler);
			}

			if (target.host != null && target.port != 0) {
				target.bootstrap.remoteAddress(target.host, target.port);
			}

			return target;
		}
	}

}
