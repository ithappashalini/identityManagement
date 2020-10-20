package com.rheal.security.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.rheal.security.idm.exception.ErrorDetail;
import com.rheal.security.idm.exception.ErrorResponse;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(IDMException.class)
	public ResponseEntity<ErrorResponse> igfException(final IDMException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		ErrorDetail errorDetail = new ErrorDetail(e.getErrorID(), HttpStatus.INTERNAL_SERVER_ERROR, e.getErrorMessage(),
				e.getErrorState(), e.getErrorDescription());
		errorResponse.setErrors(errorDetail);
		return new ResponseEntity<ErrorResponse>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
