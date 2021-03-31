package com.xter.ifornetty;

import com.xter.util.L;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.socket.DatagramPacket;

public class NormalUDPClientHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress() + "");
	}

	@Override
	public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress() + "");
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("我在广播" + i, Charset.forName("utf-8")), new InetSocketAddress("255.255.255.255", 14001)));
			TimeUnit.SECONDS.sleep(3);
		}
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		L.d(ctx.channel().remoteAddress() + "");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		L.d(ctx.channel().toString());
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
