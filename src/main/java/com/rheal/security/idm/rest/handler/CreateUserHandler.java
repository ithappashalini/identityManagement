package com.rheal.security.idm.rest.handler;

import java.util.Map;

import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttributes;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rheal.security.idm.client.ldap.LdapOps;
import com.rheal.security.idm.client.ldap.enums.AttributeName;
import com.rheal.security.idm.client.ldap.utils.AttributeUtil;
import com.rheal.security.idm.client.ldap.utils.CommonUtil;
import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.common.CommonValidations;
import com.rheal.security.idm.enums.ApplicationType;
import com.rheal.security.idm.property.config.AppConfig;
import com.rheal.security.idm.property.config.AppSettings;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.rest.model.AuthHeader;
import com.rheal.security.idm.rest.model.CreateUserRequest;
import com.rheal.security.idm.rest.model.GuidResponse;
import com.rheal.security.idm.util.IDMUtils;
import com.rheal.security.idm.ws.exception.IDMException;
import com.rheal.security.util.SHAHashing;

/**
 * 
 * @author Prashanth Errabelli
 *
 */


@Component("createUserHandler")
public class CreateUserHandler
		implements IdentityServiceHandler<CreateUserRequest, GuidResponse, AuthHeader> {

	private static final Logger logger = LoggerFactory.getLogger(CreateUserHandler.class);

	private String operationName = AppConstants.CREATE_USER_OPERATION;

	@Autowired
	private LdapOps ldapOps;

	@Autowired 
	AppConfig appConfig;
	
	@Autowired
	private Map<ApplicationType, AppSettings> appSettingsMap;

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	private Map<String, ErrorMessage> idmErrors;

	@Override
	public GuidResponse execute(CreateUserRequest request, AuthHeader authHeader)
			throws IDMException {

		logger.debug("{} : Begin createUser execution", operationName);

		
		logger.debug("execute >>>>>>>> Checking For duplicate userid <{}> in companyid <{}> and App <{}>",
				request.getUserId(), request.getCompanyId(), request.getApplicationName());

		Boolean isDupilicate = ldapOps.isUserNameDuplicate(request.getUserId(), request.getApplicationName(),
				request.getCompanyId());

		if (isDupilicate) {

			logger.debug("execute >>>>>>>> found duplicate userid <{}> in companyid <{}> and App <{}>",
					request.getUserId(), request.getCompanyId(), request.getApplicationName());

			ErrorMessage error;
			error = idmErrors.get("E029");

			logger.error("{} : New User ID already exists, {} {} {} {}", operationName, error.getId(),
					error.getDescription(), request.getUserId());

			throw new IDMException(error, "New User ID already exists");
		} else {
			
			logger.debug("execute >>>>>>>> creating user  with userid <{}> in companyid <{}> and App <{}>",
					request.getUserId(), request.getCompanyId(), request.getApplicationName());

			ApplicationType applicationType = ApplicationType
					.getApplicationByName(request.getApplicationName());

			String randomGUID = IDMUtils.generateUniqueGUID();
			Attributes attrs = new BasicAttributes();

			attrs.put(AttributeName.GUID.getLdapName(), CommonUtil.checkNull(randomGUID));
			attrs.put(AttributeName.COMPANY.getLdapName(), CommonUtil.checkNull(request.getCompanyId()));
			
			attrs.put(AttributeName.FIRST_NAME.getLdapName(), CommonUtil.checkNull(request.getFirstName()));
			attrs.put(AttributeName.LAST_NAME.getLdapName(), CommonUtil.checkNull(request.getLastName()));
			attrs.put(AttributeName.UID.getLdapName(), CommonUtil.checkNull(request.getUserId()));
			attrs.put(AttributeName.PASSWORD.getLdapName(), CommonUtil.checkNull(request.getPassword()));
			/*
			AppSettings appSettings = null;
			
			if(appSettingsMap != null && appSettingsMap.get(applicationType) != null ){
				appSettings = appSettingsMap.get(applicationType);
			}
			
			String passwordHistoryHashVal = getPasswordHistory(appSettings, CommonUtil.checkNull(request.getPassword()));
			if (StringUtils.isNotBlank(passwordHistoryHashVal)) {
				attrs.put(AttributeName.PASSWORDHISTORY.getLdapName(),
						CommonUtil.checkNull(passwordHistoryHashVal));
			}
			String expiryDate = getPasswordValidity(appSettings);
			if (StringUtils.isNotBlank(expiryDate)) {
				attrs.put(AttributeName.PASSWORDEXPIRYDATE.getLdapName(), CommonUtil.checkNull(expiryDate));
			}*/
		    attrs = AttributeUtil.removeNullValueAttributes(attrs);

			try {
				ldapOps.createUser(authHeader.getServiceAccount(), attrs, request.getApplicationName(), randomGUID);
			} catch (IDMException igf) {
				throw igf;
			} catch (Exception e) {
				ErrorMessage error;

				error = idmErrors.get("E000");
				logger.error(": createUser >> create User : In Exception:{} ", e);

				throw new IDMException(error, "");
			}
			GuidResponse guidResponse = new GuidResponse();
			guidResponse.setGuid(randomGUID);
			logger.debug(": createUser>> create User is success");
			logger.debug("{} : End createUser execution", operationName);

			return guidResponse;
		}
	}

	@Override
	public boolean validate(CreateUserRequest request, AuthHeader authHeader)
			throws IDMException {

		logger.debug("{} : Begin createUser validations", operationName);

		logger.debug("{}: Start Client Validations of createUser", operationName);

		commonValidations.validateUserId(request.getUserId());

		commonValidations.validateApplicationName(request.getApplicationName());

	    ApplicationType applicationType = ApplicationType.getApplicationByName(request.getApplicationName());

	    AppSettings appSettings = null;
		
		if(appSettingsMap != null && appSettingsMap.get(applicationType) != null ){
			appSettings = appSettingsMap.get(applicationType);
		}

		if (StringUtils.isBlank(request.getPassword())) {
			ErrorMessage error;
			error = idmErrors.get("E030");
			logger.debug("{}:  Password is required but missing", operationName);
			throw new IDMException(error, "Missing Input variable");
		}

		validatePasswordComplexity(appSettings, request.getPassword(), request.getUserId(),
					applicationType.toString());


		commonValidations.validateCompanyId(request.getCompanyId());

		logger.debug("{} : End createUser validations", operationName);

		return true;
	}

	@Override
	public String getOperation() {
		return operationName;
	}

	private boolean validatePasswordComplexity(AppSettings appSettings, String newPassword, String userID,
			String appName) throws IDMException {

		logger.debug("{} : password management ", operationName);

		if (appSettings == null || appSettings.getPassword() == null
				|| appSettings.getPassword().getComplexity() == null) {
			logger.debug("{} : App Specific password complexity settings are not configured, using default settings", operationName);
			appSettings = appConfig.getDefaultSettings();
		}

		commonValidations.validateAppPassword(userID, newPassword, appSettings);
	
		return true;
	}

	public String getPasswordHistory(AppSettings appSettings, String newPassword, String reqUserUpdate) {

		if (appSettings == null || appSettings.getPassword() == null
				|| appSettings.getPassword().getManagement() == null) {
			logger.debug("{} : App Specific password management settings are not configured, using default settings", operationName);
			appSettings = appConfig.getDefaultSettings();
		}
		
		boolean historyEnabled = appSettings != null && appSettings.getPassword() != null
				&& appSettings.getPassword().getManagement().isEnableHistory();

		int passwordHistoryNum = appSettings != null && appSettings.getPassword() != null
				&& appSettings.getPassword().getManagement() != null
						? appSettings.getPassword().getManagement().getHistoryNum()
						: 0;
		boolean addHistory = historyEnabled
				&& ("true".equalsIgnoreCase(reqUserUpdate) || "1".equalsIgnoreCase(reqUserUpdate))
				&& (passwordHistoryNum > 0);

		String passwordHistoryHashValue = null;
		if (addHistory) {

			passwordHistoryHashValue = SHAHashing.getHashVal(newPassword);
		}
		logger.debug("{} -- <historyEnabled = {}> <hashVal= {}>", addHistory, passwordHistoryHashValue);
		return passwordHistoryHashValue;
	}

	private String getPasswordValidity(AppSettings appSettings) {

		if (appSettings == null || appSettings.getPassword() == null
				|| appSettings.getPassword().getValidity() == null) {
			logger.debug("{} : App Specific password validity settings are not configured, using default settings", operationName);
			appSettings = appConfig.getDefaultSettings();
		}
		
		if (appSettings != null && appSettings.getPassword() != null
				&& appSettings.getPassword().getValidity() != null
				&& appSettings.getPassword().getValidity().isEnabled()) {

			int expiryInDays = appSettings.getPassword().getValidity().getExpiryInDays();

			logger.debug("{} : UpdatepasswordExpiryDate: <days = {}>", operationName, expiryInDays);
			String futureDateTime = CommonUtil.getFutureDateTime(expiryInDays);
			logger.debug("{} : Formatted expirtDate:{}", operationName, futureDateTime);

		}
		return null;
	}
}
