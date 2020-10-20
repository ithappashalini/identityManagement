package com.rheal.security.idm.rest.model;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class UpdateUserDetailsRequest {

	private String riskLevelStatus;
	private String userId;
	private String aecin;
	private String applicationName;

	public String getRiskLevelStatus() {
		return riskLevelStatus;
	}

	public void setRiskLevelStatus(String riskLevelStatus) {
		this.riskLevelStatus = riskLevelStatus;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAecin() {
		return aecin;
	}

	public void setAecin(String aecin) {
		this.aecin = aecin;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	@Override
	public String toString() {
		return "UpdateUserDetailsRequest [riskLevelStatus=" + riskLevelStatus + ", userId=" + userId + ", " + "aecin="
				+ aecin + ", " + "applicationName=" + applicationName + "]";
	}

}