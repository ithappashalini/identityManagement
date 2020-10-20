package com.rheal.security.idm.client.ldap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.ContextSource;
import org.springframework.ldap.core.LdapOperations;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Component;

import com.rheal.security.idm.client.ldap.enums.AclType;
import com.rheal.security.idm.client.ldap.enums.AttributeName;
import com.rheal.security.idm.client.ldap.model.ModItem;
import com.rheal.security.idm.client.ldap.utils.AttributeUtil;
import com.rheal.security.idm.client.ldap.utils.InputSanitizer;
import com.rheal.security.idm.common.AppConstants;
import com.rheal.security.idm.enums.ApplicationType;
import com.rheal.security.idm.property.config.AppAclSetting;
import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.property.config.LdapAppConfig;
import com.rheal.security.idm.property.config.LdapAppSettings;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
@Component
public class LdapOps {


	private static final Logger logger = LoggerFactory.getLogger(LdapOps.class);

	@Autowired
	private LdapOperations customLdapTemplate;

	@Autowired
	private Map<String, ErrorMessage> idmErrors;

	@Autowired
	private ContextSource nonPooledContextSource;

	@Autowired
	private Map<ApplicationType, LdapAppSettings> ldapAppSettingsMap;
	
	@Autowired
	private LdapAppConfig ldapAppConfig;
	
	@Autowired
	private Map<String, Map<ApplicationType, AppAclSetting>> acttAclSettings;

	@Value("${ldap.app_pid_account.search_base}")
	private String appAcctSearchBase;

	@Value("${ldap.app_pid_account.filter}")
	private String appAcctFilter;


	/**
	 * @param userName
	 * @return
	 */
	public boolean authenticateServiceAccount(String userName, String password) {
	
		DirContext context = null;
	
		try {
	
			logger.debug("searchBase >>>>>>>> {}", appAcctSearchBase);

			logger.debug("authenticateServiceAccount >>>>>>>> authenticating service account <{}>",
					userName);
	
			//String dn = "CN=" + InputSanitizer.escapeLDAPSearchFilter(userName) + "," + appAcctSearchBase;
			String dn = "CN=authadmin"+ "," + appAcctSearchBase;
			context = nonPooledContextSource.getContext(dn, "master01");
			logger.debug("authenticateServiceAccount >>>>>>>> credentials authenticated for service account <{}>",
					userName);
			return true;
		} catch (Exception e) {
			logger.error("Login failed", e);
			return false;
		} finally {
			LdapUtils.closeContext(context);
		}
	}

	public boolean authenticateUser(String password, String DN) {

		DirContext context = null;

		try {

			logger.debug("searchBase >>>>>>>> {}", DN);

			context = nonPooledContextSource.getContext(DN, password);
			return true;
		} catch (Exception e) {
			logger.debug("Login failed", e);
			return false;
		} finally {
			LdapUtils.closeContext(context);
		}
	}

	public boolean isUserNameDuplicate(String uid, String applicationName, String companyId) throws IDMException {

		boolean isDuplicate = false;

		try {
			ApplicationType applicationType = ApplicationType.getApplicationByName(applicationName);

			LdapAppSettings settings;
			String filter;
			String searchBase = ldapAppConfig.getDefaultSettings().getContainerBase();;
			
			
			switch (applicationType) {

			
			case rheal: {
				settings = ldapAppSettingsMap.get(applicationType);
				//searchBase = settings.getContainerBase();
				filter = String.format(settings.getFilter("duplicate_user"),
						InputSanitizer.escapeLDAPSearchFilter(uid));
				if (userNameExists(searchBase, filter)) {
					isDuplicate = true;
				}
				break;
			}

			default: {
                break;
			}
			}

			return isDuplicate;
		} catch (Exception e) {
			ErrorMessage error;
			error = idmErrors.get("E026");
			logger.error(": createUser >> create User : In Exception:{} ", e);
			
			throw new IDMException(error, e.getMessage());
		}
	}

	public boolean userNameExists(String searchBase, String filter) {
		
		logger.debug("searchBase >>>>>>>> {}", searchBase);

		List<String>  records = customLdapTemplate.search(searchBase, filter, new AttributesMapper<String>() {
			public String mapFromAttributes(Attributes attributes) throws NamingException {
				return AttributeUtil.getStringValue(AttributeName.GUID.toString(), attributes);
			}
		});

		if (records != null && records.size() >= 1) {
			return true;
		}
		return false;
	}

	public void createUser(String serviceAccountID, Attributes attrs, String applicationName, String randomGUID)
			throws IDMException {

		String searchBase;
		LdapAppSettings settings;
		ApplicationType applicationType = ApplicationType.getApplicationByName(applicationName);
		settings = ldapAppSettingsMap.get(applicationType);

		checkACL(serviceAccountID, applicationName, attrs, null,AclType.CREATE);
		
		searchBase = settings.getContainerBase();
		
		Name dn = LdapNameBuilder.newInstance().add(searchBase).add("CN", randomGUID)// doubt
				.build();
		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		ocattr.add("inetOrgPerson");
		//ocattr.add("oblixPersonPwdPolicy");
		//ocattr.add("user");
		ocattr.add("person");
		ocattr.add("organizationalPerson");
		attrs.put(ocattr);

		customLdapTemplate.bind(dn, null, attrs);
		
	}

	public Map<String, String> retrieveUser(String serviceAccountID, String applicationName, String filter, String[] returnAttributes) throws IDMException {
		
		checkACL(serviceAccountID, applicationName, null, null, AclType.READ);
		
		ApplicationType appName = ApplicationType.getApplicationByName(applicationName);
		LdapAppSettings settings = ldapAppSettingsMap.get(appName);
		String searchBase = settings.getContainerBase();
		
		return retrieveUserDetails(searchBase, filter, returnAttributes);
		
	}
	private Map<String, String> retrieveUserDetails(String searchBase, String filter, String[] returnAttributes) throws IDMException {
		
		SearchControls searchControl = new SearchControls();
		searchControl.setReturningAttributes(returnAttributes);
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);

		List<Map<String, String>> userDetails = customLdapTemplate.search(searchBase, filter, searchControl, new AttributesMapper<Map<String, String>>() {
			public Map<String, String> mapFromAttributes(Attributes attributes) throws NamingException {
				Map<String, String> user = new HashMap<>();
				for (NamingEnumeration<? extends Attribute> ae = attributes.getAll(); ae.hasMore();) {

					Attribute attr = (Attribute) ae.next();
					
					user.put(attr.getID(), attr.get().toString());
					
					//handle multiValue attributes if necessary

				}
				return user;
			}
		});
		if(userDetails == null || userDetails.isEmpty()) {
			ErrorMessage error = idmErrors.get("E001");
			logger.error("No profiles matching <base={}><filter={}> found ", searchBase, filter);
			throw new IDMException(error, "User Not Found");
		} else if (userDetails.size() > 1) {
			ErrorMessage error = idmErrors.get("E002");
			logger.error("Expected 1, Multiple <{}>profiles matching <base={}><filter={}> found ",userDetails.size(), searchBase, filter);
			throw new IDMException(error, "Error Searching User");
		}
		
		Map<String, String> user = userDetails.get(0);

		return user;
	}

	public void updateUserProfile(String serviceAccountId, ModificationItem items[], String dn, String applicationName)
			throws IDMException {

		
		checkACL(serviceAccountId, applicationName, null, items, AclType.MODIFY);
		
		customLdapTemplate.modifyAttributes(dn, items);
	}
	
	public void updateUserProfile(String serviceAccountId, List<ModItem> modItems, String dn, String applicationName)
			throws IDMException {

		List<ModificationItem> modificationItemList = new ArrayList<>();

		
		Object newValue;
		for(ModItem item : modItems) {
			newValue = item.getNewValue();

			modificationItemList.add(AttributeUtil.getModificationItem(item.getName().getLdapName(), item.getType(), item.getOldValue(), newValue));

		}
		
		modificationItemList.removeAll(Collections.singleton(null));

		ModificationItem items[] = modificationItemList.toArray(new ModificationItem[modificationItemList.size()]);

		updateUserProfile(serviceAccountId, items, dn, applicationName);
	}

	public List<Map<String, String>> searchUsers(String serviceAccountID, String filter, String applicationType, String[] returnAttributes)
			throws IDMException {
		
		return searchUsers(serviceAccountID, filter, applicationType, returnAttributes, -1);
	}
	
	public List<Map<String, String>> searchUsers(String serviceAccountID, String filter, String applicationType, String[] returnAttributes, int resultSize)
			throws IDMException {
		
		String searchBase = AppConstants.String_Empty;
		if(!"default".equalsIgnoreCase(applicationType)) {
		    
			checkACL(serviceAccountID,applicationType, null, null, AclType.READ);
			ApplicationType appName = ApplicationType.getApplicationByName(applicationType);
			LdapAppSettings settings = ldapAppSettingsMap.get(appName);
			searchBase = settings.getContainerBase();
		} else {
			searchBase = ldapAppConfig.getDefaultSettings().getContainerBase();
		}

		logger.debug("searchBase >>>>>>>> {}", searchBase);

		SearchControls searchControl = new SearchControls();
		searchControl.setReturningAttributes(returnAttributes);
		searchControl.setSearchScope(SearchControls.SUBTREE_SCOPE);
		
		if(resultSize > 0){
			searchControl.setCountLimit(resultSize + 1);
		}
		
		List<Map<String, String>> users = customLdapTemplate.search(searchBase, filter, searchControl, new AttributesMapper<Map<String, String>>() {
			
			public Map<String, String> mapFromAttributes(Attributes attributes) throws NamingException {
				
				Map<String, String> user = new HashMap<>();
				for (NamingEnumeration<? extends Attribute> ae = attributes.getAll(); ae.hasMore();) {
					Attribute attr = (Attribute) ae.next();
					user.put(attr.getID(), attr.get().toString());

				}
				return user;
			}
		});
		
		if(users.size() > resultSize && resultSize != -1) {
			//throw IGF
			ErrorMessage error = idmErrors.get("E002");
			logger.error("Found more that Expected <{}> profiles matching <base={}><filter={}> found ",resultSize, searchBase, filter);
			
			throw new IDMException(error.getId(),error.getMessage(),"Failed to get the next entry. Size Limit Exceeded", "Error Searching User");
		}
		
		return users;

	}

	public void createLWC(String serviceAccountID, String applicationName, Attributes attrs, String guid ) throws IDMException {
		
		checkACL(serviceAccountID, applicationName, attrs, null, AclType.CREATE);
		
		ApplicationType appName = ApplicationType.getApplicationByName(applicationName);
		LdapAppSettings settings = ldapAppSettingsMap.get(appName);
		String searchBase = settings.getContainerBase();
		
		Name dn = LdapNameBuilder.newInstance().add(searchBase).add("CN", guid).build();

		BasicAttribute ocattr = new BasicAttribute("objectclass");
		ocattr.add("top");
		// ocattr.add("inetOrgPerson");
		ocattr.add("oblixPersonPwdPolicy");
		ocattr.add("user");
		ocattr.add("person");
		ocattr.add("organizationalPerson");
		attrs.put(ocattr);
		customLdapTemplate.bind(dn, null, attrs);
	}

	public void deleteUser(String serviceAccountID, String applicationName, String dn) throws IDMException {

		if (checkACL(serviceAccountID, applicationName, null, null, AclType.DELETE)) {
			customLdapTemplate.unbind(dn);
		}
		
	}

	
    private boolean checkACL(String serviceAccountID, String applicationType, Attributes attrs, ModificationItem[] items, AclType aclType) throws IDMException {
		
		if(attrs == null) {
			//no attributes being updated;
			return true;
		}
		Map<ApplicationType, AppAclSetting> acctAclSetting = acttAclSettings.get("AUTHADMIN");
		
		if(acctAclSetting == null || acctAclSetting.isEmpty()) {
			//account does not have access to modify any OU attributes
			throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation");
		}
		
		ApplicationType appType = ApplicationType.getApplicationByName(applicationType);
		AppAclSetting appAclSetting = acctAclSetting.get(appType);
		if(appAclSetting == null) {
			//account does not have access to modify any attributes of the given app OU
			throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation for the given application");
		}
		
		List<String> allowedAttributes = null;
		List<String> errorFields = new ArrayList<String>();	
		
		switch (aclType) {
		case CREATE:
			if(appAclSetting == null || appAclSetting.getAllowCreateAttributes() == null || appAclSetting.getAllowCreateAttributes().isEmpty()) {
				//account does not have access to modify any attributes of the given app OU
				throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation for the given application");
			}
			if(appAclSetting.getAllowCreateAttributes().size() == 1 && "*".equals(appAclSetting.getAllowCreateAttributes().get(0))) {
				
				//has access to all attributes for this given OU
				return true;
			}
			NamingEnumeration<? extends Attribute> attributes = attrs.getAll();
			Attribute attribute;
			try {
				while(attributes.hasMoreElements()) {
					attribute = attributes.nextElement();
					if(attribute != null && StringUtils.isNotBlank(attribute.getID())) {
						if(!appAclSetting.getAllowCreateAttributes().contains(attribute.getID().toLowerCase())) {
							//this attribute being updated is not part allow attributes of the given OU for the given serviceAccount
							errorFields.add(attribute.getID().toLowerCase());
						}
					}
				} 
			} catch (NoSuchElementException nse) {
				//no more elements
			}
			break;
			
		case MODIFY:
			if(appAclSetting == null || appAclSetting.getAllowModifyAttributes() == null || appAclSetting.getAllowModifyAttributes().isEmpty()) {
				//account does not have access to modify any attributes of the given app OU
				throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation");
			}
			if(appAclSetting.getAllowModifyAttributes().size() == 1 && "*".equals(appAclSetting.getAllowModifyAttributes().get(0))) {
				
				//has access to all attributes for this given OU
				return true;
			}
			
			
			for(ModificationItem item : items){

				try{
					if(StringUtils.isNotBlank(item.getAttribute().getID()) && !appAclSetting.getAllowModifyAttributes().contains(item.getAttribute().getID().toLowerCase())) {
						//this attribute being updated is not part allow attributes of the given OU for the given serviceAccount
						errorFields.add(item.getAttribute().getID().toLowerCase());
					}
				} catch (Exception e) {
					logger.debug("", e);
				}
			}
			break;
			
		case READ:
			if (appAclSetting.isAllowRead()) {
				return true;
			} else {
				logger.error("<{}><{}> Insufficient Access rights to read <{}>", serviceAccountID, applicationType, errorFields);
				throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation");
			}
			
			
		case DELETE:
			if (appAclSetting.isAllowDelete()) {
				return true;
			} else {
				logger.error("<{}><{}> Insufficient Access rights to delete <{}>", serviceAccountID, applicationType, errorFields);
				throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation");
			}
			
		default:
		}
		
		if(!errorFields.isEmpty()) {
			//app log with info
			logger.error("<{}><{}> Insufficient Access rights to create <{}>", serviceAccountID, applicationType, errorFields);
			throw new IDMException( idmErrors.get("E033"), "Insufficient Access rights to perform the operation");
		}

		logger.debug("<{}> Authorized to create all attributes <{}> OU", serviceAccountID, applicationType);
		return true;
	}
}