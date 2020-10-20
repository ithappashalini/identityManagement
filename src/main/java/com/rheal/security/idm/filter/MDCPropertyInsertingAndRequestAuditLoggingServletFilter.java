
package com.rheal.security.idm.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

/**
 * filer to insert various values retrieved from the incoming http request into
 * the MDC.
 * <p/>
 * <p/>
 * The values are removed after the request is processed.
 *
 * @author Prashanth Errabelli;
 */
@Component
public class MDCPropertyInsertingAndRequestAuditLoggingServletFilter implements Filter {

	public void destroy() {
		// do nothing
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		insertIntoMDC(request);
		try {
			
			chain.doFilter(request, response);
		} finally {
			clearMDC();
		}
	}

	void insertIntoMDC(ServletRequest request) {

		if (request instanceof HttpServletRequest) {
			String correlationid = UUID.randomUUID().toString();
			MDC.put("correlationid", correlationid);
			HttpServletRequest httpServletRequest = (HttpServletRequest) request;
			String osVersionWithManufacturer = httpServletRequest.getHeader("osVersionWithManufacturer");
			String derivedIpAddress = getIpAddress(null, httpServletRequest);
			String agent = (osVersionWithManufacturer != null) ? osVersionWithManufacturer : httpServletRequest.getHeader(HttpHeaders.USER_AGENT);
					
			MDC.put("derived_ip", derivedIpAddress);
			MDC.put("remote_ip", request.getRemoteAddr());
			MDC.put("agent", agent);
			MDC.put("referrer", httpServletRequest.getHeader(HttpHeaders.REFERER));
		}
	}

	void clearMDC() {
		MDC.remove("correlationid");
		MDC.remove("derived_ip");
		MDC.remove("remote_ip");
		MDC.remove("agent");
		MDC.remove("referrer");
		
		
	}

	public void init(FilterConfig arg0) throws ServletException {
		// do nothing
	}

	public String getIpAddress(String _ipAddress, HttpServletRequest request) {
		String ipAddress = (_ipAddress != null) ? _ipAddress.trim() : null;
		if (ipAddress == null) {
			ipAddress = request.getHeader("X-FORWARDED-FOR");
		}
		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}
		if (ipAddress.contains(":")) {
			ipAddress = ipAddress.substring(0, ipAddress.indexOf(":"));
		}
		// Below code is to handle request from postman or soap ui
		if (ipAddress != null && ipAddress.contains(",")) {
			String[] ipAddresses = ipAddress.split("\\s*,\\s*");
			if (ipAddresses != null) {
				ipAddress = ipAddresses[0];
			}
		}
		return ipAddress;
	}

}
