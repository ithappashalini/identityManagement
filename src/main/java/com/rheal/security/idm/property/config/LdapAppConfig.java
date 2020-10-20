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
@ConfigurationProperties(prefix = "ldap.app_config")
public class LdapAppConfig {
    private List<LdapAppSettings> settings;
    
    private LdapAppDefaultSettings defaultSettings;

    public List<LdapAppSettings> getSettings() {
        return settings;
    }

    public void setSettings(List<LdapAppSettings> settings) {
        this.settings = settings;
    }

	public LdapAppDefaultSettings getDefaultSettings() {
		return defaultSettings;
	}

	public void setDefaultSettings(LdapAppDefaultSettings defaultSettings) {
		this.defaultSettings = defaultSettings;
	}
}