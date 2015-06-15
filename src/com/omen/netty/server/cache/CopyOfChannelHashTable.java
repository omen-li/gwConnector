package com.omen.netty.server.cache;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;

import java.util.Hashtable;


public class CopyOfChannelHashTable {
	
	private static Hashtable<String,ChannelHandlerContext> busiTable = new Hashtable<String, ChannelHandlerContext>();
	
	private static Hashtable<Integer,String> ctxTable= new Hashtable<Integer, String>();
	
	
	public static ChannelHandlerContext getCtx(String busiId){
		return busiTable.get(busiId);
	}
	
	public static void put(String busiId,ChannelHandlerContext ctx){
		busiTable.put(busiId, ctx);
		ctxTable.put(ctx.hashCode(), busiId);
	}
	
	public static boolean exists(String busiId){
		if(busiTable.contains(busiId))
			return true;
		else
			return false;
	}
	
	public static void remove(Integer ctxHashCode){
		if(ctxTable.contains(ctxHashCode)){
			String busiId = ctxTable.get(ctxHashCode);
			busiTable.remove(busiId);
			ctxTable.remove(ctxHashCode);
		}
	}
	
	public static boolean existsWritableChannel(String busiId){
		if(busiTable.contains(busiId)&&busiTable.get(busiId).channel().isWritable())
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
