package com.dusun.dubbo.service.redisService;

public interface FrontConnectionService {

	public String getFrontConnectionRecorde(String busiKey);
	
	public void setFrontConnectionRecorde(String busiKey,String frontIp);
	
	
}
