package com.xter.ifornetty.codec;

import com.xter.ifornetty.NormalMessage;
import com.xter.util.L;

import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NormalClientEncoder extends MessageToByteEncoder<NormalMessage> {

	private Charset charset = Charset.forName("utf-8");

	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, NormalMessage normalMessage, ByteBuf byteBuf) throws Exception {
		L.d(normalMessage.toString());
		byteBuf.writeInt(normalMessage.type);
		byteBuf.writeInt(normalMessage.length);
		byteBuf.writeCharSequence(normalMessage.content,charset);
	}
}
