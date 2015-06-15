package com.omen.netty.server.factory;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.omen.netty.exception.SysErrException;
import com.omen.netty.server.http.initializer.CustomServerInitializer;
import com.omen.netty.server.http.initializer.HttpServerInitializer;
import com.omen.netty.server.http.initializer.TcpServerInitializer;
import com.omen.netty.server.http.initializer.WebsocketServerInitializer;
import com.omen.netty.server.sysPojo.ProtocolType;
import com.omen.netty.server.sysPojo.SystemInfo;

@Service()@DependsOn({"httpServerHandler"})
public class ServerChannelFactory {

	@Autowired
	private HttpServerInitializer httpServerInitializer;
	
	@Autowired
	private TcpServerInitializer tcpServerInitializer;
	
	@Autowired
	private CustomServerInitializer customServerInitializer;
	
	@Autowired
	private WebsocketServerInitializer websocketServerInitializer;
	
	private static Logger log = Logger.getLogger(ServerChannelFactory.class);
	
	public  Channel createAcceptorChannel() throws SysErrException{
		
		final ServerBootstrap serverBootstrap = ServerBootstrapFactory.createServerBootstrap();
		serverBootstrap.childHandler(getChildHandler());
		log.info("创建Server...");
		 try {
			 ChannelFuture channelFuture = serverBootstrap.bind(SystemInfo.getPort()).sync();
			 channelFuture.awaitUninterruptibly();
	            if (channelFuture.isSuccess()) {
	            	SystemInfo.printSysInfo();
	                return channelFuture.channel();
	            } else {
	            	String errMsg="Failed to open socket! Cannot bind to port: "+SystemInfo.getPort()+"!";
	            	log.error(errMsg);
	            	throw new SysErrException(errMsg);
	            }
				
		} catch (Exception e){
			throw new SysErrException(e);
		}
	}
	
	
	private  ChannelInitializer<SocketChannel> getChildHandler() throws SysErrException{
		
		switch (SystemInfo.getProtocolType()) {
		case ProtocolType.HTTP:
			return (ChannelInitializer<SocketChannel>)httpServerInitializer;
			
		case ProtocolType.HTTPS:
			return (ChannelInitializer<SocketChannel>)httpServerInitializer;
			
		case ProtocolType.TCP:
			return (ChannelInitializer<SocketChannel>)tcpServerInitializer;	
			
		case ProtocolType.CUSTOM:
			return (ChannelInitializer<SocketChannel>)customServerInitializer;	
			
		case ProtocolType.WEBSOCKET:
			return (ChannelInitializer<SocketChannel>)websocketServerInitializer;	

		default:
			String errMsg="undefined protocol:"+SystemInfo.getProtocolType()+"!";
			throw new SysErrException(errMsg);
		}
		
		
		/*if(ProtocolType.HTTP.equals(SystemInfo.getProtocolType())
				||ProtocolType.HTTPS.equals(SystemInfo.getProtocolType())){
			return (ChannelInitializer<SocketChannel>)httpServerInitializer;
		}
		
		else if(ProtocolType.TCP.equals(SystemInfo.getProtocolType()))
			return (ChannelInitializer<SocketChannel>)tcpServerInitializer;
		
		else if(ProtocolType.CUSTOM.equals(SystemInfo.getProtocolType()))
			return (ChannelInitializer<SocketChannel>)customServerInitializer;
		
		else{
			String errMsg="undefined protocol:"+SystemInfo.getProtocolType()+"!";
			throw new SysErrException(errMsg);
		}*/
		
	}
}
