package com.xter.ifornetty;

import com.xter.util.L;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;

public class NormalClientHeartBeatHandler extends ChannelInboundHandlerAdapter {

	private NormalMessage heartBeat = new NormalMessage(2,0,"");

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (evt instanceof IdleStateEvent) {
			IdleStateEvent event = (IdleStateEvent) evt;
			switch (event.state()) {
				case WRITER_IDLE:
					L.d("------write idle-------");
					ctx.writeAndFlush(heartBeat);
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
