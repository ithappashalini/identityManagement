package com.rheal.security.idm.property.config;

/**
 * 
 * @author Prashanth Errabelli
 * 
 *
 */
public class PasswordSettings {

	private PasswordSettingComplexity complexity;
    private PasswordSettingManagement management;
    private PasswordSettingValidity validity;
  
    public PasswordSettingComplexity getComplexity() {
		return complexity;
	}
	public void setComplexity(PasswordSettingComplexity complexity) {
		this.complexity = complexity;
	}
	public PasswordSettingManagement getManagement() {
		return management;
	}
	public void setManagement(PasswordSettingManagement management) {
		this.management = management;
	}
	/**
	 * @return the validity
	 */
	public PasswordSettingValidity getValidity() {
		return validity;
	}
	/**
	 * @param validity the validity to set
	 */
	public void setValidity(PasswordSettingValidity validity) {
		this.validity = validity;
	}

}