package com.xter.ifornetty.codec;

import com.xter.ifornetty.NormalMessage;
import com.xter.util.L;

import java.nio.charset.Charset;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class NormalClientDecoder extends ByteToMessageDecoder {
	private Charset charset = Charset.forName("utf-8");

	@Override
	protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
		L.d("size="+byteBuf.readableBytes());
//		if (byteBuf.readableBytes() < 8) {
//			return;
//		}
//		byteBuf.markReaderIndex();
//		int type = byteBuf.readInt();
//		int length = byteBuf.readInt();
//		if (byteBuf.readableBytes() < length) {
//			byteBuf.resetReaderIndex();
//			return;
//		}
//		String content = (String) byteBuf.readCharSequence(length, charset);
//		NormalMessage nm = new NormalMessage(type, length, content);
//		L.d(Thread.currentThread().getName());
//		L.d(nm.toString());
//		list.add(nm);
		byte[] bytes = new byte[byteBuf.readableBytes()];
		byteBuf.readBytes(bytes);
		L.d(new String(bytes));
	}
}
