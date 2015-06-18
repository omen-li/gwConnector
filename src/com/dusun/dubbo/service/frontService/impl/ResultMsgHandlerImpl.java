package com.dusun.dubbo.service.frontService.impl;

import io.netty.channel.Channel;

import com.dusun.dubbo.service.frontService.ResultMsgHandler;
import com.omen.netty.server.cache.ChannelHashTable;

public class ResultMsgHandlerImpl implements ResultMsgHandler{

	@Override
	public boolean send(Object obj) {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * 发送sender返回的消息
	 * @param deviceId
	 * @param retMsg
	 */
	private boolean send2GW(String deviceId, String retMsg) {
		
		Channel channel = ChannelHashTable.getChannel(deviceId);
		
		// 判断channel是否已经激活
		if (channel != null && channel.isActive()) {
			channel.writeAndFlush(retMsg);
			return true;
		} else {
			return false;
		}
	}

}
