package com.rheal.security.idm.util;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.rest.model.AuthHeader;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class IDMUtils {

	public static boolean isAlphaNumeric(String s) {
		boolean result = true;
		if (!s.matches(".*[a-zA-Z].*")) {
			result = false;
		}

		if (!s.matches(".*[0-9].*")) {
			result = false;
		}
		return result;
	}

	public static int countChar(String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isLetter(str.charAt(i)))
				counter++;
		}
		return counter;
	}

	public static int countNum(String str) {
		int counter = 0;
		for (int i = 0; i < str.length(); i++) {
			if (Character.isDigit(str.charAt(i)))
				counter++;
		}
		return counter;
	}


	public static String generateUniqueGUID() throws IDMException {
		String uniqueKey = null;
		UUID uniKey = UUID.randomUUID();
		uniqueKey = uniKey.toString();
		return uniqueKey;
	}

	public static AuthHeader extractAuthHeader(HttpServletRequest request)
			throws IDMException, UnsupportedEncodingException {

		String authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			throw new IDMException("1002", "ServiceAccount failed to be authenticated",
					"ServiceAccount ID/Password Validation failed", "ServiceAccount ID/Password Validation failed");
		}

		authHeader = authHeader.replaceAll(AppConstants.AUTHORIZATION_SCHEMA + " ", "");

		byte[] decodedAuthHeader = Base64.getDecoder().decode(authHeader);

		String header = new String(decodedAuthHeader, StandardCharsets.UTF_8.toString());

		String serviceAccountID = header.split(":")[0];
		String serviceAccountPassword = header.split(":")[1];

		AuthHeader serviceAccountCredentials = new AuthHeader();
		serviceAccountCredentials.setServiceAccount(serviceAccountID);
		serviceAccountCredentials.setPassword(serviceAccountPassword);
		return serviceAccountCredentials;
	}
}
