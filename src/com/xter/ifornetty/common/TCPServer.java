package com.xter.ifornetty.common;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TCPServer {

	private EventLoopGroup bossLoop;
	private EventLoopGroup workerLoop;

	private ServerBootstrap serverBootstrap;

	/**
	 * 连接前的handler
	 */
	private ChannelHandler handler;

	/**
	 * 连接后的handler，若初始化逻辑较复杂，最好使用继承{@link ChannelInitializer}
	 */
	private ChannelHandler childHandler;

	private int port;

	private TCPServer() {
		serverBootstrap = new ServerBootstrap();
	}

	public void bind() {
		serverBootstrap.bind();
	}

	public void shut() {
		bossLoop.shutdownGracefully();
		workerLoop.shutdownGracefully();
	}

	public static class Builder {
		private TCPServer target = new TCPServer();

		public Builder assign(int bossThreads, int workerThreads) {
			target.bossLoop = new NioEventLoopGroup(bossThreads);
			target.workerLoop = new NioEventLoopGroup(workerThreads);
			return this;
		}

		public Builder option(ChannelOption channelOption, Object value) {
			target.serverBootstrap.option(channelOption, value);
			return this;
		}

		public Builder childOption(ChannelOption channelOption, Object value) {
			target.serverBootstrap.childOption(channelOption, value);
			return this;
		}

		public Builder handler(ChannelHandler channelHandler) {
			target.handler = channelHandler;
			return this;
		}

		public Builder childHandler(ChannelHandler channelHandler) {
			target.childHandler = channelHandler;
			return this;
		}

		public Builder setPort(int port) {
			target.port = port;
			return this;
		}

		public TCPServer build() {
			if (target.bossLoop == null) {
				target.bossLoop = new NioEventLoopGroup();
			}
			if (target.workerLoop == null) {
				target.workerLoop = new NioEventLoopGroup();
			}
			target.serverBootstrap
					.group(target.bossLoop, target.workerLoop)
					.channel(NioServerSocketChannel.class)
			;

			if (target.handler != null) {
				target.serverBootstrap.handler(target.handler);
			}

			if (target.childHandler != null) {
				target.serverBootstrap.childHandler(target.childHandler);
			}

			if (target.port != 0) {
				target.serverBootstrap.localAddress(target.port);
			}
			return target;
		}
	}

	@Override
	public String toString() {
		return "TCPServer{" +
				"serverBootstrap=" + serverBootstrap +
				", port=" + port +
				'}';
	}
}
