package com.dusun.front.adapter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSON;
import com.dusun.dubbo.service.bo.mqService.GwRequest;
import com.dusun.dubbo.service.mqProvider.GwRequestProvideService;
import com.dusun.dubbo.service.redisService.FrontConnectionService;
import com.dusun.front.busi.Login;
import com.dusun.front.utils.NetUtils;
import com.dusun.front.utils.PropertiesUtil;
import com.omen.demo.utils.MyLogger;
import com.omen.demo.utils.StringUtil;
import com.omen.netty.server.cache.ChannelHashTable;
import com.omen.netty.server.service.WebSocketServerService;

public class WebSocketServiceAdapter implements WebSocketServerService {

	@Autowired
	private FrontConnectionService frontConnectionService;

	@Autowired
	private GwRequestProvideService gwRequestProvideService;
	
	// 当前类类名
	private static final String className = new Object() {
		public String getClassName() {
			String clazzName = this.getClass().getName();
			return clazzName.substring(0, clazzName.lastIndexOf('$'));
		}
	}.getClassName();
	
	/**
	 * 1.校验内容是否为空 2.将报文交给MQ
	 */
	public String doService(ChannelHandlerContext ctx, String request) {

		Channel channel = ctx.channel();

		String message = null;

		Map<String, Object> map = null;

		GwRequest gwRequest = null;

		try {

			if (StringUtil.isEmpty(request)) {
				MyLogger.error("[" + className + "]request为空!");
				throw new Exception();
			}
			
			// 返回ACK，告知设备消息已抵达
			String ackMsg = getAckRes(request);
			if (StringUtil.isEmpty(ackMsg)) {
				MyLogger.error("[" + className + "]消息格式错误!");
				throw new Exception();
			}
			channel.write(new TextWebSocketFrame(ackMsg));
			
			// 反序列化报文--map
			try {
				message = getRequestMsg(request);
				if (StringUtil.isEmpty(message)) {
					MyLogger.error("[" + className + "]获取报文失败!");
					throw new Exception();
				}
				map = JSON.parseObject(message);
			} catch (Exception e) {
				MyLogger.error("[" + className + "]报文格式错误,解析失败![" + message + "]", e);
				throw e;
			}
			
			// 遍历参数，正则校验参数格式
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				if (!Pattern.compile(PropertiesUtil.getValue(entry.getKey()))
						.matcher(entry.getValue().toString()).find()) {
					MyLogger.error("[" + className + "]参数[" + entry.getKey() + "]未通过校验!value="
							+ entry.getValue());
					throw new Exception();
				}
			}

			// 反序列化报文--obj
			try {
				gwRequest = JSON.parseObject(message, GwRequest.class);
			} catch (Exception e) {
				MyLogger.error("[" + className + "]报文缺少必须参数,解析失败!" + e);
				throw e;
			}
			
			// 判断如果为login请求，保存网关与前置的对应关系
			if (gwRequest.getReqType() == 2) {
				MyLogger.info("判断为login请求,保存设备与前置服务器的对应关系.");
				String loginJson = gwRequest.getContent();
				if (StringUtil.isEmpty(loginJson)) {
					MyLogger.error("[" + className + "]消息体内容为空,保存对应关系失败!");
					throw new Exception();
				}
				try {
					Login login = JSON.parseObject(loginJson, Login.class);
					frontConnectionService.setFrontConnectionRecorde(
							login.getDeviceId(), NetUtils.getIp());
					MyLogger.info("对应关系保存成功.");
					// 保存GW与channel的对应关系
					ChannelHashTable.put(login.getDeviceId(), channel);
				} catch (Exception e) {
					MyLogger.error("[" + className + "]保存对应关系发生异常,保存失败!" + e);
					throw e;
				}
			}

			// 将消息保存进MQprovider中
			gwRequestProvideService.sendRequest(gwRequest);
			
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	public static void main(String[] strs) {
		/*
		 * String mac=""; String patternMac="^[A-F0-9]{2}(-[A-F0-9]{2}){5}$";
		 * System.out.println(Pattern.compile(patternMac).matcher(mac).find());
		 * String version=""; String
		 * patternVersion="^[^ \\f\\n\\r\\t\\v]{1,16}$";
		 * System.out.println(Pattern
		 * .compile(patternVersion).matcher(version).find()); int reqType = 1;
		 * String patternreqType="^([1-9]|10)$";
		 * System.out.println(Pattern.compile(patternreqType).matcher(reqType +
		 * "").find());
		 */
	}

	/**
	 * 
	 * @Title: getAckRes
	 * @Description: 返回终端设备请求抵达响应
	 * @param @param request
	 * @param @return
	 * @return String
	 * @throws
	 * @author omen www.liyidong.com
	 * @date 2015年6月15日 下午5:55:53
	 */
	private static String getAckRes(String request) {
		if (request.indexOf("|") >= 0) {
			String requestId = request.split("\\|", 2)[0];
			String retMsg = "ACK|" + requestId;
			return retMsg;
		} else {
			return null;
		}
	}

	private static String getRequestMsg(String request) {
		if (request.indexOf("|") >= 0) {
			String requestId = request.split("\\|", 2)[1];
			return requestId;
		} else {
			return null;
		}
	}

	@Override
	public void close(ChannelHandlerContext ctx) throws Exception {
		// TODO 链接关闭时需要做的操作

		// 回收业务流程中自己创建的且在关闭channel时不会自动回收的资源
		ChannelHashTable.remove(ctx.channel().id());
	}

}