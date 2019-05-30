package com.xter.ifornetty.common;

import com.xter.ifornetty.NormalMessage;
import com.xter.util.L;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GenericFutureListener;

@ChannelHandler.Sharable
public abstract class FunctionsChannelHandler extends ChannelInboundHandlerAdapter implements Runnable {

	private Bootstrap bootstrap;

	private long interval;

	private int maxReconnectTimes;

	private volatile boolean isActive;

	private AtomicInteger counter = new AtomicInteger(0);

	private Channel channel;

	private static final long INTERVAL = 10 * 1000;

	private static final int MAX_TIMES = 3;

	public FunctionsChannelHandler(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
		this.interval = INTERVAL;
		this.maxReconnectTimes = MAX_TIMES;
	}

	public FunctionsChannelHandler(Bootstrap bootstrap, long interval) {
		this.bootstrap = bootstrap;
		this.interval = interval;
		this.maxReconnectTimes = MAX_TIMES;
	}

	public FunctionsChannelHandler(Bootstrap bootstrap, long interval, int maxReconnectTimes) {
		this.bootstrap = bootstrap;
		this.interval = interval;
		this.maxReconnectTimes = maxReconnectTimes;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		isActive = true;
		counter.set(0);
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		if (isActive)
			L.d(bootstrap.config().remoteAddress() + " 连接断开");
		isActive = false;
		ctx.executor().execute(this);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
				case WRITER_IDLE:
					L.d("------write idle-------");
					break;
				case READER_IDLE:
					break;
				case ALL_IDLE:
					break;
			}
		}
	}

	@Override
	public void run() {
		if (bootstrap != null) {
			if (counter.getAndIncrement() < maxReconnectTimes) {
				L.d("重连第" + counter.get() + "次");
				bootstrap.connect().addListener(new GenericFutureListener<ChannelFuture>() {
					@Override
					public void operationComplete(ChannelFuture f) throws Exception {
						if (f.isSuccess()) {
							L.d("重连成功，" + Thread.currentThread().getName());
							channel = f.channel();
						} else {
							L.d("重连失败，" + Thread.currentThread().getName());
							f.channel().pipeline().fireChannelInactive();
						}
					}
				});
			}
		}

	}

	public abstract ChannelHandler[] handlers();

}
