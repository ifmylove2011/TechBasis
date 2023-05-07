package com.xter.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	public static void main(String[] args) throws IOException {

		//1 定义一个ServerSocket 用来监听8080端口 如果有则返回一个与之关联的socket
		ServerSocket serverSocket=new ServerSocket(22222);

		System.out.println("服务端正在监听");
		//监听: 此方法是阻塞方法
		Socket socket=serverSocket.accept();
		//获得一个流，并转化成一个字符流
		BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		//读取请求行
		String reqLine=br.readLine();
		System.out.println(reqLine);

		//读取请求头
		String headreq="";
		while ((headreq=br.readLine())!=null){
			System.out.println(headreq);
		}

		br.close();
		serverSocket.close();
		socket.close();
	}
}
