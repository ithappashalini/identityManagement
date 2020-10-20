package com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent;

import java.util.concurrent.Callable;

import org.slf4j.MDC;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class MdcAwareCallableWrapper implements HystrixCallableWrapper {

	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return new MdcAwareCallable<>(callable, MDC.getCopyOfContextMap());
	}

}