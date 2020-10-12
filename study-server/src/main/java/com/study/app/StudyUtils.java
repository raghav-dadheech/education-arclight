package com.study.app;

import java.util.Random;

public class StudyUtils {

	public static String getTokenString() {
		int leftLimit = 48; // numeral '0'
		int rightLimit = 122; // letter 'z'
		int targetStringLength = 100;
		Random random = new Random();

		return random.ints(leftLimit, rightLimit + 1).filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
				.limit(targetStringLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
	}
	
	public static String generateOTP() 
	{ 
		int len = 6;
	    // All possible characters of my OTP 
	    String str = "abcdefghijklmnopqrstuvwxyzABCD"
	            +"EFGHIJKLMNOPQRSTUVWXYZ0123456789"; 
	    int n = str.length(); 
	  
	    // String to hold my OTP 
	    String OTP=""; 
	  
	    for (int i = 1; i <= len; i++) 
	        OTP += (str.charAt((int) ((Math.random()*10) % n))); 
	  
	    return(OTP); 
	} 
	  
}
