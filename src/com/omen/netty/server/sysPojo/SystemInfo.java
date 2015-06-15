package com.omen.netty.server.sysPojo;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.omen.netty.utils.StringUtil;

public class SystemInfo {
	
	private static Logger log = Logger.getLogger(SystemInfo.class);
	
	
	static{
		
	}
	
	
	public SystemInfo(String protocolType,Integer port,Integer channelType) throws Exception{
		
		/**
		 * 基础校验
		 */
		if(StringUtil.isEmpty(protocolType)){
			throw new Exception("protocolType is not setted");
		}
		
		if(port == null){
			throw new Exception("port is not setted");
		}
		
		if(channelType == null){
			throw new Exception("channelType is not setted");
		}
		
		this.protocolType=protocolType.toUpperCase();
		this.port=port;
		this.isSsl=false;
		this.channelType=channelType;
	}
	
	public SystemInfo(String protocolType,Integer port,Integer channelType,
			Integer bossGroupSize,Integer workerGroupSize) throws Exception{
		
		/**
		 * 基础校验
		 */
		if(StringUtil.isEmpty(protocolType)){
			throw new Exception("protocolType is not setted");
		}
		
		if(port == null){
			throw new Exception("port is not setted");
		}
		if(channelType == null){
			throw new Exception("channelType is not setted");
		}
		
		this.protocolType=protocolType.toUpperCase();
		this.port=port;
		this.isSsl=false;
		this.channelType=channelType;
		this.bossGroupSize=bossGroupSize;
		this.workerGroupSize=workerGroupSize;
	}
	
	
	public SystemInfo(String protocolType,Integer port,Integer channelType, boolean isSsl,String jksPath,
			String jksPwd) throws Exception{
		
		/**
		 * 基础校验
		 */
		if(StringUtil.isEmpty(protocolType)){
			throw new Exception("protocolType is not setted");
		}
		
		if(port == null){
			throw new Exception("port is not setted");
		}
		if(isSsl){
			if(StringUtil.isEmpty(jksPath)){
				throw new Exception("jksPath type is not setted");
			}
			if(StringUtil.isEmpty(jksPwd)){
				throw new Exception("jksPwd type is not setted");
			}
		}
		
		if(channelType == null){
			throw new Exception("channelType is not setted");
		}
		
		this.protocolType=protocolType.toUpperCase();
		this.port=port;
		this.isSsl=isSsl;
		this.jksPath=jksPath;
		this.jksPwd=jksPwd;
		this.channelType=channelType;
	}
	
	public SystemInfo(String protocolType,Integer port,Integer channelType, boolean isSsl,String jksPath,
			String jksPwd,Integer bossGroupSize,Integer workerGroupSize) throws Exception{
		
		/**
		 * 基础校验
		 */
		if(StringUtil.isEmpty(protocolType)){
			throw new Exception("protocolType is not setted");
		}
		
		if(port == null){
			throw new Exception("port is not setted");
		}
		if(isSsl){
			if(StringUtil.isEmpty(jksPath)){
				throw new Exception("jksPath type is not setted");
			}
			if(StringUtil.isEmpty(jksPwd)){
				throw new Exception("jksPwd type is not setted");
			}
		}
		
		if(channelType == null){
			throw new Exception("channelType is not setted");
		}
		
		this.protocolType=protocolType.toUpperCase();
		this.port=port;
		this.isSsl=isSsl;
		this.jksPath=jksPath;
		this.jksPwd=jksPwd;
		this.channelType=channelType;
		this.bossGroupSize=bossGroupSize;
		this.workerGroupSize=workerGroupSize;
	}
	
	private static String protocolType;

	private static  Integer port;
	
	private static Boolean isSsl;
	
	private static String jksPath;
	
	private static  String jksPwd;
	
	private static Integer channelType;

	private static  ApplicationContext ctx;
	
	private static EventLoopGroup bossGroup ;
	
	private static EventLoopGroup workerGroup;
	
	private static Integer bossGroupSize;
	
	private static Integer workerGroupSize;
	
	
	public static void printSysInfo(){
		log.info("**************SYSTEM INFO******************");
		log.info("protocolType  : " + protocolType);
		log.info("port          : " + port);
		log.info("channelType   : " + channelType + " (0=NIO 1=OIO)");
		log.info("isSsl         : " + isSsl);
		if(!StringUtil.isEmpty(jksPath))
			log.info("jksPath       : " + jksPath);
		if(!StringUtil.isEmpty(jksPwd))
			log.info("jksPwd        : " + jksPwd);
		if(bossGroupSize!=null)
			log.info("bossGroupSize : " + bossGroupSize);
		if(workerGroupSize!=null)
			log.info("workerGroupSize: " + workerGroupSize);
		log.info("**************SYSTEM INFO******************");
	}
	
	public static void shutDownGraceFully(){
		bossGroup.shutdownGracefully();
		workerGroup.shutdownGracefully();
	}
	


	public static String getProtocolType() {
		return protocolType;
	}
	
	public static void setProtocolType(String protocolType) {
		SystemInfo.protocolType = protocolType;
	}
	
	public static Integer getPort() {
		return port;
	}

	public static void setPort(Integer port) {
		SystemInfo.port = port;
	}

	public static Boolean getIsSsl() {
		return isSsl;
	}

	public static void setIsSsl(Boolean isSsl) {
		SystemInfo.isSsl = isSsl;
	}

	public static String getJksPath() {
		return jksPath;
	}

	public static void setJksPath(String jksPath) {
		SystemInfo.jksPath = jksPath;
	}

	public static String getJksPwd() {
		return jksPwd;
	}

	public static void setJksPwd(String jksPwd) {
		SystemInfo.jksPwd = jksPwd;
	}

	public static Integer getChannelType() {
		return channelType;
	}

	public static void setChannelType(Integer channelType) {
		SystemInfo.channelType = channelType;
	}
	
	public static ApplicationContext getCtx() {
		return ctx;
	}
	
	public static void setCtx(ApplicationContext ctx) {
		SystemInfo.ctx = ctx;
	}

	public static Integer getBossGroupSize() {
		return bossGroupSize;
	}

	public static void setBossGroupSize(Integer bossGroupSize) {
		SystemInfo.bossGroupSize = bossGroupSize;
	}
	
	public static EventLoopGroup getBossGroup() {
		return bossGroup;
	}
	
	public static void setBossGroup(EventLoopGroup bossGroup) {
		SystemInfo.bossGroup = bossGroup;
	}
	
	public static EventLoopGroup getWorkerGroup() {
		return workerGroup;
	}
	
	public static void setWorkerGroup(EventLoopGroup workerGroup) {
		SystemInfo.workerGroup = workerGroup;
	}

	public static Integer getWorkerGroupSize() {
		return workerGroupSize;
	}

	public static void setWorkerGroupSize(Integer workerGroupSize) {
		SystemInfo.workerGroupSize = workerGroupSize;
	}
	
	public static void main(String[] args) {
		
	}
	

}
