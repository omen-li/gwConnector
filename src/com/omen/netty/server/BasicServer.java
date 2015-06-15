package com.omen.netty.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Service;

import com.omen.netty.server.factory.ServerChannelFactory;
import com.omen.netty.server.sysPojo.SystemInfo;

@Service()@DependsOn({"serverChannelFactory"})
public class BasicServer implements IServer {

    private Channel acceptorChannel;
    
	@Autowired
    ServerChannelFactory serverChannelFactory;
    

	
	@Override
	public void start()throws  Exception{
		try{
			acceptorChannel = serverChannelFactory.createAcceptorChannel();
			acceptorChannel.closeFuture().sync();
		}finally{
			//优雅退出，释放线程组资源
			SystemInfo.shutDownGraceFully();
		}
	}

	@Override
	public void stop()throws  Exception {
		try{
			if(acceptorChannel!=null)
				acceptorChannel.close().addListener(ChannelFutureListener.CLOSE);
		}finally{
			//优雅退出，释放线程组资源
			SystemInfo.shutDownGraceFully();
		}
	}

	@Override
	public void restart()throws  Exception {
		stop();
	    start();
	}
	
}
