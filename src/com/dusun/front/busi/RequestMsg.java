package com.dusun.front.busi;

public class RequestMsg {

	public RequestMsg() {
		super();
	}

	public RequestMsg(String mac, String version, int reqType, String content,
			String timeStamp, String permit, String remarks1, String remarks2) {
		super();
		this.mac = mac;
		this.version = version;
		this.reqType = reqType;
		this.content = content;
		this.timeStamp = timeStamp;
		this.permit = permit;
		this.remarks1 = remarks1;
		this.remarks2 = remarks2;
	}

	private String mac;

	private String version;

	private int reqType;

	private String content;

	private String timeStamp;

	private String permit;

	private String remarks1;

	private String remarks2;

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public int getReqType() {
		return reqType;
	}

	public void setReqType(int reqType) {
		this.reqType = reqType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPermit() {
		return permit;
	}

	public void setPermit(String permit) {
		this.permit = permit;
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