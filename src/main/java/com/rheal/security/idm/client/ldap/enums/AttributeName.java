package com.rheal.security.idm.client.ldap.enums;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public enum AttributeName {
	
	   GUID("cn"),
	   COMPANY("departmentNumber"),
	   FIRST_NAME("givenName"),
	   LAST_NAME("sn"),
	   DN("DN"),
	   PASSWORD("userPassword"),
	   UID("uid"),
	   ;
	   
	private final String ldapName;
	
	private AttributeName(String ldapName) {
		this.ldapName = ldapName;
	}
	
	public String getLdapName() {
        return ldapName;
}

	public static String getAttributeName(String name) {
		return valueOf(name).toString();
	}
	 
	  
}
