package com.rheal.security.idm.property.config;

import java.util.Map;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class LdapAppOperationSettings {
	
    private String name;
    private String[] readAttrs;
    private String searchFilter;

    private Map<String, String> parameters;
    
   	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getReadAttrs() {
		return readAttrs;
	}

	public void setReadAttrs(String[] readAttrs) {
		this.readAttrs = readAttrs;
	}

	public String getSearchFilter() {
		return searchFilter;
	}

	public void setSearchFilter(String searchFilter) {
		this.searchFilter = searchFilter;
	}

	/**
	 * @return the parameters
	 */
	public Map<String, String> getParameters() {
		return parameters;
	}

	/**
	 * @param parameters the parameters to set
	 */
	public void setParameters(Map<String, String> parameters) {
		this.parameters = parameters;
	}
	
	public String getParameter(String name){
		
		if(parameters != null) {
			
			return parameters.get(name);
		}
		return null;
	}

}