package com.cvs.avocado.models;

public class ServerStats {

	private int appId;
	private int custId;
	private int deptId;
	private int adplAppId;
	private int pid;
	private String serverIP;
	private int serverPort;
	private int protocol;
	private String UUID;
	private String socketUUID;
	private int clientCount;
	private int sessAllowed;
	private int sessRejected;
	private int sessRejPolicies;
	private int sessRejCustId;
	private int sessRejDeptId;
	private int sessRejSla;
	private int sessRejOSFails;
	
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
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
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
	public String getUUID() {
		return UUID;
	}
	public void setUUID(String uUID) {
		UUID = uUID;
	}
	public String getSocketUUID() {
		return socketUUID;
	}
	public void setSocketUUID(String socketUUID) {
		this.socketUUID = socketUUID;
	}
	public int getClientCount() {
		return clientCount;
	}
	public void setClientCount(int clientCount) {
		this.clientCount = clientCount;
	}
	public int getSessAllowed() {
		return sessAllowed;
	}
	public void setSessAllowed(int sessAllowed) {
		this.sessAllowed = sessAllowed;
	}
	public int getSessRejected() {
		return sessRejected;
	}
	public void setSessRejected(int sessRejected) {
		this.sessRejected = sessRejected;
	}
	public int getSessRejPolicies() {
		return sessRejPolicies;
	}
	public void setSessRejPolicies(int sessRejPolicies) {
		this.sessRejPolicies = sessRejPolicies;
	}
	public int getSessRejCustId() {
		return sessRejCustId;
	}
	public void setSessRejCustId(int sessRejCustId) {
		this.sessRejCustId = sessRejCustId;
	}
	public int getSessRejDeptId() {
		return sessRejDeptId;
	}
	public void setSessRejDeptId(int sessRejDeptId) {
		this.sessRejDeptId = sessRejDeptId;
	}
	public int getSessRejSla() {
		return sessRejSla;
	}
	public void setSessRejSla(int sessRejSla) {
		this.sessRejSla = sessRejSla;
	}
	public int getSessRejOSFails() {
		return sessRejOSFails;
	}
	public void setSessRejOSFails(int sessRejOSFails) {
		this.sessRejOSFails = sessRejOSFails;
	}	
}
