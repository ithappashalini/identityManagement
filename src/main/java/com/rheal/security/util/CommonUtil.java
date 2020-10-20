package com.rheal.security.util;

import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.exception.BusinessException;


/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class CommonUtil {
	public static final String generateRandomID() {
		Random randomGenerator = new Random();
		// This will return Random generated positive number only
		return Integer.toString(randomGenerator.nextInt() & Integer.MAX_VALUE);
	}

	
	public static final String generateClientSessionId(){
		String clientSessionId=AppConstants.OCM_SESSION_ID;
		clientSessionId=clientSessionId+generateRandomID();
		return clientSessionId;
		
	}
	
	
	
	public static final Boolean isBlank(String field){
		Boolean isSuccess=false;
		if(StringUtils.isNotBlank(field)) {
			isSuccess=true;
		}else {
			throw new BusinessException("{} is missing", "{} is missing", HttpStatus.BAD_REQUEST);
		}
		return isSuccess;
	}
	
	
	
	public static final String getDeviceType(String deviceTypeInput){
		String deviceType=AppConstants.WEB_BROWSER;
		if (AppConstants.CHANNEL_INDICATOR_MOBILE.equalsIgnoreCase(deviceTypeInput)) {
			deviceType = AppConstants.MOBILE_DEVICE;
		}
		
		return deviceType;
	}
	
	
    public static void validateParams(Map<String, String> VariableMap) {
         
		for (Map.Entry<String, String> entry : VariableMap.entrySet()) {
			if (StringUtils.isBlank(entry.getValue())) {
				throw new BusinessException(AppConstants.MANDATORY_PARAMETER_MISSING + entry.getKey(),
						AppConstants.MANDATORY_PARAMETER_MISSING + entry.getKey(), HttpStatus.BAD_REQUEST);
			}
		}
}
   
    
    //This method replaces hyphen with space in the userName and returns the replacedUserName
    public static String getValidatedUserName(String username){
		String replacedUserName = null;
		if (username != null) {
			replacedUserName = username.replace("-", "");
		}
		return replacedUserName;
    }
    
    //This method checks whether the request is mobile or web
    public static String getChannelIndicator(HttpServletRequest request) {
    	String channelIndicator = null;
    	String channelHeaderVal = request.getHeader(AppConstants.HTTP_HEADER_CHANNEL);
    	if (AppConstants.NB.equalsIgnoreCase(channelHeaderVal)) {
    		channelIndicator = AppConstants.CHANNEL_INDICATOR_WEB;	
    	} else if (AppConstants.NM.equalsIgnoreCase(channelHeaderVal)) {
    		channelIndicator = AppConstants.CHANNEL_INDICATOR_MOBILE;	
    	}
    	return channelIndicator;
    	
    }
  
}
