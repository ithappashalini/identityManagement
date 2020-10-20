package com.rheal.security.idm.ws.exception;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rheal.security.idm.client.ldap.utils.DateUtil;
import com.rheal.security.idm.property.config.ErrorMessage;


/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */

public class IDMException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4771014327142975772L;

	private static final Logger logger = LoggerFactory.getLogger(IDMException.class);
	private static final Logger auditLogger = LoggerFactory.getLogger("com.rheal.auditlog");

	private String errorID;
	private String errorMessage;
	private String errorDescription;
	private String errorState;

	public IDMException(String operationName, ErrorMessage error, String errorDesc, String serviceAccountID,
			String strTranID, String userGuid, String strOpenField, long startTime, Timestamp startTimeStamp) {

		// need to check
		this(error, errorDesc);

		logger.error("{} : {} {} {}", operationName, errorDesc, error.getId(), error.getDescription());
		auditLogger.info("{}|{}||{}|Failure|{}|{}|{}|{}||{}|{}|", strTranID, operationName, serviceAccountID,
				startTimeStamp, DateUtil.getCurrentTimestamp(), System.currentTimeMillis() - startTime, userGuid,
				error.getId(), error.getMessage(), strOpenField);
	}

	public IDMException(ErrorMessage erorrMessageConfig, String p3) {

		this.errorID = erorrMessageConfig.getId();
		this.errorMessage = erorrMessageConfig.getMessage();
		this.errorDescription = erorrMessageConfig.getDescription();
		this.errorState = p3;
	}

	public IDMException(String p0, String p1, String p2, String p3) {
		this.errorID = p0;
		this.errorMessage = p1;
		this.errorDescription = p2;
		this.errorState = p3;
	}

	public String getErrorID() {
		return errorID;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorDescription() {
		return errorDescription;
	}

	public void setErrorState(String errorState) {
		this.errorState = errorState;
	}

	public String getErrorState() {
		return errorState;
	}

	public String toString() {
		return "IGFException";
		// return getErrorID() + ":" + getErrorState() + ":: " +
		// getErrorMessage() + "," + getErrorDescription();;
	}
}
