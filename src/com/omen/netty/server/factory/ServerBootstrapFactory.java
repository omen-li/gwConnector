package com.omen.netty.server.factory;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.oio.OioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.oio.OioServerSocketChannel;

import com.omen.netty.server.sysPojo.ChannelType;
import com.omen.netty.server.sysPojo.SystemInfo;


/**
 * @author liyidong       
 * @version 1.0     
 * @created 2014-12-16 上午11:14:33 
 * @function:工厂模式  server服务器 ServerBootstrap创建
 */
public class ServerBootstrapFactory {
	private ServerBootstrapFactory() {
    }
    public static ServerBootstrap createServerBootstrap() throws UnsupportedOperationException {
         
    	ServerBootstrap serverBootstrap = new ServerBootstrap();
        switch (SystemInfo.getChannelType()) {
            case ChannelType.NIO:
            	EventLoopGroup bossGroup = new NioEventLoopGroup();
            	EventLoopGroup workerGroup = new NioEventLoopGroup();
            	serverBootstrap.group(bossGroup, workerGroup);
            	SystemInfo.setBossGroup(bossGroup);
            	SystemInfo.setWorkerGroup(workerGroup);
            	serverBootstrap.channel(NioServerSocketChannel.class);
            	//连接的最大队列长度。如果队列满时收到连接指示，则拒绝该连接
            	serverBootstrap.option(ChannelOption.SO_BACKLOG, 100);
            	
            	return serverBootstrap;
            //TODO
            case ChannelType.OIO:
            	serverBootstrap.group(new OioEventLoopGroup());
            	serverBootstrap.channel(OioServerSocketChannel.class);
                 
                return serverBootstrap;
            default:
                throw new UnsupportedOperationException("Failed to create ServerBootstrap,  " + SystemInfo.getChannelType() + " not supported!");
        }
    }
}
