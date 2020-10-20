package com.rheal.security.idm.property.config;

import javax.validation.constraints.Null;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class PasswordSettingComplexity {
	
	
	@Null
    private int minLength;
    private int minAlphabets;
    private int minNumeric;
    private boolean notIncludeUserIdCheck;
    
	public int getMinLength() {
		return minLength;
	}
	public void setMinLength(int minLength) {
		this.minLength = minLength;
	}
	public int getMinAlphabets() {
		return minAlphabets;
	}
	public void setMinAlphabets(int minAlphabets) {
		this.minAlphabets = minAlphabets;
	}
	public int getMinNumeric() {
		return minNumeric;
	}
	public void setMinNumeric(int minNumeric) {
		this.minNumeric = minNumeric;
	}
	/**
	 * @return the notIncludeUserIdCheck
	 */
	public boolean isNotIncludeUserIdCheck() {
		return notIncludeUserIdCheck;
	}

	/**
	 * @param notIncludeUserIdCheck the notIncludeUserIdCheck to set
	 */
	public void setNotIncludeUserIdCheck(boolean notIncludeUserIdCheck) {
		this.notIncludeUserIdCheck = notIncludeUserIdCheck;
	}
}