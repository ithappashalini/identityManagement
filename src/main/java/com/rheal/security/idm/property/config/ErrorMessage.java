package com.rheal.security.idm.property.config;

import java.util.Map;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 25, 2019 1:18:47 PM
 *
 */
public class ErrorMessage {
	
    private String id;
    private String message;
    private Map<String, String> descriptions;
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, String> getDescriptions() {
		return descriptions;
	}
	public void setDescriptions(Map<String, String> descriptions) {
		this.descriptions = descriptions;
	}
	
	public String getDescription(String appName) {
		if (descriptions != null) {
			if(descriptions.containsKey(appName)) {
				return descriptions.get(appName);
			} else {
				return descriptions.get("any");
			}
		}
		return null;
	}
	public String getDescription() {
		if (descriptions != null) {
			return descriptions.get("any");
		}
		return null;
	}
	
	public String toString(){
		
		return  "<" + getId() + ", " + getDescription() + ", " + getMessage() + ">";
	}
}