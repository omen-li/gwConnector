package com.dusun.dubbo.service.mqProvider;

import com.dusun.dubbo.service.bo.mqService.GwResult;

public interface GwResultProvideService {

	public void sendResult(GwResult gwResult)throws Exception;
}
