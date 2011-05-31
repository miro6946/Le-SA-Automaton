package kr.lesaautomaton.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncrytionUtils {
	
	public static String password(String input) throws NoSuchAlgorithmException{
		
		MessageDigest md = MessageDigest.getInstance("SHA1");
		
		byte[] encrypted = md.digest(input.getBytes());
		encrypted = md.digest(encrypted);
		
		StringBuffer output = new StringBuffer(encrypted.length + 1);
		output.append("*");
		for (byte b : encrypted) {
			output.append(Integer.toHexString(b & 0xff).toUpperCase());
		}
		
		return output.toString();
		
	}

}
