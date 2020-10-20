package com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent;

import java.util.concurrent.Callable;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public interface HystrixCallableWrapper {

	/**
	 * Wraps the passed callable instance.
	 *
	 * @param callable
	 *            the callable to wrap
	 * @param <T>
	 *            the result type
	 * @return the wrapped callable
	 */
	<T> Callable<T> wrapCallable(Callable<T> callable);
}