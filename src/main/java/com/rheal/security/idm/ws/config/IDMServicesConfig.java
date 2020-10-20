package com.rheal.security.idm.ws.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rheal.security.idm.enums.ApplicationType;
import com.rheal.security.idm.property.config.AppAclSetting;
import com.rheal.security.idm.property.config.AppConfig;
import com.rheal.security.idm.property.config.AppServiceAccount;
import com.rheal.security.idm.property.config.AppSettings;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.property.config.LdapAppConfig;
import com.rheal.security.idm.property.config.LdapAppOperationSettings;
import com.rheal.security.idm.property.config.LdapAppSettings;
import com.rheal.security.idm.rest.handler.IdentityServiceHandler;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@Configuration
public class IDMServicesConfig {

	private static final Logger logger = LoggerFactory.getLogger(IDMServicesConfig.class);

	@Autowired
	private List<IdentityServiceHandler<?, ?, ?>> serviceHandlerList;
	
	
	@Bean(name = "serviceHandlerMap") 
	public Map<String, IdentityServiceHandler<?, ?, ?>> handlerMap() {
		
		Map<String, IdentityServiceHandler<?, ?, ?>> handlerMap = new HashMap<String, IdentityServiceHandler<?, ?, ?>>();
        if(serviceHandlerList != null){
        	
        	for(IdentityServiceHandler<?, ?, ?> handler : serviceHandlerList) {
        		
        		handlerMap.put(handler.getOperation(), handler);
        	}
        	
        }
		return handlerMap;
	}
}