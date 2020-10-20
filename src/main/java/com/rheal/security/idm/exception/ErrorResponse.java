package com.rheal.security.idm.exception;

import java.io.StringWriter;
import java.util.UUID;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class ErrorResponse {
	private static Logger logger = LoggerFactory.getLogger(ErrorResponse.class);

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			long timestamp) {
		super();
		ErrorDetail errorDetail = new ErrorDetail(uniqueErrorId, httpStatusCode, reasonCode, statusCode,
				timestamp);
		this.setErrors(errorDetail);
	}

	public ErrorResponse(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription) {
		super();
		ErrorDetail errorDetail = new ErrorDetail(uniqueErrorId, httpStatusCode, reasonCode, statusCode,
				reasonDescription);
		this.setErrors(errorDetail);
	}
	
	public ErrorResponse(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription,String actionCode) {
		super();
		ErrorDetail errorDetail = new ErrorDetail(uniqueErrorId, httpStatusCode, reasonCode, statusCode,
				reasonDescription,actionCode);
		this.setErrors(errorDetail);
	}

	public ErrorResponse(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode) {
		super();
		ErrorDetail errorDetail = new ErrorDetail(uniqueErrorId, httpStatusCode, reasonCode, statusCode);
		this.setErrors(errorDetail);
	}

	public ErrorResponse(HttpStatus httpStatusCode, String reasonCode, String statusCode, long timestamp) {
		this(UUID.randomUUID().toString(), httpStatusCode, reasonCode, statusCode, timestamp);
	}

	public ErrorResponse(HttpStatus httpStatusCode, String reasonCode, String statusCode) {
		this(UUID.randomUUID().toString(), httpStatusCode, reasonCode, statusCode);
	}

	public ErrorResponse(HttpStatus httpStatusCode, String reasonCode, String statusCode, String reasonDescription) {
		this(UUID.randomUUID().toString(), httpStatusCode, reasonCode, statusCode, reasonDescription);
	}

	private ErrorDetail errors;

	public ErrorDetail getErrors() {
		return errors;
	}

	public void setErrors(ErrorDetail errors) {
		this.errors = errors;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	public void logErrorResponse() {
		StringWriter writer = new StringWriter();
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			objectMapper.writeValue(writer, this);
			String responseJsonSerializedString = writer.toString();
			logger.error("errors: {}", responseJsonSerializedString);
		} catch (Exception e) {
			logger.error("Exception occurred while serializing response", e);
			logger.error("falling back to toString errors: {}", this);
		}
	}

}
