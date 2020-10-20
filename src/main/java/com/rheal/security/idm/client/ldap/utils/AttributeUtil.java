package com.rheal.security.idm.client.ldap.utils;

import java.lang.reflect.Field;
import java.util.Date;

import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.apache.commons.lang.StringUtils;

import com.rheal.security.idm.client.ldap.enums.AttributeName;
import com.rheal.security.idm.client.ldap.enums.AttributeType;
import com.rheal.security.idm.common.AppConstants;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class AttributeUtil {

	public static String getStringValue(String attributeName, Attributes attributes) {

		checkAttributes(attributes);

		Attribute attribute = attributes.get(attributeName);

		if (attribute == null) {
			return null;
		}

		try {
			return attribute.get().toString();
		} catch (NamingException ex) {
			throw new RuntimeException("Error reading " + attributeName, ex);
		}
	}

	public static Integer getIntegerValue(String attributeName, Attributes attributes) {

		checkAttributes(attributes);

		Attribute attribute = attributes.get(attributeName);

		if (attribute == null) {
			return null;
		}

		String attributeValue = null;

		try {
			attributeValue = attribute.get().toString();
			return Integer.parseInt(attributeValue);
		} catch (NamingException ex) {
			throw new RuntimeException("Error reading " + attributeName, ex);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(buildExceptionMessage(attributeName, AttributeType.INTEGER, attributeValue), ex);
		}
	}

	public static Long getLongValue(String attributeName, Attributes attributes) {

		checkAttributes(attributes);

		Attribute attribute = attributes.get(attributeName);

		if (attribute == null) {
			return null;
		}

		String attributeValue = null;

		try {
			attributeValue = attribute.get().toString();
			return Long.parseLong(attributeValue);
		} catch (NamingException ex) {
			throw new RuntimeException("Error reading " + attributeName, ex);
		} catch (NumberFormatException ex) {
			throw new RuntimeException(buildExceptionMessage(attributeName, AttributeType.LONG, attributeValue), ex);
		}
	}

	public static Boolean getBooleanValue(String attributeName, Attributes attributes) {

		checkAttributes(attributes);

		Attribute attribute = attributes.get(attributeName);

		if (attribute == null) {
			return null;
		}

		String attributeValue = null;

		try {
			attributeValue = attribute.get().toString();
			return Boolean.parseBoolean(attributeValue);
		} catch (NamingException ex) {
			throw new RuntimeException("Error reading " + attributeName, ex);
		}
	}

	public static Date getWindowsDateValue(String attributeName, Attributes attributes) throws NamingException {

		Long windowsTime = AttributeUtil.getLongValue(attributeName, attributes);

		if (windowsTime == null) {
			return null;
		} else {
			return DateUtil.winTimeToDate(windowsTime);
		}
	}

	public static Date getUnixDateValue(String attributeName, Attributes attributes) throws NamingException {

		Long unixTime = AttributeUtil.getLongValue(attributeName, attributes);

		if (unixTime == null) {
			return null;
		} else {
			return DateUtil.unixTimeToDate(unixTime);
		}
	}

	public static Attribute createAttribute(String name, AttributeType attributeType, Object value) {

		if (name == null) {
			throw new IllegalArgumentException("name cannot be null.");
		}

		if (value == null) {
			throw new IllegalArgumentException("value cannot be null.");
		}

		String attributeValue = null;

		switch (attributeType) {
		case INTEGER:
			attributeValue = ((Integer) value).toString();
			break;
		case LONG:
			attributeValue = ((Long) value).toString();
			break;
		case WIN_DATE:
			attributeValue = Long.toString(DateUtil.dateToWindowsTime((Date) value));
			break;
		case UNIX_DATE:
			attributeValue = Long.toString(DateUtil.dateToUnixTime((Date) value));
			break;
		default:
			attributeValue = value.toString();
		}

		return new BasicAttribute(name, attributeValue);
	}

	public static ModificationItem getModificationItem(String name, AttributeType attributeType, Object originalValue,
			Object finalValue) {

		if (originalValue == null && finalValue == null) {
			return null;
		}

		if (originalValue != null && originalValue.equals(finalValue)) {
			return null;
		}
		if (AttributeName.PASSWORD.getLdapName().equals(name)) {
			return new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					AttributeUtil.createAttribute(name, attributeType, finalValue));
		}
		if (finalValue != null && AppConstants.String_Empty.equalsIgnoreCase(finalValue.toString())) {
			return null;
		}

		if (originalValue == null) {
			return new ModificationItem(DirContext.ADD_ATTRIBUTE,
					AttributeUtil.createAttribute(name, attributeType, finalValue));
		} else if (finalValue == null && attributeType == AttributeType.WIN_DATE) {
			return new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute(name, "0"));
		} else if (finalValue == null) {
			return new ModificationItem(DirContext.REMOVE_ATTRIBUTE, new BasicAttribute(name));
		} else {
			return new ModificationItem(DirContext.REPLACE_ATTRIBUTE,
					AttributeUtil.createAttribute(name, attributeType, finalValue));
		}
	}

	private static void checkAttributes(Attributes attributes) {

		if (attributes == null) {
			throw new IllegalArgumentException("attributes cannot be null.");
		}
	}

	private static String buildExceptionMessage(String attributeName, AttributeType attributeType,
			String attributeValue) {

		if (attributeValue == null) {
			attributeValue = "(null)";
		} else if (attributeValue.isEmpty()) {
			attributeValue = "(empty)";
		}

		return String.format("[%s]:error reading %s as %s.", attributeName, attributeValue, attributeType.toString());
	}

	// Unable to access protected members
	public static void checkForNullAndAdd(Object obj) {
		/*
		 * 1. Get list of fields in object of the class 2. Iteration Over fields
		 * 3. get value of the field in object 4. null check the value 5. get
		 * attribute name from enum based on enum 6. add attribute name and
		 * value in attribute
		 */
		Attributes attrs = new BasicAttributes();
		for (Field f : obj.getClass().getDeclaredFields()) {
			try {
				if (f.get(obj) != null) {
					// Based on field name, need to get enum value(Attribute
					// name)

					// attrs.put();
				}
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static Attributes removeNullValueAttributes(Attributes attrs) {
		try {
			for (NamingEnumeration ae = attrs.getAll(); ae.hasMore();) {
				Attribute attr = (Attribute) ae.next();
				if (attr.get() == null) {
					attrs.remove(attr.getID());
				} else if (StringUtils.isBlank(attr.get().toString())) {
					attrs.remove(attr.getID());
				}
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return attrs;
	}

}
