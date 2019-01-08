package com.cvs.avocado.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariables {
	
	@Value("${server.ssl.key-store}")
	private String sslKeyStore;
	
	@Value("${server.ssl.key-store-password}")
	private String sslKeyStorePassword;
	
	@Value("${server.port}")
	private String port;
	
//	public EnvironmentVariables() {
//		System.out.println("Inside constructor");
//		System.out.println("Port:"+port);
//	}

	public String getSslKeyStore() {
		return sslKeyStore;
	}

	public void setSslKeyStore(String sslKeyStore) {
		this.sslKeyStore = sslKeyStore;
	}

	public String getSslKeyStorePassword() {
		return sslKeyStorePassword;
	}

	public void setSslKeyStorePassword(String sslKeyStorePassword) {
		this.sslKeyStorePassword = sslKeyStorePassword;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public void print() {
		System.out.println(".............Hellooooo................");
	}
	
	
}
