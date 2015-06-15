package com.dusun.front.busi;

public class Sensor {

	public Sensor() {
		super();
	}

	public Sensor(String sensorType, String sensorIeee, String version,
			String battery, Integer batteryUnit, String serialNo, Integer status) {
		super();
		this.sensorType = sensorType;
		this.sensorIeee = sensorIeee;
		this.version = version;
		this.battery = battery;
		this.batteryUnit = batteryUnit;
		this.serialNo = serialNo;
		this.status = status;
	}

	private String sensorType;

	private String sensorIeee;

	private String version;

	private String battery;

	private Integer batteryUnit;

	private String serialNo;

	private Integer status;

	public String getSensorType() {
		return sensorType;
	}

	public void setSensorType(String sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorIeee() {
		return sensorIeee;
	}

	public void setSensorIeee(String sensorIeee) {
		this.sensorIeee = sensorIeee;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBattery() {
		return battery;
	}

	public void setBattery(String battery) {
		this.battery = battery;
	}

	public Integer getBatteryUnit() {
		return batteryUnit;
	}

	public void setBatteryUnit(Integer batteryUnit) {
		this.batteryUnit = batteryUnit;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
