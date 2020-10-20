package com.rheal.security.idm.exception;

import java.time.Instant;
import java.util.UUID;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class ErrorDetail {

	private int status;
	private long timestamp;
	@JsonProperty("errorID")
	private String id;
	@JsonProperty("errorMessage")
	private String reasonCode;
	@JsonProperty("errorDescription")
	private String reasonDescription;
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("errorState")
	private String statusCode;
	@JsonInclude(Include.NON_NULL)
	private ErrorSource source;
	@JsonIgnore
	private String actionCode;

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String applicationCode, String statusCode,
			long timestamp) {
		super();
		this.timestamp = timestamp;
		this.id = uniqueErrorId;
		this.reasonCode = applicationCode;
		this.status = httpStatusCode.value();
		this.statusCode = statusCode;
	}

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription, long timestamp) {
		super();
		this.status = httpStatusCode.value();
		this.timestamp = timestamp;
		this.id = uniqueErrorId;
		this.reasonCode = reasonCode;
		this.statusCode = statusCode;
		this.reasonDescription = reasonDescription;
	}

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode) {
		this(uniqueErrorId, httpStatusCode, reasonCode, statusCode, Instant.now().toEpochMilli());
	}

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription) {
		this(uniqueErrorId, httpStatusCode, reasonCode, statusCode, reasonDescription, Instant.now().toEpochMilli());
	}

	public ErrorDetail(HttpStatus httpStatusCode, String reasonCode, String statusCode) {
		this(UUID.randomUUID().toString(), httpStatusCode, reasonCode, statusCode);
	}

	public ErrorDetail(HttpStatus httpStatusCode, String reasonCode, String statusCode, String errorSourcePointer) {
		this(UUID.randomUUID().toString(), httpStatusCode, reasonCode, statusCode);
		this.source = new ErrorSource(errorSourcePointer);

	}

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription, String actionCode) {
		this(uniqueErrorId, httpStatusCode, reasonCode, statusCode, reasonDescription, actionCode,
				Instant.now().toEpochMilli());
	}

	public ErrorDetail(String uniqueErrorId, HttpStatus httpStatusCode, String reasonCode, String statusCode,
			String reasonDescription, String actionCode, long timestamp) {
		this.status = httpStatusCode.value();
		this.timestamp = timestamp;
		this.id = uniqueErrorId;
		this.reasonCode = reasonCode;
		this.statusCode = statusCode;
		this.reasonDescription = reasonDescription;
		this.actionCode = actionCode;
	}

	public String getActionCode() {
		return actionCode;
	}

	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public ErrorSource getSource() {
		return source;
	}

	public void setSource(ErrorSource source) {
		this.source = source;
	}

}
