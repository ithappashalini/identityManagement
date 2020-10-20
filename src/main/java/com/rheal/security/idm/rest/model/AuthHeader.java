package com.rheal.security.idm.rest.model;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class AuthHeader {

	private String serviceAccount;
	
	public String getServiceAccount() {
		return serviceAccount;
	}


	public void setServiceAccount(String serviceAccount) {
		this.serviceAccount = serviceAccount;
	}

	private String password;
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}



	
	
	@Override
	public String toString() {
		return "CreateUserRequest [serviceAccount=" + serviceAccount + ", password=" + password  + "]";
	}

}