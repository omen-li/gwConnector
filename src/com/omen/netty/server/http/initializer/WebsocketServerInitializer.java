package com.omen.netty.server.http.initializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLEngine;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.omen.netty.securechat.SecureChatSslContextFactory;
import com.omen.netty.server.Main;
import com.omen.netty.server.http.handler.WebsocketServerHandler;
import com.omen.netty.server.sysPojo.SystemInfo;

@Service()@Scope("prototype")@DependsOn({"websocketServerHandler"})
public class WebsocketServerInitializer extends ChannelInitializer<SocketChannel>   {
  
	@Autowired
    private LinkedHashMap<String, ChannelHandler> customPipelineMap;
    
	@Autowired
	private WebsocketServerHandler websocketServerHandler;
	
    private static Logger log = Logger.getLogger(Main.class);
   
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		
		ChannelPipeline pipeline = ch.pipeline();
		if (SystemInfo.getIsSsl()) {
			SSLEngine engine = SecureChatSslContextFactory.getServerContext().createSSLEngine();
			engine.setNeedClientAuth(true); //ssl双向认证
			engine.setUseClientMode(false);
			engine.setWantClientAuth(true);
			engine.setEnabledProtocols(new String[]{"SSLv3"});
			pipeline.addLast("ssl", new SslHandler(engine));
		}
		
		/**
		 * http-request解码器
		 * http服务器端对request解码
		 */
		pipeline.addLast("http-decoder", new HttpServerCodec());
		/**
		 * http报文的数据组装成为封装好的httprequest对象
		 */
		 pipeline.addLast("aggregator", new HttpObjectAggregator(1048576));
		 /**
		  * 压缩
		  * Compresses an HttpMessage and an HttpContent in gzip or deflate encoding
		  * while respecting the "Accept-Encoding" header.
		  * If there is no matching encoding, no compression is done.
		  */
//		 pipeline.addLast("deflater", new HttpContentCompressor());
		 
		 /**
		  * 
		  */
		 pipeline.addLast("http-chunked", new ChunkedWriteHandler());
		 
		 
		 for(Iterator<Map.Entry<String,ChannelHandler>> it = customPipelineMap.entrySet().iterator();it.hasNext();){  
	            Entry<String, ChannelHandler> entry = (Entry<String, ChannelHandler>)it.next();  
	            if(entry.getValue()!=null){  
	            	String handlerName = entry.getKey();
					ChannelHandler handlerValue = entry.getValue();
					pipeline.addLast(handlerName, handlerValue);
	            }  
	        }  
		 
		 pipeline.addLast("handler",websocketServerHandler);
	}

	
}
