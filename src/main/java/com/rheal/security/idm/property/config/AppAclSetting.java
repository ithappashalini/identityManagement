package com.rheal.security.idm.property.config;

import java.util.List;

/**
 * 
 * @author Prashanth Errabelli
 * 
 *
 */
public class AppAclSetting {
	
	private String appId;
	private List<String> allowCreateAttributes;
    private List<String> allowModifyAttributes;
    private boolean allowRead;
    private boolean allowDelete;
  
    
    public boolean isAllowRead() {
		return allowRead;
	}
	public void setAllowRead(boolean allowRead) {
		this.allowRead = allowRead;
	}
	public boolean isAllowDelete() {
		return allowDelete;
	}
	public void setAllowDelete(boolean allowDelete) {
		this.allowDelete = allowDelete;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	public List<String> getAllowModifyAttributes() {
		return allowModifyAttributes;
	}
	public void setAllowModifyAttributes(List<String> allowModifyAttributes) {
		this.allowModifyAttributes = allowModifyAttributes;
	}
	public List<String> getAllowCreateAttributes() {
		return allowCreateAttributes;
	}
	public void setAllowCreateAttributes(List<String> allowCreateAttributes) {
		this.allowCreateAttributes = allowCreateAttributes;
	}

}