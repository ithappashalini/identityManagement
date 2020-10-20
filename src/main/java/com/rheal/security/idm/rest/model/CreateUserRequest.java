package com.rheal.security.idm.rest.model;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class CreateUserRequest {

	private String userId;
	private String companyId;
	private String firstName;
	private String lastName;
	private String applicationName;
	private String password;

	
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	@Override
	public String toString() {
		return "CreateUserRequest [lastName=" + lastName + ", userId=" + userId + ", " + "firstName="
				+ firstName + ", " + "companyId=" + companyId +  ", " + "applicationName=" + applicationName +"]";
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}