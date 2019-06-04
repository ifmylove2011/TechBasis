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
import io.netty.util.concurrent.GenericFutureListener;

/**
 * @author XTER
 * @date 2019/5/30
 */
public class NettyConnector {

	/**
	 * 连接器
	 */
	private Bootstrap bootstrap;

	/**
	 * 地址
	 */
	private String host;
	private int port;

	/**
	 * 会话
	 */
	private Channel channel;

	private static final long TIME_OUT = 10;


	private long connectTimeoutMills;

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
		connectTimeoutMills = builder.timeoutMills;
		bootstrap = builder.bootstrap;
		bootstrap.handler(new ChannelInitializer() {
			@Override
			protected void initChannel(Channel channel) throws Exception {
				channel.pipeline().addLast(new ChannelDisconnectHandler());
				channel.pipeline().addLast(builder.handlerSet.handlers());
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
			L.d("第" + (connectCounter.get() + 1) + "次连接" + host + ":" + port + "中......");

			long startMills = System.currentTimeMillis();
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
						long delay = System.currentTimeMillis() - startMills;
						if (delay > 0) {
							TimeUnit.MILLISECONDS.sleep(connectTimeoutMills - delay);
						}
						L.d("连接(" + bootstrap.config().remoteAddress() + ")失败");
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
		if (bootstrap == null)
			throw new IllegalArgumentException("bootstrap cannot be null");
		//如果已经连接，则直接【连接成功】
		if (channel == null || !channel.isActive()) {
			//连接
			channel = bootstrap.connect().awaitUninterruptibly().channel();
		}
	}

	public void reconnect(final long reconnectTimeoutMills, final int reconnectTimes) {
		try {
			recconnectCounter.set(0);
			while (channel != null && !channel.isActive() && recconnectCounter.getAndIncrement() < reconnectTimes) {
				L.d(Thread.currentThread().getName() + "," + "重连" + bootstrap.config().remoteAddress() + "(" + recconnectCounter.get() + ")次...");
				reconnect();
				if (channel.isActive()) {
					break;
				} else {
					TimeUnit.MILLISECONDS.sleep(reconnectTimeoutMills);
				}
				L.d(channel.isActive() + "");
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

	public Channel getChannel() {
		return channel;
	}

	public boolean isConnected() {
		return channel != null && channel.isActive();
	}

	public String getAddress() {
		return host + ":" + port;
	}

	public int getConnectFailedTimes() {
		return connectCounter.get();
	}

	public int getReconnectFailedTimes() {
		return recconnectCounter.get();
	}

	public static class Builder {

		private Bootstrap bootstrap = new Bootstrap();
		private HandlerSet handlerSet;
		private long timeoutMills = 10 * 1000;

		public Builder group(EventLoopGroup loopGroup) {
			bootstrap.group(loopGroup);
			return this;
		}

		@Deprecated
		public Builder remoteAddress(String inetHost, int inetPort) {
			bootstrap.remoteAddress(inetHost, inetPort);
			return this;
		}

		public Builder setConnectTimeoutMills(long timeout) {
			timeoutMills = timeout;
			return this;
		}

		public Builder handler(HandlerSet handlers) {
			handlerSet = handlers;
			return this;
		}

		public NettyConnector build() {
			bootstrap.channel(NioSocketChannel.class);
			return new NettyConnector(this);
		}
	}

	/**
	 * 主要用于监听断开
	 */
	class ChannelDisconnectHandler extends ChannelInboundHandlerAdapter {

		@Override
		public void channelInactive(ChannelHandlerContext ctx) throws Exception {
			ctx.fireChannelInactive();
			if (channelStateListener != null) {
				channelStateListener.onDisconnect();
			}
		}
	}

	public static abstract class HandlerSet extends ChannelInboundHandlerAdapter{
		public abstract ChannelHandler[] handlers();
	}

}
