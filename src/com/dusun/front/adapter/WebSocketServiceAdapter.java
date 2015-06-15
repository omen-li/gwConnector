package com.dusun.front.adapter;

import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.dusun.front.busi.RequestMsg;
import com.omen.demo.utils.MyLogger;
import com.omen.demo.utils.StringUtil;
import com.omen.netty.server.service.WebSocketServerService;
import com.omen.netty.utils.PropertiesUtil;

public class WebSocketServiceAdapter implements WebSocketServerService {

	// @Autowired
	// private FrontConnectionService frontConnectionService;
	
	// @Autowired
	// private MsgClassifyService msgClassifyService;
	
	/**
	 * 1.校验内容是否为空
	 * 2.分离ACK与报文内容
	 * 3.将报文交给MQ
	 * 4.返回ACK
	 */
	public void doService(String request) {

		Map<String, Object> map = null;

		RequestMsg requestMsg = null;
		
		String requestData = null;
		
		try {
			
			if (StringUtil.isEmpty(request)) {
				MyLogger.error("request为空!");
				throw new Exception();
			} else {
				try {
					requestData = getRequestData(request);
					// TODO MQconsumer模块进行反序列化
					map = JSON.parseObject(requestData);
				} catch (Exception e) {
					MyLogger.error("报文内容格式错误,解析失败![" + requestData + "]", e);
				}
			}

			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (!Pattern.compile(PropertiesUtil.getValue(entry.getKey()))
						.matcher(entry.getValue().toString()).find()) {
					MyLogger.error("参数[" + entry.getKey() + "]未通过校验!value="
							+ entry.getValue());
					throw new Exception();
				}
			}
			
			requestMsg = JSON.parseObject(requestData, RequestMsg.class);
			// msgClassifyService.classify(requestMsg);
			
		} catch (Exception e) {
			
		}
		
	}
	
	private static String getRequestData(String str) {
		return str.split("\\|", 2)[1];
	}
	
	public static void main (String[] strs) {
		/*String mac="";
		String patternMac="^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
		System.out.println(Pattern.compile(patternMac).matcher(mac).find());
		String version="";
		String patternVersion="^[^ \\f\\n\\r\\t\\v]{1,16}$";
		System.out.println(Pattern.compile(patternVersion).matcher(version).find());
		int reqType = 1;
		String patternreqType="^([1-9]|10)$";
		System.out.println(Pattern.compile(patternreqType).matcher(reqType + "").find());*/
		String a = "000000|{ \"mac\": \"11:22:33:44:55:66\", \"version\": \"1.0\", \"reqType\": 1, \"content\": \"nothing\", \"timeStamp\": \"20140616T091111\", \"permit\": \"permitNumber\" }";
		System.out.println(getAck(a) + "|" + getRequestData(a));
		System.out.println(a.split("\\|")[0] + "|" + a.split("\\|")[1]);
	}
}