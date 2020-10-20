package com.rheal.security.idm.property.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
@Configuration
@ConfigurationProperties(ignoreInvalidFields=true, prefix = "app.config")
public class AppConfig {
	
    private List<AppSettings> settings;
    
    private List<ErrorMessage> errorMessages;
    
    private AppSettings defaultSettings;
    
    private List<AppServiceAccount> serviceAccounts;

    public List<ErrorMessage> getErrorMessages() {
		return errorMessages;
	}

	public void setErrorMessages(List<ErrorMessage> errorMessages) {
		this.errorMessages = errorMessages;
	}

	public AppSettings getDefaultSettings() {
		return defaultSettings;
	}

	public void setDefaultSettings(AppSettings defaultSettings) {
		this.defaultSettings = defaultSettings;
	}

	public List<AppSettings> getSettings() {
		return settings;
	}

	public void setSettings(List<AppSettings> settings) {
		this.settings = settings;
	}

	public List<AppServiceAccount> getServiceAccounts() {
		return serviceAccounts;
	}

	public void setServiceAccounts(List<AppServiceAccount> serviceAccounts) {
		this.serviceAccounts = serviceAccounts;
	}
}