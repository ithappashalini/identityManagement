package com.rheal.security.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.MessageDigestAlgorithms;

/**
 * 
 * @author Prashanth Errabelli
 *
 */
public class SHAHashing {
     
    public static String getHashVal(String str){
        
    	return new DigestUtils(MessageDigestAlgorithms.SHA3_256).digestAsHex(str);
    }
}
