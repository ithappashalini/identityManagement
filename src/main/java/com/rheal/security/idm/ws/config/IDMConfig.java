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
public class IDMConfig {

	private static final Logger logger = LoggerFactory.getLogger(IDMConfig.class);

	@Bean
	public Map<ApplicationType, LdapAppSettings> ldapAppSettingsMap(@Autowired LdapAppConfig appConfig) {

		if (appConfig != null && appConfig.getSettings() != null
			&& !appConfig.getSettings().isEmpty()) {

			Map<ApplicationType, LdapAppSettings> settingsMap = new HashMap<ApplicationType, LdapAppSettings>();
			ApplicationType applicationType;
			for (LdapAppSettings setting : appConfig.getSettings()) {

				applicationType = ApplicationType.getApplicationByName(setting.getAppId());
				settingsMap.put(applicationType, setting);
			}
			return settingsMap;
		} else {
			// throw Exception("invalid configuration");
			return null;
		}
	}

	@Bean
	public Map<String, ErrorMessage> idmErrors(@Autowired AppConfig appConfig) throws Exception {

		if (appConfig != null && appConfig.getErrorMessages() != null) {

			Map<String, ErrorMessage> messagesMap = new HashMap<String, ErrorMessage>();
			for (ErrorMessage errorMessage : appConfig.getErrorMessages()) {
				messagesMap.put(errorMessage.getId(), errorMessage);
			}
			return messagesMap;
		} else {
			throw new Exception("invalid configuration");
			//return null;
		}
	}
	
	@Bean
	public Map<String, AppServiceAccount> appServiceAccountMap(@Autowired AppConfig appConfig) {

		if (appConfig != null && appConfig.getServiceAccounts() != null) {

			Map<String, AppServiceAccount> serviceAcctMap = new HashMap<String, AppServiceAccount>();
			for (AppServiceAccount appServiceAccount : appConfig.getServiceAccounts()) {
				serviceAcctMap.put(appServiceAccount.getUserId(), appServiceAccount);
			}
			return serviceAcctMap;
		} else {
			// throw Exception("invalid configuration");
			return null;
		}
	}

	@Bean
	public Map<ApplicationType, AppSettings> appSettingsMap(@Autowired AppConfig appConfig) {

		if (appConfig != null && appConfig.getSettings() != null) {

			Map<ApplicationType, AppSettings> settingsMap = new HashMap<ApplicationType, AppSettings>();
			ApplicationType applicationType;
			for (AppSettings setting : appConfig.getSettings()) {

				applicationType = ApplicationType.getApplicationByName(setting.getAppId());
				settingsMap.put(applicationType, setting);
			}
			return settingsMap;
		} else {
			// throw Exception("invalid configuration");
			return null;
		}
	}

	
	
	@Bean
	public Map<String, LdapAppOperationSettings> operationLdapSettingsMap(@Autowired LdapAppConfig appConfig) {

		if (appConfig != null && appConfig.getDefaultSettings() != null && appConfig.getDefaultSettings().getOperationSettings() != null) {

			Map<String, LdapAppOperationSettings> settingsMap = new HashMap<String, LdapAppOperationSettings>();
			for (LdapAppOperationSettings setting : appConfig.getDefaultSettings().getOperationSettings()) {

				settingsMap.put(setting.getName(), setting);
			}
			return settingsMap;
		} else {
			// throw Exception("invalid configuration");
			return null;
		}
	}
	
	@Bean
	public Map<String, Map<ApplicationType, AppAclSetting>> acttAclSettings(@Autowired AppConfig appConfig) {

		if (appConfig != null && appConfig.getServiceAccounts() != null) {

			Map<String, Map<ApplicationType, AppAclSetting>> serviceAcctAclMap = new HashMap<String, Map<ApplicationType, AppAclSetting>>();
			for (AppServiceAccount appServiceAccount : appConfig.getServiceAccounts()) {
				if(appServiceAccount.getAccessControlList() != null && !appServiceAccount.getAccessControlList().isEmpty()) {
					Map<ApplicationType, AppAclSetting> aclMap = new HashMap<ApplicationType, AppAclSetting>();
					
					for(AppAclSetting setting : appServiceAccount.getAccessControlList()) {
						if(ApplicationType.getApplicationByName(setting.getAppId()) != null) {
							
							aclMap.put(ApplicationType.getApplicationByName(setting.getAppId()), setting);
						}
					}
					if(!aclMap.isEmpty()) {
						serviceAcctAclMap.put(appServiceAccount.getUserId(), aclMap);
					}
				}
			}
			return serviceAcctAclMap;
		} else {
			// throw Exception("invalid configuration");
			return null;
		}
	}
}