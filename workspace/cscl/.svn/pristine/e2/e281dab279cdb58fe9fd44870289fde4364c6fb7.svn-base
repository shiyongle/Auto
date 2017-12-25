package com.pc.util;


import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MD5Util {
	
	public static String getMD5String(String convertStr){
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(convertStr.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
	
	
	/***java获取唯一码*/ 
	public static String getUUIDNumber() {
		String s = UUID.randomUUID().toString();
		return "cscl_"+s.replace("-", "");
	}
	
	/***批次号*/
	public static String getBatchCode() {
		SimpleDateFormat sf = new SimpleDateFormat("yyMMddHHmmssSSS");
		return "P"+sf.format(new Date());
	}
	
	
	public static void main(String args[]){
		MD5Util md =new MD5Util();
		md.getMD5String("ccdjadmin111");
		System.out.println(md.getBatchCode());
	}
	
	
	
}
