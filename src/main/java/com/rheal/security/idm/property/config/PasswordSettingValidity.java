package com.rheal.security.idm.property.config;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class PasswordSettingValidity {
	
    private boolean enabled;
    private int expiryInDays;

    
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public int getExpiryInDays() {
		return expiryInDays;
	}
	public void setExpiryInDaysm(int expiryInDays) {
		this.expiryInDays = expiryInDays;
	}
}