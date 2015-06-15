package com.omen.demo;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Date;

import com.omen.demo.utils.MyLogger;
import com.omen.netty.server.cache.ChannelHashTable;
import com.omen.netty.server.service.WebSocketServerService;

public class WsServerService implements WebSocketServerService {

	@Override
	public String doService(String request) {
		
		 MyLogger.info("service handle over!");
		// TODO Auto-generated method stub
		return "test msg returns!req:[" + request + "]; return time[" + new Date().toString() + "]";
	}

}
