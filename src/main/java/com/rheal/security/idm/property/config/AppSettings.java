package com.rheal.security.idm.property.config;

/**
 * 
 * @author Prashanth Errabelli
 * 
 *
 */
public class AppSettings {
	
	private String appId;
    private PasswordSettings password;
  
    public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public PasswordSettings getPassword() {
		return password;
	}
	public void setPassword(PasswordSettings password) {
		this.password = password;
	}
}