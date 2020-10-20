package com.rheal.security.idm.property.config;

import java.util.List;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class AppServiceAccount {
	
    private String userId;
    
    private String password;
    
    private List<AppAclSetting> accessControlList;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<AppAclSetting>  getAccessControlList() {
		return accessControlList;
	}

	public void setAccessControlList(List<AppAclSetting>  accessControlList) {
		this.accessControlList = accessControlList;
	}

   
}