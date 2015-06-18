package com.dusun.front.adapter;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.dusun.front.busi.RequestMsg;
import com.dusun.front.utils.PropertiesUtil;
import com.omen.demo.utils.MyLogger;
import com.omen.demo.utils.StringUtil;
import com.omen.netty.server.cache.ChannelHashTable;
import com.omen.netty.server.service.WebSocketServerService;

public class WebSocketServiceAdapter implements WebSocketServerService {

	// @Autowired
	// private FrontConnectionService frontConnectionService;

	// @Autowired
	// private MsgClassifyService msgClassifyService;

	/**
	 * 1.校验内容是否为空 2.将报文交给MQ
	 */
	public String doService(ChannelHandlerContext ctx, String request) {

		Channel channel = ctx.channel();

		// 返回ACK，告知设备消息已抵达
		String ackMsg = getAckRes(request);
		if (!StringUtil.isEmpty(ackMsg))
			channel.write(new TextWebSocketFrame(ackMsg));

		Map<String, Object> map = null;

		// TODO 将GW与channel的对应关系保存至hashtable

		RequestMsg requestMsg = null;

		try {

			if (StringUtil.isEmpty(request)) {
				MyLogger.error("request为空!");
				throw new Exception();
			} else {
				try {
					// TODO MQconsumer模块进行反序列化
					map = JSON.parseObject(request);
				} catch (Exception e) {
					MyLogger.error("报文内容格式错误,解析失败![" + request + "]", e);
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

			requestMsg = JSON.parseObject(request, RequestMsg.class);
			// msgClassifyService.classify(requestMsg);

		} catch (Exception e) {

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
			String requestId = request.split("|", -1)[0];
			String retMsg = "ACK|" + requestId;
			return retMsg;
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