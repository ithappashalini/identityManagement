package com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent;

import java.util.Map;
import java.util.concurrent.Callable;

import org.slf4j.MDC;

/**
 * 
 * @author Prashanth Errabelli
 *
 * @param <T>
 */
public class MdcAwareCallable<T> implements Callable<T> {

    private final Callable<T> callable;

    private final Map<String, String> contextMap;

    public MdcAwareCallable(Callable<T> callable, Map<String, String> contextMap) {
        this.callable = callable;
        this.contextMap = contextMap;
    }

    public T call() throws Exception {
        try {
            if (contextMap != null && !contextMap.isEmpty()) {
                MDC.setContextMap(contextMap);
            }
            return callable.call();
        } finally {
            MDC.clear();
        }
    }
}