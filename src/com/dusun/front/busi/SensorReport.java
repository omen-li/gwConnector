package com.dusun.front.busi;

import java.util.List;

public class SensorReport {

	public SensorReport() {
		super();
	}

	public SensorReport(String devicedId, List<Sensor> sensors) {
		super();
		this.devicedId = devicedId;
		this.sensors = sensors;
	}

	private String devicedId;

	private List<Sensor> sensors;

	public String getDevicedId() {
		return devicedId;
	}

	public void setDevicedId(String devicedId) {
		this.devicedId = devicedId;
	}

	public List<Sensor> getSensors() {
		return sensors;
	}

	public void setSensors(List<Sensor> sensors) {
		this.sensors = sensors;
	}
}