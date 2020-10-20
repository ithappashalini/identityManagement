package com.rheal.security.idm.exception;

import java.util.UUID;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String uniqueErrorId;
	private String reasonCode;
	private String reasonDescription;
	private String statusCode;
	private HttpStatus httpStatusCode;
	private String actionCode;

	public BaseException(String reasonCode, String reasonDescription, HttpStatus httpStatusCode) {
		this(reasonCode, reasonDescription, null, httpStatusCode, null);
	}

	public BaseException(String reasonCode, String reasonDescription, String statusCode, HttpStatus httpStatusCode) {
		this(reasonCode, reasonDescription, statusCode, httpStatusCode, null);
	}
	
	public BaseException(String reasonCode, String reasonDescription, String statusCode, String actionCode,
			HttpStatus httpStatusCode) {
		this(reasonCode, reasonDescription, statusCode, httpStatusCode,actionCode, null);
	}

	public BaseException(String reasonCode, String reasonDescription, String statusCode, HttpStatus httpStatusCode,
			Throwable ex) {
		super(ex);
		uniqueErrorId = UUID.randomUUID().toString();
		this.httpStatusCode = httpStatusCode;
		this.reasonCode = reasonCode;
		this.reasonDescription = reasonDescription;
		this.statusCode = statusCode;

	}

	public BaseException(String reasonCode, String reasonDescription, HttpStatus httpStatusCode, Throwable ex) {
		super(ex);
		uniqueErrorId = UUID.randomUUID().toString();
		this.httpStatusCode = httpStatusCode;
		this.reasonCode = reasonCode;
		this.reasonDescription = reasonDescription;
	}

	public BaseException(String reasonCode, String reasonDescription, String statusCode, HttpStatus httpStatusCode,
			String actionCode, Throwable ex) {
		super(ex);
		uniqueErrorId = UUID.randomUUID().toString();
		this.httpStatusCode = httpStatusCode;
		this.reasonCode = reasonCode;
		this.reasonDescription = reasonDescription;
		this.statusCode = statusCode;
		this.actionCode=actionCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public String getUniqueErrorId() {
		return uniqueErrorId;
	}

	public void setUniqueErrorId(String uniqueErrorId) {
		this.uniqueErrorId = uniqueErrorId;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(String reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public HttpStatus getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(HttpStatus httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	

}
