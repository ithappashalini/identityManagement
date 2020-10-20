package com.rheal.security.idm.common;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class AppConstants {

	private AppConstants() {
		throw new IllegalStateException("Utility class");
	}

	public static final String REQUEST_TYPE_CHALLENGE = "CHALLENGE";
	public static final String REQUEST_TYPE_UPDATE_USER = "UPDATEUSER";
	public static final String CREATEUSER = "CREATEUSER";
	public static final String NUMBER_OF_QUESTIONS = "1";
	public static final String ACTION_TYPE_CHALLENGE_USER = "SET_USER_QUESTION";
	public static final String ACTION_TYPE_GENERIC = "SET_USER_STATUS";
	public static final String ACTION_TYPE_DEVICE = "UPDATE_DEVICE";
	public static final String FALL_BACK_MESSAGE = "Back end service is either down or under maintenance";
	public static final String BINDING_TYPE_HARD_BIND = "HARD_BIND";
	public static final String RISK_TYPE_NONE = "NONE";
	public static final String RISK_TYPE_ALL = "ALL";
	public static final String USER_COUNTRY = "US";
	public static final String BROWSE_QUESTION = "BROWSE_QUESTION";
	public static final String USER_LANGUAGE = "EN";
	public static final String USER_TYPE_PERSISTENT = "PERSISTENT";
	public static final String USER_TYPE_NONPERSISTENT = "NONPERSISTENT";
	public static final String API_TYPE = "DIRECT_SOAP_API";
	public static final String VERSION = "7.0";
	public static final String METHOD = "PASSWORD";
	public static final String CREDENTIAL_PROVISIONING_STATUS = "ACTIVE";
	public static final String GET_USER_QUESTION = "GET_USER_QUESTION";
	public static final String CHANNEL_INDICATOR_WEB = "WEB";
	public static final String WMA = "WMA";

	public static final String DEVICE_ACTION_TYPES = "UPDATE_DEVICE";

	public static final String REQUEST_TYPE_AUTHENTICATE = "AUTHENTICATE";

	public static final int SUCCESS_STATUS = 200;
	public static final String SUCCESS_STATUS_STR = "200";
	public static final String CHALLEGEMODEANALYSE = "CHALLEGEMODEANALYSE";
	public static final String ANALYZE = "ANALYZE";
	public static final String EVENT_TYPE_SESSION_SIGNIN = "SESSION_SIGNIN";
	public static final String MOBILE_DEVICE = "MobileDevice";
	public static final String BROWSER = "Browser";
	public static final String CHANNEL_INDICATOR_MOBILE = "Mobile";
	public static final String REQUEST_TYPE_QUERY = "QUERY";

	public static final String OCM_SESSION_ID = "OCMUserSessionID";
	public static final String ENVIRONMENT = "DAI_ENV";
	// user Status
	public static final String USER_STATUS_NOTENROLLED = "NOTENROLLED";
	public static final String USER_STATUS_UNVERIFIED = "UNVERIFIED";
	public static final String USER_STATUS_VERIFIED = "VERIFIED";
	public static final String ACTION_CODE_CHALLENGE = "CHALLENGE";
	public static final String USER_STATUS_LOCKOUT = "LOCKOUT";
	public static final String ACTION_CODE_ALLOW = "ALLOW";
	public static final String ACTION_CODE_NONE = "NONE";
	public static final String ACTION_CODE_DENY = "DENY";
	public static final String ACTION_CODE_REVIEW = "REVIEW";
	public static final String ACTION_CODE_UNLOCKED = "UNLOCKED";

	public static final String MANDATORY_PARAMETER_MISSING = "Mandatory Parameter missing --> ";
	public static final String USER_NAME = "username";
	public static final String ORG_NAME = "OCM";
	public static final String USER_STATUS = "userStatus";
	public static final String CLIENT_SESSION_ID = "clientSessionId";
	public static final String SESSION_ID = "sessionId";
	public static final String TRANSACTION_ID = "transactionId";
	public static final String CHANNEL_INDICATION = "channelIndicator";
	public static final String OTHER_ID = "otherId";
	public static final String EVENT_TYPE = "eventType";
	public static final String WEB_BROWSER = "BROWSER";
	public static final String ENROLL = "ENROLL";
	public static final String UPDATESQA = "UPDATESQA";
	public static final String UNLOCK = "UNLOCK";
	public static final String CHALLENGE = "CHALLENGE";
	public static final String USER_TYPE = "usertype";
	public static final String REQUEST_TYPE = "requesttype";

	public static final String HTTP_HEADER_CHANNEL = "channel";
	public static final String NB = "NB";
	public static final String NM = "NM";
	public static final String IP_ADDRESS = "ipAddress";

	public static final String FORCE_CHALLENGE = "FORCE_CHALLENGE";

	public static final String CREATE_USER_OPERATION = "createUser";
	public static final String UPDATE_USER_OPERATION = "updateUserProfile";
	public static final String SEARCH_USERS_OPERATION = "searchOIMUsers";
	public static final String UPDATE_PASSWORD_OPERATION = "updatePassword";
	public static final String CREATE_LWC_OPERATION = "createLWC";
	public static final String ACTIVATE_USER_OPERATION = "activateUser";
	public static final String RESET_PASSWORD_OPERATION = "resetPasswordLockout";
	public static final String DEACTIVATE_USER_OPERATION = "deactivateUser";
	public static final String DELETE_USER_OPERATION = "deleteUser";
	public static final String DELETE_LWC_OPERATION = "deleteLWC";
	public static final String SEARCH_LWC_BY_APP_OPERATION = "searchLWCByApplicationUser";
	public static final String SEARCH_LWC_BY_EMAIL_OPERATION = "searchLWCByEmail";
	public static final String LWC_DELIVERY_FAILED_OPERATION = "lwcDeliveryFailed";
	public static final String GET_GRP_MEMBERS_OPERATION = "getGroupMembers";
	public static final String ADD_GRP_MEMBER_OPERATION = "addGroupMember";
	public static final String REMOVE_GROUP_MEMBER_OPERATION = "removeGroupMember";
	public static final String GET_ALL_EXPIRED_LWC_OPERATION = "getAllExpiredLWC";
	public static final String GET_TERMS_AND_CONDITIONS_OPERATION = "getTermsandConditions";
	public static final String GET_MULTIPLE_TERMS_AND_CONDITIONS_OPERATION = "getMultipleTermsandConditions";
	public static final String UPDATE_TERMS_AND_CONDITIONS_OPERATION = "updateTermsAndConditions";
	public static final String SEARCH_INACTIVE_USERS_OPERATION = "searchInactiveOIMUsers";
	public static final String GET_USER_PROFILE_FOR_AECIN_OPERATION = "getUserProfileForAECIN";
	public static final String UPDATE_SQA_OPERATION = "updateSecretQuestionAnswer";
	public static final String VALIDATE_SQA_OPERATION = "validateSecretQuestionAnswer";
	public static final String GET_USER_STATUS_OPERATION = "getUserStatus";
	public static final String GET_USER_GUID_OPERATION = "getUserGuid";
	public static final String UPDATE_FED_PROFILE_OPERATION = "updateFederationProfile";
	public static final String MODIFY_FRAUD_STATUS_OPERATION = "modifyFraudStatus";
	public static final String GET_USER_PROFILE_OPERATION = "getUserProfile";
	public static final String UPDATE_USER_ID_OPERATION = "updateUserID";
	public static final String UPDATE_USER_FRAUD_STATUS = "updateUserFraudStatus";

	public static final String DEACTIVATED = "DEACTIVATED";
	public static final String ACTIVATED = "ACTIVATED";

	public static final String LOCKED = "LOCKED";
	public static final String UNLOCKED = "UNLOCKED";

	public static final String TRUE = "TRUE";
	public static final String FALSE = "FALSE";

	public static final String String_Empty = "";
	public static final String String_Space = " ";

	public static final String String_Pipe = "|";
	public static final String FRAUD_LOCK_NC = "NC";
	public static final String FRAUD_LOCK_E = "E";
	public static final String RSAOrgName_MOU = "MOU";
	public static final String LDAPTimeStampPattern = "yyyyMMddHHmmss'.0Z'";

	public static final String STRING_ASTERISK = "*";
	public static final String AUTHORIZATION_KEY = "Authorization";
	public static final String AUTHORIZATION_SCHEMA = "Bearer";

}
