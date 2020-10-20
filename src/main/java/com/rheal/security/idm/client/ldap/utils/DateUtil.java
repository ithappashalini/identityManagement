package com.rheal.security.idm.client.ldap.utils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.rheal.security.idm.property.config.ErrorMessage;
import com.rheal.security.idm.ws.exception.IDMException;

/**
 * 
 * @author Prashanth Errabelli
 * @date Dec 26, 2019 10:01:43 PM 
 *
 */
public class DateUtil {
	@Autowired
	private static Map<String, ErrorMessage> idmErrors;
    
    public static long winTimeToJavaTime(long winTime) {
        return (winTime - 116444736000000000L) / 10000L;
    }
    
    public static long unixTimeToJavaTime(long unixTime) {
        return unixTime * 1000L;
    }
    
    public static long javaTimeToWinTime(long javaTime) {
        return (javaTime * 10000L) + 116444736000000000L;
    }
    
    public static long javaTimeToUnixTime(long javaTime) {
        return javaTime / 1000L;
    }
    
    public static Date winTimeToDate(long winTime) {
        return new Date(winTimeToJavaTime(winTime));
    }
    
    public static Date unixTimeToDate(long unixTime) {
        return new Date(unixTimeToJavaTime(unixTime));
    }
    
  
    
    public static long dateToWindowsTime(Date date) {
        
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null.");
        }
        
        return javaTimeToWinTime(date.getTime());
    }
    
    public static long dateToUnixTime(Date date) {
        
        if (date == null) {
            throw new IllegalArgumentException("date cannot be null.");
        }
        
        return javaTimeToUnixTime(date.getTime());
    }
    
    public static String getDateMonthYear(String strDate) throws IDMException {
		String strDateRet;
		SimpleDateFormat format = new SimpleDateFormat("M/dd/yyyy");
		DecimalFormat formatter = new DecimalFormat("00");

		format.setLenient(false);

		try {
			Date date = format.parse(strDate);
			GregorianCalendar calendar = new GregorianCalendar();
			// set time
			calendar.setTime(date);
			// get date fields
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			int month = calendar.get(Calendar.MONTH);
			int year = calendar.get(Calendar.YEAR);
			String dayformat = formatter.format(day);
			String monthformat = formatter.format(month + 1);
			strDateRet = monthformat + "/" + dayformat;

		} catch (ParseException e) {
			ErrorMessage error;
			error = idmErrors.get("E027");
			
			throw new IDMException(error, "Invalid Date format");
			
		}

		return strDateRet;
	}
    
	public static Timestamp getCurrentTimestamp() {

		Date today = new Date();
		Timestamp timeStamp = new Timestamp(today.getTime());
		return timeStamp;

	}
	
	
	public static String currentDateInString() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
