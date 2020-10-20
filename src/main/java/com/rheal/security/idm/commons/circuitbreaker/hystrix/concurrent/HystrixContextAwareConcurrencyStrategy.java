package com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent;

import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.Callable;

import org.springframework.util.Assert;

import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;

/**
 * Defines a custom {@link HystrixConcurrencyStrategy} that wraps the passed
 * {@link Callable} using the predefined {@link HystrixCallableWrapper} list.
 *
 * @author Prashanth Errabelli
 * @see HystrixCallableWrapper
 */
public class HystrixContextAwareConcurrencyStrategy extends HystrixConcurrencyStrategy {

	private final Collection<HystrixCallableWrapper> wrappers;

	/**
	 * Creates new instance of {@link HystrixContextAwareConcurrencyStrategy} with
	 * the specific wrappers list.
	 *
	 * @param wrappers
	 *            the list of wrappers
	 */
	public HystrixContextAwareConcurrencyStrategy(Collection<HystrixCallableWrapper> wrappers) {
		Assert.notNull(wrappers, "Parameter 'wrappers' can not be null");
		this.wrappers = wrappers;
	}

	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {

		return new CallableWrapperChain<>(callable, wrappers.iterator()).wrapCallable();
	}

	private static class CallableWrapperChain<T> {

		private final Callable<T> callable;

		private final Iterator<HystrixCallableWrapper> wrappers;

		public CallableWrapperChain(Callable<T> callable, Iterator<HystrixCallableWrapper> wrappers) {
			this.callable = callable;
			this.wrappers = wrappers;
		}

		public Callable<T> wrapCallable() {
			Callable<T> result = callable;
			while (wrappers.hasNext()) {
				result = wrappers.next().wrapCallable(result);
			}
			return result;
		}
	}
}