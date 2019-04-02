package com.xter.ifornetty;

import com.xter.util.L;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

public class NormalUDPServerHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress()+"");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress()+"");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress()+"");
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress()+"");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		DatagramPacket packet = (DatagramPacket) msg;
		ByteBuf byteBuf = packet.copy().content();
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		String content = new String(bytes);
		L.d(content);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
