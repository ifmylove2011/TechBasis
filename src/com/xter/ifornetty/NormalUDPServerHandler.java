package com.xter.ifornetty;

import com.xter.util.L;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.buffer.UnpooledDirectByteBuf;
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
		L.d(packet.sender().toString()+","+content);

		ByteBuf byteBuf1 = new UnpooledByteBufAllocator(false).buffer();
		byteBuf1.writeCharSequence(content, Charset.forName("utf-8"));
		L.d(ctx.channel().toString());

		ctx.writeAndFlush(new DatagramPacket(byteBuf1,packet.sender()));
//		for (int i = 0; i < 10; i++) {
//			ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("我在广播", Charset.forName("utf-8")), new InetSocketAddress("255.255.255.255", 9999)));
//			TimeUnit.SECONDS.sleep(2);
//		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}
}
