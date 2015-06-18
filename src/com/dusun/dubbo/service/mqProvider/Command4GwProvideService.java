package com.dusun.dubbo.service.mqProvider;

import com.dusun.dubbo.service.bo.mqService.Command4Gw;

public interface Command4GwProvideService {
	
	public void sendCommand(Command4Gw command)throws Exception;

}
