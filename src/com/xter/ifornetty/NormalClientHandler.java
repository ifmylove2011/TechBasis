package com.xter.ifornetty;

import com.xter.util.L;

import java.util.concurrent.TimeUnit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleStateEvent;

public class NormalClientHandler extends SimpleChannelInboundHandler<NormalMessage> {

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, NormalMessage normalMessage) throws Exception {
		L.d(Thread.currentThread().getName());
		L.d(normalMessage.toString());
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		L.d("------active-------"+ctx.channel().remoteAddress().toString());
		for (int i = 0; i < 100; i++) {
			String content = "data" + i;
			ctx.writeAndFlush(new NormalMessage(1, content.length(), content));
			TimeUnit.MILLISECONDS.sleep(200);
		}
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
					L.d("------read idle-------");
					break;
				case ALL_IDLE:
					L.d("------all idle-------");
					break;
			}
		}
	}
}
