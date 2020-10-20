package com.rheal.security.idm.rest.service;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rheal.security.idm.client.ldap.utils.CommonUtil;
import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.rest.handler.CreateUserHandler;
import com.rheal.security.idm.rest.handler.GetUserProfileHandler;
import com.rheal.security.idm.rest.handler.IdentityServiceHandler;
import com.rheal.security.idm.rest.model.AuthHeader;
import com.rheal.security.idm.rest.model.CreateUserRequest;
import com.rheal.security.idm.rest.model.GetUserProfile;
import com.rheal.security.idm.rest.model.GetUserProfileResponse;
import com.rheal.security.idm.rest.model.GuidResponse;
import com.rheal.security.idm.util.IDMUtils;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@RestController
@RequestMapping(value = "${spring.rest-context.path}")
public class UserAccessController {

	private static final Logger logger = LoggerFactory.getLogger(UserAccessController.class);

	@Autowired
	@Qualifier("serviceHandlerMap")
	private Map<String, IdentityServiceHandler<?, ?, ?>> serviceHandlerMap;

	@RequestMapping(value = "/createUser", method = RequestMethod.POST)
	public ResponseEntity<GuidResponse> createUser(HttpServletRequest request,
			@RequestBody CreateUserRequest createUserRequest)
			throws IDMException, UnsupportedEncodingException {

		long startTime = CommonUtil.startTimeLog(AppConstants.CREATE_USER_OPERATION);

		MDC.put("operation", AppConstants.CREATE_USER_OPERATION);

		AuthHeader authHeader = IDMUtils.extractAuthHeader(request);

		String openField = createUserRequest.getUserId() + ":"
				+ createUserRequest.getCompanyId();
		
		MDC.put("openField", openField);
		GuidResponse guidResponse = new GuidResponse();

		CreateUserHandler handler = (CreateUserHandler) serviceHandlerMap.get(AppConstants.CREATE_USER_OPERATION);
		
		try {		
			if (handler.validate(createUserRequest, authHeader)) { // if validated,
				guidResponse = handler.execute(createUserRequest, authHeader);
			}
			
			CommonUtil.endTimeLog(AppConstants.CREATE_USER_OPERATION, authHeader.getServiceAccount(), startTime, "");
			MDC.put("result", "Success");
			
			return new ResponseEntity<GuidResponse>(guidResponse, HttpStatus.OK);
		} catch (IDMException e) {

			MDC.put("errorId", e.getErrorID());
			MDC.put("errorMsg", e.getErrorMessage());
			MDC.put("result", "Failure");
			throw e;
		}
	}
	
	public ResponseEntity<GetUserProfileResponse> getUserProfile(HttpServletRequest request,
			@RequestBody GetUserProfile getUserProfileRequest)throws IDMException, UnsupportedEncodingException{
		
		long startTime = CommonUtil.startTimeLog(AppConstants.GET_USER_PROFILE_OPERATION);

		MDC.put("operation", AppConstants.GET_USER_PROFILE_OPERATION);

		AuthHeader authHeader = IDMUtils.extractAuthHeader(request);

		MDC.put("openField", getUserProfileRequest.getRequest().getUserGuid());
		MDC.put("guid", getUserProfileRequest.getRequest().getUserGuid());
		
		GetUserProfileHandler handler = (GetUserProfileHandler)serviceHandlerMap
				.get(AppConstants.GET_USER_PROFILE_OPERATION);
		
		GetUserProfileResponse response = new GetUserProfileResponse();
		try {
			if(handler.validate(getUserProfileRequest.getRequest(), authHeader)) {
				//if validated
				response = handler.execute(getUserProfileRequest.getRequest(), authHeader);
			}
			
			CommonUtil.endTimeLog(AppConstants.GET_USER_PROFILE_OPERATION, authHeader.getServiceAccount(), startTime, "");
			MDC.put("result", "Success");
			return new ResponseEntity<GetUserProfileResponse>(response, HttpStatus.OK);
	
		}catch(IDMException ex) {
			MDC.put("errorId", ex.getErrorID());
			MDC.put("errorMsg", ex.getErrorMessage());
			MDC.put("result", "Failure");
			throw ex;
		}
		
	}

	@RequestMapping(value = "/updateUserDetails", produces = {
			"application/json" }, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE, method = RequestMethod.POST)
	public ResponseEntity<GuidResponse> updateUserDetails(
			@RequestBody MultiValueMap<String, String> formParams, HttpServletRequest request) {
		logger.debug("********* ENTERTING REQUEST  UPDATEUSERDETAILS************");

		GuidResponse resp = new GuidResponse();

		logger.debug("********* EXITING REQUEST ************");
		return new ResponseEntity<GuidResponse>(resp, HttpStatus.OK);
	}

}
