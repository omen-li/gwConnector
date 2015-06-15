package com.dusun.front.busi;

import java.util.Map;

public class DataReport {

	public DataReport() {
		super();
	}

	public DataReport(String deviceId, String sensorIeee, Integer status,
			Map<String, String> attributes) {
		super();
		this.deviceId = deviceId;
		this.sensorIeee = sensorIeee;
		this.status = status;
		this.attributes = attributes;
	}

	private String deviceId;
	
	private String sensorIeee;
	
	private Integer status;
	
	private Map<String,String> attributes;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSensorIeee() {
		return sensorIeee;
	}

	public void setSensorIeee(String sensorIeee) {
		this.sensorIeee = sensorIeee;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}