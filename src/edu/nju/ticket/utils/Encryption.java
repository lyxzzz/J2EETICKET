package edu.nju.ticket.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

final public class Encryption {
	private static Encryption singleton;
	private static final char[] HEX_DIGITS= {'0','1','2','3','4','5','6','7','8','9','a'
			,'b','c','d','e','f'};
	private Encryption()
	{
		
	}
	public static Encryption getInstance()
	{
		if(singleton==null) singleton=new Encryption();
		return singleton;
	}
	public String sha1(String word)
	{
		MessageDigest message;
		try {
			message = MessageDigest.getInstance("SHA1");
			message.update(word.getBytes());
			return handle(message.digest());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			return null;
		}
	}
	private String handle(byte[] bytes)
	{
		int len=bytes.length;
		StringBuilder buf=new StringBuilder(len*2);
		for (int i = 0; i < len; i++) {  
            buf.append(HEX_DIGITS[(bytes[i] >> 4) & 0x0f]);  
            buf.append(HEX_DIGITS[bytes[i] & 0x0f]);  
        }  
        return buf.toString(); 
	}
}
