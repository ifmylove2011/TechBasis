package com.xter.ifornetty.codec;

import com.xter.util.L;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author XTER
 * @date 2019/5/31
 */

@ChannelHandler.Sharable
public class HeartHandler extends IdleStateHandler {
	public HeartHandler(int readerIdleTimeSeconds, int writerIdleTimeSeconds, int allIdleTimeSeconds) {
		super(readerIdleTimeSeconds, writerIdleTimeSeconds, allIdleTimeSeconds);
	}

	public HeartHandler(long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
		super(readerIdleTime, writerIdleTime, allIdleTime, unit);
	}

	public HeartHandler(boolean observeOutput, long readerIdleTime, long writerIdleTime, long allIdleTime, TimeUnit unit) {
		super(observeOutput, readerIdleTime, writerIdleTime, allIdleTime, unit);
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		L.d("~~~~~~~~~");
	}
}
