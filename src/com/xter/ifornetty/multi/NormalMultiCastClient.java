package com.xter.ifornetty.multi;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;
import io.netty.util.NetUtil;

/**
 * @author XTER
 * @desc
 * @date 2019/9/24
 */
public class NormalMultiCastClient {
	public static void main(String[] args) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			InetSocketAddress groupAddress = new InetSocketAddress("224.1.1.1", 9000);
			NetworkInterface ni = NetUtil.LOOPBACK_IF;
			Enumeration<InetAddress> addresses = ni.getInetAddresses();
			InetAddress localAddress = null;
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				if (address instanceof Inet4Address) {
					localAddress = address;
				}
			}

			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.localAddress(localAddress, groupAddress.getPort())
					.option(ChannelOption.IP_MULTICAST_IF, ni)//组播
					.option(ChannelOption.SO_REUSEADDR, true)//重复地址
					.handler(new ChannelInitializer<NioDatagramChannel>() {

						@Override
						protected void initChannel(NioDatagramChannel ch) throws Exception {
							ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {

								@Override
								protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
									System.out.println(datagramPacket.content());
								}

							});
						}

					});

			Channel ch = b.bind().sync().channel();
			ch.writeAndFlush(new DatagramPacket(
					Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8),
					groupAddress)).sync();//发送数据

			ch.close().awaitUninterruptibly();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();
		}
	}
}
