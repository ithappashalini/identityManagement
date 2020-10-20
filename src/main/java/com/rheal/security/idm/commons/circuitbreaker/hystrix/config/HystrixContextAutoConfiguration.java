package com.rheal.security.idm.commons.circuitbreaker.hystrix.config;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent.HystrixCallableWrapper;
import com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent.HystrixContextAwareConcurrencyStrategy;
import com.rheal.security.idm.commons.circuitbreaker.hystrix.concurrent.MdcAwareCallableWrapper;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Configuration
@ConditionalOnExpression("${spring.cloud.circuit.breaker.enabled:false} && ${hystrix.wrappers.enabled:true}")

public class HystrixContextAutoConfiguration {

	@Autowired(required = false)
	private List<HystrixCallableWrapper> wrappers = new ArrayList<>();

	@PostConstruct
	public void configureHystrixConcurencyStrategy() {
		if (!wrappers.isEmpty()) {
			HystrixPlugins.getInstance()
					.registerConcurrencyStrategy(new HystrixContextAwareConcurrencyStrategy(wrappers));
		}
	}

	@Bean
	public HystrixCallableWrapper mdcAwareCallableWrapper() {
		return new MdcAwareCallableWrapper();
	}
}