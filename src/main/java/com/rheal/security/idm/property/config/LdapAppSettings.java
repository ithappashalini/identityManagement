package com.rheal.security.idm.property.config;

import java.util.Map;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class LdapAppSettings {
	
    private String appId;
    private String containerBase;
    private Map<String, String> filters;
    
 	public String getContainerBase() {
		return containerBase;
	}
	public void setContainerBase(String containerBase) {
		this.containerBase = containerBase;
	}
	public Map<String, String> getFilters() {
		return filters;
	}
	public void setFilters(Map<String, String> filters) {
		this.filters = filters;
	}
	
	public String getFilter(String key) {
		if(filters != null) {
		    return filters.get(key);
		}
		return null;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}

}