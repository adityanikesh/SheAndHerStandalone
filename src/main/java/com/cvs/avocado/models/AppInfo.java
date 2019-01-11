package com.cvs.avocado.models;

public class AppInfo {

	private int appId;
	private int custId;
	private int deptId;
	private int adplAppId;
	private String serverIP;
	private int serverPort;
	private int protocol;
	private int pid;
	private String managementIP;
	private String uuid;
	private String socketUUID;
	
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public int getCustId() {
		return custId;
	}
	public void setCustId(int custId) {
		this.custId = custId;
	}
	public int getDeptId() {
		return deptId;
	}
	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}
	public int getAdplAppId() {
		return adplAppId;
	}
	public void setAdplAppId(int adplAppId) {
		this.adplAppId = adplAppId;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public int getProtocol() {
		return protocol;
	}
	public void setProtocol(int protocol) {
		this.protocol = protocol;
	}
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getManagementIP() {
		return managementIP;
	}
	public void setManagementIP(String managementIP) {
		this.managementIP = managementIP;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getSocketUUID() {
		return socketUUID;
	}
	public void setSocketUUID(String socketUUID) {
		this.socketUUID = socketUUID;
	}	
}
