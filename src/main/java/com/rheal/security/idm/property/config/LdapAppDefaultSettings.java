package com.rheal.security.idm.property.config;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Prashanth Errabelli
 * 
 *
 */
public class LdapAppDefaultSettings {
	
	private String containerBase;
    private List<LdapAppOperationSettings> operationSettings;
    
    public String getContainerBase() {
		return containerBase;
	}
	public void setContainerBase(String containerBase) {
		this.containerBase = containerBase;
	}
	
	public List<LdapAppOperationSettings> getOperationSettings() {
		return operationSettings;
	}
	public void setOperationSettings(List<LdapAppOperationSettings> operationSettings) {
		this.operationSettings = operationSettings;
	}

}