package com.xter.ifornetty.multi;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.util.Enumeration;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.NetUtil;

/**
 * @author XTER
 * @desc
 * @date 2019/9/24
 */
public class NormalMultiCastServer {
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			NetworkInterface ni = NetUtil.LOOPBACK_IF;
			InetSocketAddress groupAddress = new InetSocketAddress("224.1.1.1", 9000);
			Enumeration<InetAddress> addresses = ni.getInetAddresses();
			InetAddress localAddress = null;
			while (addresses.hasMoreElements()) {
				InetAddress address = addresses.nextElement();
				if (address instanceof Inet4Address){
					localAddress = address;
				}
			}
			System.out.println(localAddress instanceof Inet4Address);
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioDatagramChannel.class)
					.localAddress(localAddress,groupAddress.getPort())
					.option(ChannelOption.IP_MULTICAST_IF, ni)//组播
					.option(ChannelOption.IP_MULTICAST_ADDR, localAddress)
					.option(ChannelOption.SO_REUSEADDR, true)//重复地址
					.handler(new ChannelInitializer<NioDatagramChannel>() {
						@Override
						public void initChannel(NioDatagramChannel ch) throws Exception {
							ch.pipeline().addLast(new SimpleChannelInboundHandler<DatagramPacket>() {
								@Override
								protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
									ByteBuf buf = datagramPacket.content();
									System.out.println(datagramPacket.sender()+" >>> "+new String(buf.readBytes(buf.readableBytes()).array()));
								}
							});
						}
					});

			NioDatagramChannel ch = (NioDatagramChannel)b.bind(groupAddress.getPort()).sync().channel();
			ch.joinGroup(groupAddress, ni).sync();
			ch.closeFuture().await();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			group.shutdownGracefully();//优雅退出
		}
	}
}
