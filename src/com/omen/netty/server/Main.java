package com.omen.netty.server;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.dusun.dubbo.service.redisService.FrontConnectionService;
import com.omen.netty.server.sysPojo.SystemInfo;

public class Main {

	private static Logger log = Logger.getLogger(Main.class);
	 @Autowired
	 private SystemInfo systemInfo;
	 
	
//	private static ApplicationContext ctx;
	static {
		// 先加载spring
		log.info("准备载入spring...");
		//Pro
//		String url = "../classes/omen.xml";
		
		// TEST
//		String url = "bin/omen.xml";
		// 保存context
//		SystemInfo.setCtx(new FileSystemXmlApplicationContext(url));
		SystemInfo.setCtx(new ClassPathXmlApplicationContext("omen.xml"));
		
		log.info("载入spring 完毕...");
	}

	public static void main(String[] args) throws Exception {
		IServer iServer = (IServer) SystemInfo.getCtx().getBean("basicServer");
		iServer.start();
	}

}
