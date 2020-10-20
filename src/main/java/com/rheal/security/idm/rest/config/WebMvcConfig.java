package com.rheal.security.idm.rest.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.rheal.security.idm.filter.ContentCachingRequestResponseWrapperFilter;
import com.rheal.security.idm.rest.interceptors.AuthenticatingAndLoggingRestInterceptor;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@Configuration
@ComponentScan("com.rheal.security.idm")
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Autowired
	AuthenticatingAndLoggingRestInterceptor interceptor;

	@Value("${spring.rest-context.path}")
	String contextPath;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		logger.info("Registering AuthenticatingAndLoggingRestInterceptor");
		registry.addInterceptor(interceptor).addPathPatterns(contextPath + "/*");
	}

	@Bean
	public FilterRegistrationBean contentCachingRequestResponseWrapperFilter() {
		logger.info("Registering ContentCachingRequestResponseWrapperFilter");
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		registrationBean.setName("contentCachingRequestResponseWrapperFilter");
		ContentCachingRequestResponseWrapperFilter contentCachingRequestResponseWrapperFilter = new ContentCachingRequestResponseWrapperFilter();
		registrationBean.setFilter(contentCachingRequestResponseWrapperFilter);
		registrationBean.addUrlPatterns(contextPath + "/*");
		return registrationBean;
	}
}