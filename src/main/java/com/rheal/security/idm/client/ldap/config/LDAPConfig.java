package com.rheal.security.idm.client.ldap.config;

import java.util.Hashtable;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Configuration
public class LDAPConfig {

    @Value("${ldap.service.host}")
    private String ldapServiceHost;
    
    @Value("${ldap.service.port}")
    private int ldapServicePort;
    
    @Value("${ldap.service.dn}")
    private String ldapServiceDN;
    
    @Value("${ldap.service.password}")
    private String ldapServicePwd;
   
    @Value("${ldap.read_timeout}")
    private String ldapReadTimeout;

    @Value("${ldap.connect_timeout}")
    private String ldapConnectTimeout;
    
    
    @Bean(name = "pooledContextSource")
	public LdapContextSource pooledContextSource() {
    	
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapServiceHost + ":" + ldapServicePort);
		contextSource.setUserDn(ldapServiceDN);
		contextSource.setPassword(ldapServicePwd);
		
		Map<String, Object> baseEnv = new Hashtable<>();
		baseEnv.put("com.sun.jndi.ldap.connect.timeout", ldapConnectTimeout);
		baseEnv.put("com.sun.jndi.ldap.read.timeout", ldapReadTimeout);
		contextSource.setBaseEnvironmentProperties(baseEnv);
		
		contextSource.afterPropertiesSet();
		return contextSource;
	}
    
    @Bean(name = "nonPooledContextSource")
	public LdapContextSource nonPooledContextSource() {
    	
		LdapContextSource contextSource = new LdapContextSource();
		contextSource.setUrl(ldapServiceHost + ":" + ldapServicePort);
		contextSource.setUserDn(ldapServiceDN);
		contextSource.setPassword(ldapServicePwd);
		Map<String, Object> baseEnv = new Hashtable<>();
		baseEnv.put("com.sun.jndi.ldap.connect.timeout", ldapConnectTimeout);
		baseEnv.put("com.sun.jndi.ldap.read.timeout", ldapReadTimeout);
		contextSource.setBaseEnvironmentProperties(baseEnv);
		
		contextSource.afterPropertiesSet();
		return contextSource;
	}
}
