package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientEncoder;
import com.xter.util.L;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class NettyClient {
	public static void main(String[] args) throws InterruptedException {
		NioEventLoopGroup group = new NioEventLoopGroup();

		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.handler(new ChannelInitializer<Channel>() {
						@Override
						protected void initChannel(Channel ch) {
							ch.pipeline().addLast(new IdleStateHandler(10, 10, 10));
							ch.pipeline().addLast(new NormalClientEncoder());
							ch.pipeline().addLast(new NormalClientHeartBeatHandler());
							ch.pipeline().addLast(new NormalClientHandler());
						}
					});

			ChannelFuture channelFuture = bootstrap.connect("127.0.0.1", 8000);
			L.d("done?");

			channelFuture.channel().closeFuture().sync();
			L.d("done?");

		}finally {
			group.shutdownGracefully();
		}

	}
}