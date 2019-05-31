package com.xter.ifornetty;

import com.xter.ifornetty.codec.NormalClientEncoder;
import com.xter.ifornetty.common.FunctionsChannelHandler;
import com.xter.util.L;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.GenericFutureListener;

public class CommonClient1 {
	public static void main(String[] args) {
//		TCPClient tcpClient = new TCPClient.Builder()
//				.assign(3)
//				.handler(new ChannelInitializer<Channel>() {
//					@Override
//					protected void initChannel(Channel channel) throws Exception {
//						L.d(Thread.currentThread().getName());
//						channel.pipeline().addLast(new NormalClientEncoder());
//						channel.pipeline().addLast(new IdleStateHandler(20, 10, 20));
//						channel.pipeline().addLast(new NormalClientHeartBeatHandler());
//						channel.pipeline().addLast(new NormalClientHandler());
//					}
//				})
//				.setRemoteAddress("127.0.0.1", 8000)
//				.build();
//		tcpClient.connect();
		EventLoopGroup workerLoop = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap()
					.group(workerLoop)
					.channel(NioSocketChannel.class)
					.remoteAddress("127.0.0.1", 8000);

			FunctionsChannelHandler functionsChannelHandler = new FunctionsChannelHandler(bootstrap){

				@Override
				public ChannelHandler[] handlers() {
					return new ChannelHandler[]{
							new NormalClientEncoder(),
							new IdleStateHandler(20, 10, 20),
							this,
							new NormalClientHandler()};
				}
			};

			bootstrap.handler(new ChannelInitializer<Channel>() {
				@Override
				protected void initChannel(Channel channel) throws Exception {
					L.d(Thread.currentThread().getName()+","+this.hashCode());
					channel.pipeline().addLast(functionsChannelHandler.handlers());
				}

				@Override
				public void channelInactive(ChannelHandlerContext ctx) throws Exception {
					super.channelInactive(ctx);
					//监听到断开
					L.d("-------------");
				}
			});

			connect(bootstrap);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void connect(Bootstrap bootstrap){
		bootstrap.connect().addListener(new GenericFutureListener<ChannelFuture>() {
			@Override
			public void operationComplete(ChannelFuture f) throws Exception {
				if (f.isSuccess()) {
					L.d("连接成功，" + Thread.currentThread().getName());
				} else {
					L.d("连接失败，" + Thread.currentThread().getName());
					connect(bootstrap);
				}
			}
		});
	}
}
