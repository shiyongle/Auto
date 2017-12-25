package com.pc.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//字符串处理 工具类  蒋凡毅
public class String_Custom {

	/**
	 * 判断不包含此字符串
	 * 
	 * @param txt
	 *            原文字
	 * @param contains
	 *            判断是否包含的文字
	 * @return true 不包含
	 */
	public static boolean noContains(String txt, String contains) {
		if (txt.indexOf(contains) != -1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 正则表达式：验证用户名
	 */
	public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

	/**
	 * 正则表达式：验证密码
	 */
	public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

	/**
	 * 正则表达式：验证手机号
	 */
	public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(18[0,2,3,5-9]))\\d{8}$";

	/**
	 * 正则表达式：验证邮箱
	 */
	public static final String REGEX_EMAIL = "^((13[0-9])|(15[0-9])|(18[0-9])|(17[0-8])|(147)|(145))\\d{8}$";

	/**
	 * 正则表达式：验证汉字
	 */
	public static final String REGEX_CHINESE = "[\\u4e00-\\u9fa5]+";

	/**
	 * 正则表达式：验证身份证
	 */
	public static final String REGEX_ID_CARD = "(\\d{14}[0-9a-zA-Z])|(\\d{17}[0-9a-zA-Z])";

	/**
	 * 正则表达式：验证URL
	 */
	public static final String REGEX_URL = "http(s)?://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";

	/**
	 * 正则表达式：验证IP地址
	 */
	public static final String REGEX_IP_ADDR = "(25[0-5]|2[0-4]\\d|[0-1]\\d{2}|[1-9]?\\d)";

	/**
	 * 正则表达式：是否为数字
	 */
	public static final String REGEX_Number = "[0-9]*";
	
	/**
	 * 正则表达式：是否为浮点
	 */
	public static final String REGEX_Float = "[0-9]+(\\.?)[0-9]*";

	/**
	 * 校验是否为数字
	 * 
	 * @param username
	 * @return 不是数字返回 true
	 */
	public static boolean noNumber(String username) {
		return !Pattern.matches(REGEX_Number, username);
	}

	/**
	 * 校验用户名
	 * 
	 * @param username
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noUsername(String username) {
		return !Pattern.matches(REGEX_USERNAME, username);
	}

	/**
	 * 校验密码
	 * 
	 * @param password
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noPassword(String password) {
		return !Pattern.matches(REGEX_PASSWORD, password);
	}

	/**
	 * 校验手机号
	 * 
	 * @param mobile
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noMobile(String mobile) {
		return !Pattern.matches(REGEX_MOBILE, mobile);
	}

	/**
	 * 校验邮箱
	 * 
	 * @param email
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noEmail(String email) {
		return !Pattern.matches(REGEX_EMAIL, email);
	}

	/**
	 * 校验汉字
	 * 
	 * @param chinese
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noChinese(String chinese) {
		Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m = p_str.matcher(chinese);
		if (m.find() && m.group(0).equals(chinese)) {
			return false;
		}
		return true;
	}

	/**
	 * 校验身份证
	 * 
	 * @param idCard
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noIDCard(String idCard) {
		return !Pattern.matches(REGEX_ID_CARD, idCard);
	}

	/**
	 * 校验URL
	 * 
	 * @param url
	 * @return 校验通过返回true，否则返回false
	 */
	public static boolean noUrl(String url) {
		return !Pattern.matches(REGEX_URL, url);
	}

	/**
	 * 校验IP地址
	 * 
	 * @param ipAddr
	 * @return
	 */
	public static boolean noIPAddr(String ipAddr) {
		return !Pattern.matches(REGEX_IP_ADDR, ipAddr);
	}
	
	/**
	 * 校验是否浮点
	 * @param ipAddr
	 * @return 不是浮点返回false
	 */
	public static boolean noFloat(String ipAddr) {
		return !Pattern.matches(REGEX_Float, ipAddr);
	}

	
	
	/**
	 * 是否为空判断
	 * 
	 * @param paramenters
	 *            需要判断的列表
	 * @param msg
	 *            存在则提示的信息
	 * @return 如返回长度为0的字符串 则需要判断的列表没有存在空对象
	 */
	public static String parametersEmpty(String[] paramenters, String msg) {
		for (int i = 0; i < paramenters.length; i++) {
			if (paramenters[i] == null || paramenters[i].length() <= 0)
				return msg;
		}
		return "";
	}
	
	public static String parametersEmpty(String[] paramenters) {
		for (int i = 0; i < paramenters.length; i++) {
			if (paramenters[i] == null || paramenters[i].length() <= 0)
				return "缺少必要参数!";
		}
		return "";
	}
	
	

	public static boolean noNull(String string) {
		if (string == null || string.length() <= 0)
			return true;
		return false;
	}

	
	/**
	 * 获取支付所需要的创建时间
	 * @return
	 */
	public static String getPayOrderCreateTime()
	{
		Date nowTime=new Date();
        SimpleDateFormat time=new SimpleDateFormat("yyyMMdd");
		return time.format(nowTime);
	}
	
	
}
