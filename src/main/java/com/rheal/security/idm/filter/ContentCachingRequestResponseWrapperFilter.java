package com.rheal.security.idm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.rheal.security.idm.rest.config.WebMvcConfig;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class ContentCachingRequestResponseWrapperFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(WebMvcConfig.class);

	@Override
	public void init(FilterConfig config) throws ServletException {
		logger.info("Initializing ContentCachingRequestResponseWrapperFilter");
	}

	@Override
	public void destroy() {
		logger.info("Stopping ContentCachingRequestResponseWrapperFilter");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		try {
			if (!(response instanceof ContentCachingResponseWrapper) && response instanceof HttpServletResponse
					&& !(request instanceof ContentCachingRequestWrapper) && request instanceof HttpServletRequest) {
				ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
						(HttpServletResponse) response);

				ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(
						(HttpServletRequest) request);
				chain.doFilter(requestWrapper, responseWrapper);
				responseWrapper.copyBodyToResponse();

			} else {
				chain.doFilter(request, response);
			}
		} catch (Exception e) {
			chain.doFilter(request, response);
		}
	}
}
