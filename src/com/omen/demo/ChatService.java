package com.omen.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.omen.demo.busiPojo.RetData;
import com.omen.demo.handler.ChatHandler;
import com.omen.demo.utils.MyLogger;
import com.omen.netty.exception.BusiErrException;
import com.omen.netty.message.XmlData;
import com.omen.netty.server.http.pojo.HttpParameter;
import com.omen.netty.server.service.HttpServerService;
import com.omen.netty.utils.StringUtil;

public class ChatService implements HttpServerService {

	private Map<String, ChatHandler> handlers;
	
	/**
	 * 
	* @Title: doService
	* @Description: 业务处理类，返回String
	* @param @param paramMap 第一个key存放Set<Cookie> cookies
	* @param @return    
	* @return String    
	* @throws
	* @author omen  www.liyidong.com 
	* @date 2014年12月25日 下午2:59:22
	 */
	@Override
	public String doService(HttpParameter httpParameter) {
		
		MyLogger.info("进入业务处理...");
		RetData retData = new RetData();
		String content = httpParameter.getContent();
		String contentType = httpParameter.getContentType();
		Map<String,String> paramMap = httpParameter.getParameters();
		
		MyLogger.info("content =" + content);
		MyLogger.info("contentType =" + contentType);
		try {
			String operation = (String)paramMap.get("operation");
			if(StringUtil.isEmpty(operation)){
				MyLogger.error("业务请求:operation不能为空!");
				throw new BusiErrException(MyLogger.getErrorInfo());
			}
			
			ChatHandler handler=handlers.get(operation);
			
			if(handler==null){
				MyLogger.error("no handler for operation:"+operation);
				throw new BusiErrException();
			}
			MyLogger.info("业务请求 : operation = [" +operation + "] 获取handler完毕,进入业务处理...");
			
			retData = handler.handle(paramMap);
			
			MyLogger.info("业务请求 : operation = [" +operation + "]   handler处理完毕,准备返回数据...");
			
		} catch (BusiErrException e ) {
			if(MyLogger.getErrorInfo() == null)
				retData.setRetMsg(XmlData.FAIL_MSG);
			else 
				retData.setRetMsg(MyLogger.getErrorInfo());
			
			retData.setRetCode(XmlData.FAIL);
			
		} catch (Exception e1 ) {
			retData.setRetMsg(XmlData.FAIL_MSG);
			MyLogger.error("System Error!", e1);
			retData.setRetCode(XmlData.FAIL);
		}
		MyLogger.info("业务处理完毕,返回结果...");
		return JSON.toJSONString(retData);
			

		
	}
	
	
	public Map<String, ChatHandler> getHandlers() {
		return handlers;
	}
	
	public void setHandlers(Map<String, ChatHandler> handlers) {
		this.handlers = handlers;
	}

}
