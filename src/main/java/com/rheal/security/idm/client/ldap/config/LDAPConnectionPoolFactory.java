package com.rheal.security.idm.client.ldap.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.ldap.pool.factory.PoolingContextSource;
import org.springframework.ldap.pool.validation.DefaultDirContextValidator;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Configuration
public class LDAPConnectionPoolFactory {

	@Value("${ldap.connection.factory.testOnBorrow}")
	boolean testOnBorrow;

	@Value("${ldap.connection.factory.testOnReturn}")
	boolean testOnReturn;

	@Value("${ldap.connection.factory.testWhileIdle}")
	boolean testWhileIdle;

	@Value("${ldap.connection.factory.maxActive}")
	private int maxActive;

	@Value("${ldap.connection.factory.maxIdle}")
	private int maxIdle;

	@Value("${ldap.connection.factory.maxTotal}")
	private int maxTotal;

	@Value("${ldap.connection.factory.maxWait}")
	private long maxWait;

	@Value("${ldap.connection.factory.minIdle}")
	private int minIdle;

	@Value("${ldap.connection.factory.numTestsPerEvictionRun}")
	private int numTestsPerEvictionRun;

	@Value("${ldap.connection.factory.minEvictableIdleTimeMillis}")
	private long minEvictableIdleTimeMillis;

	@Value("${ldap.connection.factory.timeBetweenEvictionRunsMillis}")
	private long timeBetweenEvictionRunsMillis;

	@Bean
	public PoolingContextSource poolingContextSource(@Autowired @Qualifier("pooledContextSource") LdapContextSource ldapCtxSource) {
		PoolingContextSource poolingContextSource = new PoolingContextSource();
		poolingContextSource.setContextSource(ldapCtxSource);
		poolingContextSource.setTestOnBorrow(testOnBorrow);
		poolingContextSource.setTestOnReturn(testOnReturn);
		poolingContextSource.setTestWhileIdle(testWhileIdle);
		poolingContextSource.setMaxActive(maxActive);
		poolingContextSource.setMaxIdle(maxIdle);
		poolingContextSource.setMaxTotal(maxTotal);
		poolingContextSource.setMaxWait(maxWait);
		poolingContextSource.setMinIdle(minIdle);
		poolingContextSource.setNumTestsPerEvictionRun(numTestsPerEvictionRun);
		poolingContextSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		poolingContextSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
		poolingContextSource.setDirContextValidator(new DefaultDirContextValidator());
		return poolingContextSource;
	}
}
