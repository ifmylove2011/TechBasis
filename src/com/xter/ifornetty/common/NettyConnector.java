package com.xter.ifornetty.common;

import com.xter.util.L;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author XTER
 * @date 2019/5/30
 */
public class NettyConnector {

	private Bootstrap bootstrap;
	private String host;
	private int port;

	private Channel channel;

	private static final long TIME_OUT = 10;

	/**
	 * 重连次数
	 */
	private AtomicInteger recconnectCounter;
	/**
	 * 首次连接次数
	 */
	private AtomicInteger connectCounter;

	public interface IChannelStateListener {
		void onConnectSuccess(Channel channel);

		void onConnectFailed();

		void onDisconnect();
	}

	private IChannelStateListener channelStateListener;

	private NettyConnector(final Builder builder) {
		recconnectCounter = new AtomicInteger(0);
		connectCounter = new AtomicInteger(0);
		bootstrap = builder.bootstrap;
		bootstrap.handler(new ChannelInitializer<Channel>() {
			@Override
			protected void initChannel(Channel channel) throws Exception {
				L.d("~~~~~~~~~~~~~");
				channel.pipeline().addLast(new ListenHandler());
				channel.pipeline().addLast(builder.addedHandlers);
			}
		});
	}

	public void setRemoteAddress(String host, int port) {
		L.d("设置地址与端口-" + host + ":" + port);
		this.host = host;
		this.port = port;
	}


	public void setChannelStateListener(IChannelStateListener listener) {
		channelStateListener = listener;
	}

	public void connect() {
		if (channel == null || !channel.isActive()) {
			bootstrap.remoteAddress(this.host, this.port);

			ChannelFuture channelFuture = bootstrap.connect();

			channelFuture.addListener(new GenericFutureListener<ChannelFuture>() {
				@Override
				public void operationComplete(ChannelFuture f) throws Exception {
					if (f.isSuccess()) {
						L.d("连接(" + bootstrap.config().remoteAddress() + ")成功");
						channel = f.channel();
						if (channelStateListener != null) {
							connectCounter.set(0);
							channelStateListener.onConnectSuccess(channel);
						}
					} else {
						L.d("连接(" + bootstrap.config().remoteAddress() + ")失败");
						f.channel().pipeline().fireChannelInactive();
						if (channelStateListener != null) {
							connectCounter.incrementAndGet();
							channelStateListener.onConnectFailed();
						}
					}
				}
			});
		}
	}

	private void reconnect() {
		if (channel != null && !channel.isActive()) {
			channel.connect(bootstrap.config().remoteAddress()).addListener(new GenericFutureListener<Future<? super Void>>() {
				@Override
				public void operationComplete(Future<? super Void> future) throws Exception {
					L.d(future.isSuccess()+"");
				}
			});
		}
//		if (bootstrap == null)
//			throw new IllegalArgumentException("bootstrap cannot be null");
//		//如果已经连接，则直接【连接成功】
//		if (channel == null || !channel.isActive()) {
//			//连接
//			ChannelFuture future = bootstrap.connect();
//			//响应连接成功或失败
//			if (future.isSuccess()) {
//				//得到会话
//				channel = future.channel();
//			}
//		}
	}

	public void reconnect(final long reconnectTimeoutMills, final int reconnectTimes) {
		try {
			recconnectCounter.set(0);
			while (channel != null && !channel.isActive() && recconnectCounter.incrementAndGet() < reconnectTimes) {
				reconnect();
				L.d("connect?"+channel.isOpen()+","+channel.isActive());
				if (channel.isActive()) {
					break;
				} else {
					TimeUnit.MILLISECONDS.sleep(reconnectTimeoutMills);
				}
				L.d(Thread.currentThread().getName() + "," + "重连" + bootstrap.config().remoteAddress()+ "(" + recconnectCounter.get() + ")次...");
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			if (channel != null && channel.isActive()) {
				if (channelStateListener != null) {
					channelStateListener.onConnectSuccess(channel);
				}
			} else {
				if (channelStateListener != null) {
					channelStateListener.onConnectFailed();
				}
			}
		}
	}

	public static class Builder {

		private Bootstrap bootstrap = new Bootstrap();
		private ChannelHandler[] addedHandlers;

		public Builder group(EventLoopGroup loopGroup) {
			bootstrap.group(loopGroup);
			return this;
		}

		@Deprecated
		public Builder remoteAddress(String inetHost, int inetPort) {
			bootstrap.remoteAddress(inetHost, inetPort);
			return this;
		}

		public Builder handler(ChannelHandler... handlers) {
			addedHandlers = handlers;
			return this;
		}

		public NettyConnector build() {
			bootstrap.channel(NioSocketChannel.class);
			return new NettyConnector(this);
		}
	}

	class ListenHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			ctx.fireChannelInactive();
			channel.pipeline().remove(ChannelHandler.class);
			if (channelStateListener != null) {
				channelStateListener.onDisconnect();
			}
		}
	}

}
