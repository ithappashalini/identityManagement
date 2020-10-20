package com.rheal.security.idm.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class BusinessException extends BaseException {

	private static final long serialVersionUID = 1L;

	public BusinessException(String reasonCode, String reasonDescription, HttpStatus httpStatusCode) {
		super(reasonCode, reasonDescription, httpStatusCode);
	}

	public BusinessException(String reasonCode, String reasonDescription) {
		super(reasonCode, reasonDescription, HttpStatus.OK);
	}

	public BusinessException(String reasonCode, String reasonDescription, String statusCode,
			HttpStatus httpStatusCode) {
		super(reasonCode, reasonDescription, statusCode, httpStatusCode);
	}
	
	public BusinessException(String reasonCode, String reasonDescription, String statusCode,String actionCode,
			HttpStatus httpStatusCode) {
		super(reasonCode, reasonDescription, statusCode,actionCode, httpStatusCode);
	}

	public BusinessException(String reasonCode, String reasonDescription, String statusCode, Throwable ex) {
		super(reasonCode, reasonDescription, statusCode, HttpStatus.OK, ex);
	}

	public BusinessException(String reasonCode, String reasonDescription, Throwable ex) {
		super(reasonCode, reasonDescription, HttpStatus.OK, ex);
	}

}
