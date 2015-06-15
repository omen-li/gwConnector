package com.omen.demo.busiPojo;

import com.alibaba.fastjson.JSON;

public class RetData {

	private String retCode;
	
	private String retMsg;
	
	private String content;
	

	public String getRetCode() {
		return retCode;
	}

	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}

	public String getRetMsg() {
		return retMsg;
	}

	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public static void main(String[] args) {
		RetData ret = new RetData();
		ret.setRetCode("0000");
		ret.setRetMsg("retMsg");
	}
	
	
	
}
