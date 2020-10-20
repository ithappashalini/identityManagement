package com.rheal.security.idm.commons.circuitbreaker.hystrix.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Configuration
@ConditionalOnProperty(value = "spring.cloud.circuit.breaker.enabled", matchIfMissing = false)

@EnableCircuitBreaker
public class CircuitBreakerHystrixConfig {

	@Bean
	public HystrixMetricsStreamServlet hystrixMetricsStreamServlet() {
		HystrixMetricsStreamServlet hystrixMetricsStreamServlet = new HystrixMetricsStreamServlet();
		return hystrixMetricsStreamServlet;
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean(HystrixMetricsStreamServlet hystrixMetricsStreamServlet) {

		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(hystrixMetricsStreamServlet,
				"/hystrix.stream");
		servletRegistrationBean.setName("hystrixServlet");
		servletRegistrationBean.setLoadOnStartup(1);

		return servletRegistrationBean;
	}

}
