package com.xter.ifornetty.codec;

import com.xter.ifornetty.NormalMessage;

import java.util.List;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

public class SpecialEncoder extends MessageToMessageEncoder<NormalMessage> {
	@Override
	protected void encode(ChannelHandlerContext channelHandlerContext, NormalMessage normalMessage, List<Object> list) throws Exception {
		System.out.println(normalMessage.toString());
		list.add(normalMessage);
	}
}
