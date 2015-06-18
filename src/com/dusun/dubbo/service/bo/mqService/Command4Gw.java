package com.dusun.dubbo.service.bo.mqService;

import java.io.Serializable;

public class Command4Gw  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String deviceId;
	
	private String commandType;
	
	private String commandName;
	
	private String commandId;
	
	private String commandValue;
	
	private String remarks1;
	
	private String remarks2;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getCommandType() {
		return commandType;
	}

	public void setCommandType(String commandType) {
		this.commandType = commandType;
	}

	public String getCommandName() {
		return commandName;
	}

	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public String getCommandId() {
		return commandId;
	}

	public void setCommandId(String commandId) {
		this.commandId = commandId;
	}

	public String getCommandValue() {
		return commandValue;
	}

	public void setCommandValue(String commandValue) {
		this.commandValue = commandValue;
	}

	public String getRemarks1() {
		return remarks1;
	}

	public void setRemarks1(String remarks1) {
		this.remarks1 = remarks1;
	}

	public String getRemarks2() {
		return remarks2;
	}

	public void setRemarks2(String remarks2) {
		this.remarks2 = remarks2;
	}
	
	
	

}
