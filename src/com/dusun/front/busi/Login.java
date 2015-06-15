package com.dusun.front.busi;

public class Login {

	public Login() {
		super();
	}

	public Login(String deviceId) {
		super();
		this.deviceId = deviceId;
	}

	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
}