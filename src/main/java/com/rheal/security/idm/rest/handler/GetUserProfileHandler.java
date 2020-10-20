package com.rheal.security.idm.rest.handler;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.rheal.security.idm.client.ldap.LdapOps;
import com.rheal.security.idm.client.ldap.enums.AttributeName;
import com.rheal.security.idm.client.ldap.utils.CommonUtil;
import com.rheal.security.idm.client.ldap.utils.InputSanitizer;
import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.common.CommonValidations;
import com.rheal.security.idm.enums.ApplicationType;
import com.rheal.security.idm.property.config.AppConfig;
import com.rheal.security.idm.property.config.AppSettings;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.property.config.LdapAppOperationSettings;
import com.rheal.security.idm.rest.model.AuthHeader;
import com.rheal.security.idm.rest.model.CreateUserRequest;
import com.rheal.security.idm.rest.model.GetUserProfileRequeset;
import com.rheal.security.idm.rest.model.GetUserProfileResponse;
import com.rheal.security.idm.rest.model.GuidResponse;
import com.rheal.security.idm.rest.model.UserProfile;
import com.rheal.security.idm.ws.exception.IDMException;

@Component("getUserProfileHandler")
public class GetUserProfileHandler 
	implements IdentityServiceHandler<GetUserProfileRequeset, GetUserProfileResponse, AuthHeader> {
	
	private static final Logger logger = LoggerFactory.getLogger(GetUserProfileHandler.class);

	private String operationName = AppConstants.GET_USER_PROFILE_OPERATION;

	@Autowired
	private LdapOps ldapOps;

	@Autowired
	private Map<String, LdapAppOperationSettings> operationLdapSettingMap;

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	private Map<String, ErrorMessage> idmErrors;


	
	@Override
	public boolean validate(GetUserProfileRequeset request, AuthHeader authHeader) throws IDMException {
		
		logger.debug("{} : Begin getUserProfile validations", operationName);
		
		commonValidations.validateUserGUId(request.getUserGuid());
		
		logger.debug("{} : End getUserProfile validations", operationName);
		
		return true;
	}

	@Override
	public GetUserProfileResponse execute(GetUserProfileRequeset request, AuthHeader authHeader) throws IDMException {
		logger.debug("{} : Begin getUserProfile execution", operationName);
		
		ErrorMessage error;
		
		LdapAppOperationSettings operationLdapSettings = operationLdapSettingMap.get(operationName);
		String filter = String.format(operationLdapSettings.getSearchFilter(), 
				InputSanitizer.escapeLDAPSearchFilter(request.getUserGuid()));
		
		Map<String, String>userDetails = null;
		
		try {
			userDetails = ldapOps.retrieveUser(authHeader.getServiceAccount(), request.getApplicationName(),
					filter, operationLdapSettings.getReadAttrs());
		}catch (IDMException ex) {
			throw ex;
		}catch (Exception e) {
			error = idmErrors.get("E002");
			logger.error("{} : Error Searching User {} {}", operationName, error.getId(), error.getDescription());
			
		}
		
		GetUserProfileResponse response = new GetUserProfileResponse();
		UserProfile userProfile = new UserProfile();
		
		for(String attr : operationLdapSettings.getReadAttrs()) {
			userDetails.put(attr, CommonUtil.checkNull(userDetails.get(attr)));
		}
		
		userProfile.setUserId(CommonUtil.checkNull(userDetails.get(AttributeName.UID.getLdapName())));
		userProfile.setCompanyId(CommonUtil.checkNull(userDetails.get(AttributeName.COMPANY.getLdapName())));
		userProfile.setFirstName(CommonUtil.checkNull(userDetails.get(AttributeName.FIRST_NAME.getLdapName())));
		userProfile.setLastName(CommonUtil.checkNull(userDetails.get(AttributeName.LAST_NAME.getLdapName())));
		userProfile.setGuid(CommonUtil.checkNull(userDetails.get(AttributeName.GUID.getLdapName())));
		
		response.setProfile(userProfile);
		
		logger.debug("{} : End getUserProfile execution", operationName);
		
		return response;
	}

	@Override
	public String getOperation() {
		return operationName;
	}

}
