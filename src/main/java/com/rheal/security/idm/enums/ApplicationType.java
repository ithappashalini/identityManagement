package com.rheal.security.idm.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public enum ApplicationType 
{
	rheal("rheal"),
	;

	private final String name;

	private ApplicationType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}  
	
	public static ApplicationType getApplicationByName(String name) {
		
		if(StringUtils.isBlank(name)) {
			return null;
		}
		return valueOf(name.toLowerCase());
	}
}
