package com.rheal.security.idm.client.ldap.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rheal.security.idm.client.ldap.enums.AttributeName;
import com.rheal.security.idm.client.ldap.enums.AttributeType;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class ModItem {

	private static final Logger logger = LoggerFactory.getLogger(ModItem.class);
	
	private Object oldValue;
	
	private Object newValue;
	
	private AttributeType type;
	
	private AttributeName name;
	
	
	public ModItem(AttributeName name, AttributeType type, Object oldValue, Object newValue) {
		
		this.type = type;
		this.name = name;
		this.oldValue = oldValue;
		this.newValue = newValue;
	}
	
	public Object getOldValue() {
		return oldValue;
	}


	
	public Object getNewValue() {
		return newValue;
	}


	


	public AttributeType getType() {
		return type;
	}


	


	public AttributeName getName() {
		return name;
	}




	
	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder("<");

		sb.append(name.getLdapName()).append("-").append(type.name()).append("-").append(oldValue).append("-").append(newValue).append(">");

		

		return sb.toString();
	}

}

