package com.omen.netty.server.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

import java.util.Hashtable;


public class ChannelHashTable {
	
	private static Hashtable<String,Channel> busiTable = new Hashtable<String, Channel>();
	
	private static Hashtable<ChannelId,String> channelTable= new Hashtable<ChannelId, String>();
	
	
	public static Channel getCtx(String busiId){
		return busiTable.get(busiId);
	}
	
	public static void put(String busiId,Channel channel){
		busiTable.put(busiId, channel);
		channelTable.put(channel.id(), busiId);
	}
	
	public static boolean exists(String busiId){
		if(busiTable.contains(busiId))
			return true;
		else
			return false;
	}
	
	public static void remove(ChannelId channelId){
		if(channelTable.contains(channelId)){
			String busiId = channelTable.get(channelId);
			busiTable.remove(busiId);
			channelTable.remove(channelId);
		}
	}
	
	public static boolean existsWritableChannel(String busiId){
		if(busiTable.contains(busiId)&&busiTable.get(busiId).isWritable())
			return true;
		else
			return false;
	}
	
	
	public static void main(String[] args) {
		
		String a ="1";
		Integer b = 2;
		
		Hashtable<String, Integer> table = new Hashtable<String, Integer>();
		table.put(a, b);
		b =3;
		
		System.out.println(table.get("2"));
		
	}
	

}
