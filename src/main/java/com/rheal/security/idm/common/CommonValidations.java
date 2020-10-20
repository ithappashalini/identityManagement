package com.rheal.security.idm.common;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rheal.security.idm.enums.ApplicationType;
import com.rheal.security.idm.property.config.AppConfig;
import com.rheal.security.idm.property.config.AppSettings;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.util.IDMUtils;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Component
public class CommonValidations {

	private static final Logger logger = LoggerFactory.getLogger(CommonValidations.class);

	@Autowired
	private Map<String, ErrorMessage> idmErrors;

	@Autowired
	private AppConfig appConfig;

	public boolean validateAppPassword(String userId, String password, AppSettings appSettings) throws IDMException {

		logger.debug("{} : password management in validateAppPassword");
		
		int minLength = appSettings.getPassword().getComplexity().getMinLength();
		int minAlph  =  appSettings.getPassword().getComplexity().getMinAlphabets();
		int minNumeric = appSettings.getPassword().getComplexity().getMinNumeric(); 
		
		ErrorMessage error;

		if (StringUtils.isBlank(password)) {

			error = idmErrors.get("E009");
			logger.error("Missing Input <Password>, {}-{}", error.getId(), error.getDescription());
			
			throw new IDMException(error, "Missing Input variable");
		}
		
		if (minLength <= 0) {
			minLength = appConfig.getDefaultSettings().getPassword().getComplexity().getMinLength();
		}
		if (password.length() < minLength) {
			
			error = idmErrors.get("E103");
			logger.error("{} : Invalid Input <password>, {}-{}: {}", error.getId(), error.getDescription());
			throw new IDMException(error, "Invalid password length");
		}

		if (minAlph < 1) {
			
			minAlph = 1;
		}

		int passCharLen = IDMUtils.countChar(password);
		if (passCharLen < minAlph) {

			logger.debug("{} : Enrolled in password complexity: minAplh",  minAlph);
		
			error = idmErrors.get("E104");
			logger.error("Invalid Input <Password>, {}-{}: {}",  error.getId(), error.getDescription());
			
			throw new IDMException(error, "Invalid Input variable");
		}

		if (minNumeric < 1) {
			
			minNumeric = 1;
		}
		int passNumLen = IDMUtils.countNum(password);

		if (passNumLen < minNumeric) {
			
			logger.debug("{} : Enrolled in password complexity : Minnumeric", minNumeric);
			
			error = idmErrors.get("E105");
			logger.error("Invalid Input <Password>, {}-{}: {}", error.getId(), error.getDescription());
			
			throw new IDMException(error, "Invalid Input variable");
		}

		
		if (appSettings.getPassword().getComplexity().isNotIncludeUserIdCheck()) {
			if (password.toLowerCase().contains(userId.toLowerCase())) {

				error = idmErrors.get("E106");
				logger.error("Invalid Input <Password>, {}-{}: {}", error.getId(), error.getDescription());
				
				throw new IDMException(error, "Invalid Input variable");

			}
		}
		return true;
	}


	/**
	 * validating applicationName value.
	 */
	public boolean validateApplicationName(String applicationName) throws IDMException {

		ErrorMessage error;
		validateRequiredApplicationName(applicationName);

		try {
			logger.debug("validateApplicationName : <ApplciationName>, {}:", applicationName);
			ApplicationType.getApplicationByName(applicationName);
			return true;

		} catch (IllegalArgumentException e) {
			error = idmErrors.get("E028");
			logger.error("{} : Invalid Input <ApplicationName>, {}-{}: {}", error.getId(), error.getDescription(), applicationName,e);
			
			throw new IDMException(error, "Invaild Input variable");
		}

	}

	/**
	 * validate applicationname is exist or not.
	 * 
	 * @param applicationName
	 * @return
	 * @throws IDMException
	 */
	public boolean validateRequiredApplicationName(String applicationName) throws IDMException {
		ErrorMessage error;
		if (StringUtils.isBlank(applicationName)) {
			error = idmErrors.get("E005");
			logger.error("Missing input <ApplciationName>, {}-{}", error.getId(), error.getDescription());
		
			throw new IDMException(error, "Missing Input variable");
		}
		return true;
	}

	/**
	 * Validating required userGuid.
	 * 
	 * @param userGuid
	 * @throws IDMException
	 */
	public boolean validateUserGUId(String userGuid) throws IDMException {
		ErrorMessage error;
		if (StringUtils.isBlank(userGuid)) {
			error = idmErrors.get("E003");
			logger.error("Missing Input <userGuid>, , {}-{}", error.getId(), error.getDescription());
			
			throw new IDMException(error, "Missing Input variable");
		} else {

			String value = "" + userGuid.charAt(userGuid.length() - 1);
			if (("*".equals(value))) {
				error = idmErrors.get("E002");
				logger.error("Invalid Search Criteria <userGuid>, {}-{}", error.getId(), error.getDescription());
				
				throw new IDMException(error, "Invalid Search Criteria");
			}
		}

		return true;
	}

	/**
	 * if Email value exist then check for valide email.
	 * 
	 * @param email
	 * @return boolean
	 */
	public boolean validateEmail(String email) {
		boolean validateEmail = false;
		String emailPattern = "^\\w([-\\.']?\\w)*@(\\w[-\\w]*\\w\\.)+[a-zA-Z]{2,9}$";
		if (!StringUtils.isBlank(email)) {
			if (email.trim().matches(emailPattern)) {
				validateEmail = true;
			}
		}
		return validateEmail;
	}

	public boolean validateServiceAccountCredentials(String serviceAccountId, String serviceAccountPassword) throws IDMException {
		ErrorMessage error;
		
		
		if (StringUtils.isBlank(serviceAccountId)) {
			error = idmErrors.get("E100");
			logger.error("Missing Input <serviceAcccountId>, {}-{}", error.getId(), error.getDescription());

			throw new IDMException(error, "Enter ServiceAccount ID ");
		}
		if (StringUtils.isBlank(serviceAccountPassword)) {
			error = idmErrors.get("E101");
			logger.error("Missing Input <serviceAcccountPassword>, {}-{}", error.getId(), error.getDescription());
			throw new IDMException(error, "Enter Service password");
		}
		
		return true;
	}

	/**
	 * validating user id value.
	 */
	public boolean validateUserId(String userId) throws IDMException {
		ErrorMessage error;
		if (StringUtils.isBlank(userId)) {
			error = idmErrors.get("E013");
			logger.error("User ID is required but missing, {}-{}", error.getId(), error.getDescription());
			
			throw new IDMException(error, "Missing Input variable");
		}
		return true;
	}

	/**
	 * validate companyId exist or not
	 * 
	 */
	public boolean validateCompanyId(String companyId) throws IDMException {
		ErrorMessage error;
		if (StringUtils.isBlank(companyId)) {
			error = idmErrors.get("E014");
			logger.error(" Company ID is required but missing, {}-{}", error.getId(),
					error.getDescription());
			
			throw new IDMException(error, "Missing Input variable");
		}
		return true;
	}

	public boolean validateNewUserID(String newUserID) throws IDMException {

		ErrorMessage error;
		if (StringUtils.isBlank(newUserID)) {
			error = idmErrors.get("E012");
			logger.error("New UserID is required but missing, {}-{}", error.getId(),
					error.getDescription());
			throw new IDMException(error, "Missing Input variable");
		}
		return true;
	}

	public void checkForStarSearch(String searchData) throws IDMException {

		String value = "" + searchData.charAt(searchData.length() - 1);

		if ("*".equals(value)) {
			ErrorMessage error = idmErrors.get("E002");
			throw new IDMException(error, "Invalid Search Criteria(*) search not allowed");

		}
	}

}
