package com.dusun.dubbo.service.mqProvider;

import com.dusun.dubbo.service.bo.mqService.GwRequest;

public interface GwRequestProvideService {
	
	public void sendRequest(GwRequest gwRequest)throws Exception;

}
