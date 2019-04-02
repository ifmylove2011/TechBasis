package com.xter.ifornetty;

import com.xter.util.L;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class NormalServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		NormalMessage normalMessage = (NormalMessage) msg;
		L.d(Thread.currentThread().getName());
		L.d(normalMessage.toString());
	}


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		L.d("------active-------");
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			L.d("------idle-------");
		}
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		L.d("-----unreg------");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
