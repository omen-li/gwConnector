package com.omen.demo.handler;

import java.util.Map;

import com.omen.demo.busiPojo.RetData;

public interface ChatHandler {
	
	public RetData handle(Map<String,String> paramMap);

}
