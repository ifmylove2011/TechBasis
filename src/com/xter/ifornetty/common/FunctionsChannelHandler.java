package com.xter.ifornetty.common;

import com.xter.ifornetty.NormalMessage;
import com.xter.util.L;

import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.concurrent.GenericFutureListener;

@ChannelHandler.Sharable
public abstract class FunctionsChannelHandler extends ChannelInboundHandlerAdapter implements Runnable {

	private Bootstrap bootstrap;

	private NormalMessage heartBeat = new NormalMessage(2, 0, "");

	public FunctionsChannelHandler(Bootstrap bootstrap) {
		this.bootstrap = bootstrap;
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress() + " 连接断开");
		ctx.executor().schedule(this, 5, TimeUnit.SECONDS);
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
				case WRITER_IDLE:
					L.d("------write idle-------");
//					ctx.writeAndFlush(heartBeat);
					ctx.disconnect();
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
		L.d("开始重连");
		ChannelFuture future;
		if (bootstrap != null) {
			future = bootstrap.connect();
			try {
				future.addListener(new GenericFutureListener<ChannelFuture>() {
					@Override
					public void operationComplete(ChannelFuture f) throws Exception {
						if (f.isSuccess()) {
							L.d("重连成功，" + Thread.currentThread().getName());
						} else {
							L.d("重连失败，" + Thread.currentThread().getName());
							f.channel().pipeline().fireChannelInactive();
						}
					}
				});
				future.sync();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public abstract ChannelHandler[] handlers();

}
