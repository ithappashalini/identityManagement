package com.rheal.security.idm.client.ldap.utils;

import java.time.LocalDateTime;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class CommonUtil {

	private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static String checkNull(String string) {

		if (StringUtils.isBlank(string)) {

			return "";
		}
		return string;
	}

	public static boolean checkNullForAttribute(String string) {
		if (StringUtils.isBlank(string)) {
			return false;
		}
		return true;
	}

	public static String[] splitMultiValuedAttribute(String attributeValue, String attributeKey) {
		String strSplit[] = null;

		if(StringUtils.isNotBlank(attributeValue)){
				strSplit = attributeValue.split(", ");
				strSplit[0] = strSplit[0].substring(attributeKey.length() + 2, strSplit[0].length());
			
		}

		return strSplit;
	}
	
	public static String getFirstValueOfAttribute(String attributeValue, String attributeKey) {
		String strSplit[] = splitMultiValuedAttribute(attributeValue, attributeKey);

		if(strSplit != null && strSplit.length >= 1){
				return strSplit[0];
			
		}

		return "";
	}

	public static String[] splitMemberFields(Map<String, String> user1, String attributeKey) {
		String strSplit[] = null;

		for (Object key : user1.keySet()) {
			if (key.toString().equalsIgnoreCase(attributeKey)) {
				strSplit = user1.get(key).toString().split(", CN");

			}
		}
		strSplit[0] = strSplit[0].substring(attributeKey.length() + 2, strSplit[0].length());
		int i = 1;
		while (i < strSplit.length) {
			strSplit[i] = "CN" + strSplit[i];
			i++;
		}
		return strSplit;
	}

	public static String getFutureDateTime(int passwordValidity) {

		String formatted1 = null;

		if (passwordValidity > 0) {
			formatted1 = LocalDateTime.now().plusDays(passwordValidity).toString();
		} else {
			formatted1 = LocalDateTime.now().plusDays(3).toString();
		}

		String futureDateTime = formatted1.replaceAll(" ", "T");
		return futureDateTime;

	}

	public static long startTimeLog(String oprName) {
		
		long  startTime = System.currentTimeMillis();

		logger.debug("{} : Start Time : {}", oprName, startTime);

		return startTime;
	}

	public static void endTimeLog(String oprName, String serviceAccountID, long startTime, String openField)
			throws IDMException {

		long endTime = System.currentTimeMillis();
		long totalTimeTaken = endTime - startTime;

		logger.debug("{} : End Time : {}, Total Time Taken : {}", oprName, endTime, totalTimeTaken);
	}

}
