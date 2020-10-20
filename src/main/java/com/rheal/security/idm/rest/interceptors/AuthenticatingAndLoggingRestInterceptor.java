package com.rheal.security.idm.rest.interceptors;

import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import com.google.common.io.ByteSource;
import com.rheal.security.idm.client.ldap.LdapOps;
import com.rheal.security.idm.client.ldap.utils.DateUtil;
import com.rheal.security.idm.common.CommonValidations;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.rest.model.AuthHeader;
import com.rheal.security.idm.util.IDMUtils;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@Component
public class AuthenticatingAndLoggingRestInterceptor implements HandlerInterceptor {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticatingAndLoggingRestInterceptor.class);

	private static final Logger auditlog = LoggerFactory.getLogger("com.rheal.auditlog");
	private static final Logger idmapilog = LoggerFactory.getLogger("com.rheal.idm.api.trace");

	@Autowired
	CommonValidations commonValidations;

	@Autowired
	private Map<String, ErrorMessage> idmErrors;

	@Autowired
	private LdapOps ldapOps;

	@Value("${log.api.messages.enabled}")
	private boolean logApiMessagesEnabled;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		boolean authenticated = false;
		MDC.put("startTime", DateUtil.getCurrentTimestamp().toString());
		try {

			AuthHeader serviceAccountCredentials = IDMUtils.extractAuthHeader(request);

			MDC.put("accountId", serviceAccountCredentials.getServiceAccount());
			authenticateServiceAcctCredentials(serviceAccountCredentials.getServiceAccount(),
					serviceAccountCredentials.getPassword());
			authenticated = true;

		} catch (IDMException ige) {
			MDC.put("errorId", ige.getErrorID());
			MDC.put("errorMsg", ige.getErrorMessage());
			MDC.put("result", "Failure");
			throw ige;
		}

		if (!authenticated) {
			logger.error(" invalid request :: not autheticated");
			MDC.put("errorId", "1002");
			MDC.put("errorMsg", "ServiceAccount ID/Password Validation failed");
			MDC.put("result", "Failure");
			throw new IDMException("1002", "ServiceAccount failed to be authenticated",
					"ServiceAccount ID/Password Validation failed", "ServiceAccount ID/Password Validation failed");
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		MDC.put("endTime", DateUtil.getCurrentTimestamp().toString());
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
/*ContentCachingRequestWrapper requestWrapper = (ContentCachingRequestWrapper) (request);
		String requestBody = ByteSource.wrap(requestWrapper.getContentAsByteArray())
				.asCharSource(StandardCharsets.UTF_8).read();
		idmapilog.info("{} --- {}", "REQUEST", requestBody);

		ContentCachingResponseWrapper responseWrapper = (ContentCachingResponseWrapper) (response);
		String responseBody = ByteSource.wrap(responseWrapper.getContentAsByteArray())
				.asCharSource(StandardCharsets.UTF_8).read();
		idmapilog.info("{} --- {}", "RESPONSE", responseBody);*/

		auditlog.info("audit");
		clearMDC();
	}

	private void clearMDC() {
		MDC.remove("accountId");
		MDC.remove("errorId");
		MDC.remove("errorMsg");
		MDC.remove("result");
		MDC.remove("operation");
		MDC.remove("openField");
		MDC.remove("guid");
		MDC.remove("startTime");
		MDC.remove("endTime");
	}

	private void authenticateServiceAcctCredentials(String serviceAccountId, String serviceAccountPassword)
			throws IDMException {
		try {
			commonValidations.validateServiceAccountCredentials(serviceAccountId, serviceAccountPassword);

			if (!ldapOps.authenticateServiceAccount(serviceAccountId, serviceAccountPassword)) {

				throw new Exception("Invalid Credentials");
			}

		} catch (IDMException ie) {

			throw ie;
		} catch (Exception e) {
			ErrorMessage error;
			error = idmErrors.get("E102");
			logger.error(
					" : authenticateServiceAcctCredentials :: In authenticateServiceAcctCredentials User Exception", e);
			throw new IDMException(error, e.getMessage());

		}
	}

}