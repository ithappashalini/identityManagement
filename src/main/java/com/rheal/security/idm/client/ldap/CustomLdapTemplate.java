package com.rheal.security.idm.client.ldap;

import java.util.List;
import java.util.Map;

import javax.naming.Name;
import javax.naming.directory.Attributes;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.stereotype.Component;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Component
public class CustomLdapTemplate extends LdapTemplate {

	private static final Logger logger = LoggerFactory.getLogger(CustomLdapTemplate.class);
	
	@Autowired
	private Map<String, ErrorMessage> idmErrors;

		/**
		 * Constructor for bean usage.
		 */
		public CustomLdapTemplate() {
		}

		/**
		 * Constructor to setup instance directly.
		 * 
		 * @param contextSource the ContextSource to use.
		 */
		public CustomLdapTemplate(ContextSource contextSource) {
			super(contextSource);
		}
		@Override
		@Autowired 
		@Qualifier("pooledContextSource")
		public void setContextSource(ContextSource contextSource) {
			super.setContextSource(contextSource);
		}
		
		@HystrixCommand(commandKey = "Search", fallbackMethod = "SearchFallBack", threadPoolKey = "LDAPSearchServicePool")
		public <T>List<T> search(String base, String filter, AttributesMapper<T> mapper) {
			return super.search(base, filter, mapper);
		}
		@HystrixCommand(commandKey = "SearchWithControls", fallbackMethod = "SearchWithControlsFallBack", threadPoolKey = "LDAPSearchWithControlsServicePool")
		public <T> List<T> search(String base, String filter, SearchControls controls, AttributesMapper<T> mapper) {
			return super.search(base, filter, controls, mapper);
		}
		@HystrixCommand(commandKey = "ModifyAttributes", fallbackMethod = "ModifyAttributesFallBack", threadPoolKey = "LDAPModifyAttributesServicePool")
		public void modifyAttributes(final String dn, final ModificationItem[] mods) {
			super.modifyAttributes(dn, mods);
		}
		@HystrixCommand(commandKey = "Bind", fallbackMethod = "BindFallBack", threadPoolKey = "LDAPBindServicePool")
		public void bind(final Name dn, final Object obj, final Attributes attributes) {
			super.bind(dn, obj, attributes);
		}
		@HystrixCommand(commandKey = "Unbind", fallbackMethod = "UnbindFallBack", threadPoolKey = "LDAPUnbindServicePool")
		public void unbind(final String dn) {
			super.unbind(dn);
		}
		
		public <T>List<T> SearchFallBack(String base, String filter, AttributesMapper<T> mapper, Throwable t) throws IDMException {
			
			ErrorMessage error = idmErrors.get("E002");
			logger.debug("<ldapTemplateClient> : Error LDAPSearchFallBack <{}>:<{}>",  error.getId(), error.getDescription());

			throw new IDMException(error, "Back end service is either down or under maintenance");
		}
		
		public <T>List<T> SearchWithControlsFallBack(String base, String filter, SearchControls controls, AttributesMapper<T> mapper, Throwable t) throws IDMException {
			
			ErrorMessage error = idmErrors.get("E002");
			logger.debug("<ldapTemplateClient> : Error LDAPSearchWithControlsFallBack <{}>:<{}>",  error.getId(), error.getDescription());

			throw new IDMException(error, "Back end service is either down or under maintenance");
		}
		
		public void ModifyAttributesFallBack(final String dn, final ModificationItem[] mods, Throwable t) throws IDMException {
			
			ErrorMessage error = idmErrors.get("E024");
			logger.debug("<ldapTemplateClient> : Error LDAPModifyAttributesFallBack <{}>:<{}>",  error.getId(), error.getDescription());

			throw new IDMException(error, "Back end service is either down or under maintenance");
		}
		
		public void BindFallBack(final Name dn, final Object obj, final Attributes attributes, Throwable t) throws IDMException {
			
			ErrorMessage error = idmErrors.get("E025");
			logger.debug("<ldapTemplateClient> : Error LDAPBindFallBack <{}>:<{}>",  error.getId(), error.getDescription());

			throw new IDMException(error, "Back end service is either down or under maintenance");
		}
		
		public void UnbindFallBack(final String dn, Throwable t) throws IDMException {
			
			ErrorMessage error = idmErrors.get("E025");
			logger.debug("<ldapTemplateClient> : Error LDAPUnbindFallBack <{}>:<{}>",  error.getId(), error.getDescription());

			throw new IDMException(error, "Back end service is either down or under maintenance");
		}
	}

