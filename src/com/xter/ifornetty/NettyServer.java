package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientDecoder;
import com.xter.util.L;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
	public static void main(String[] args) {
		NioEventLoopGroup boos = new NioEventLoopGroup();
		NioEventLoopGroup worker = new NioEventLoopGroup();

		try {
			ServerBootstrap serverBootstrap = new ServerBootstrap()
					.group(boos, worker)
					.channel(NioServerSocketChannel.class)
					.childOption(ChannelOption.TCP_NODELAY,true)
					.childHandler(new ChannelInitializer<Channel>() {
						protected void initChannel(Channel ch) {
							L.d(ch.toString());
							ch.pipeline().addLast(new NormalClientDecoder());
							ch.pipeline().addLast(new NormalServerHandler());
						}
					});
			serverBootstrap.localAddress(8899);

			ChannelFuture channelFuture = serverBootstrap.bind();
			L.d("done?");

			channelFuture.channel().closeFuture().sync();
			L.d("done?");

		}catch (Exception e){
			e.printStackTrace();
		}finally {
			boos.shutdownGracefully();
			worker.shutdownGracefully();
		}

	}
}
