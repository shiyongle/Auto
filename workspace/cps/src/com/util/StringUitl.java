package com.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
/*** 字符串工具类*/
public class StringUitl {
	
	public static Random random = new Random();
	/**
	 * 获取当前时间字符串
	 * @return 当前时间字符串
	 */
	public static String getStringTime(){
		Date date = new Date();//获取当前系统时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSSS");//设置格式化格式
		return sdf.format(date);//返回格式化后的时间
	}
	/**
	 * 生成订单号
	 * @return 订单号
	 */
	public static String createOrderId(){
		StringBuffer sb = new StringBuffer();//定义字符串对象
		sb.append(getStringTime());//向字符串对象中添加当前系统时间
		for (int i = 0; i < 3; i++) {//随机生成3位数
			sb.append(random.nextInt(9));//将随机生成的数字添加到字符串对象中
		}
		return sb.toString();//返回字符串
	}
	/**
	 * 验证字符串的有效性
	 * @param s 验证字符串
	 * @return 是否有效的布尔值
	 */
	public static boolean validateString(String s){
		if(s != null && s.trim().length() > 0){//如果字符串不为空返回true
			return true;
		}
		return false;//字符串为空返回false
	}
	/**
	 * 验证浮点对象的有效性
	 * @param f 浮点对象
	 * @return 是否有效的布尔值
	 */
	public static boolean validateFloat(Float f){
		try {
			if(f != null && f > 0){
				return true;
			}
		} catch (Exception e) {}
		return false;
	}
	
	/**
	 * 判断对象或对象数组中每一个对象是否为空: 对象为null，字符序列长度为0，集合类、Map为empty
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null)
			return true;

		if (obj instanceof CharSequence)
			return ((CharSequence) obj).length() == 0;

		if (obj instanceof Collection)
			return ((Collection) obj).isEmpty();

		if (obj instanceof Map)
			return ((Map) obj).isEmpty();

		if (obj instanceof Object[]) {
			Object[] object = (Object[]) obj;
			if (object.length == 0) {
				return true;
			}
			boolean empty = true;
			for (int i = 0; i < object.length; i++) {
				if (!isNullOrEmpty(object[i])) {
					empty = false;
					break;
				}
			}
			return empty;
		}
		return false;
	}
	
	//2016-01-24 list 构造成 字符串 用于 sql语句 in 使用
	public static String buildIdStringFromList(List list){
		StringBuffer ids = new StringBuffer();
		String idStr = "";
		Iterator ite = list.iterator();
		while(ite.hasNext()){
			ids.append("'").append(ite.next().toString()).append("'").append(",");
		}
		if(ids.length()>0){
			idStr =  "(" + ids.toString().substring(0,ids.length()-1) + ")"; 
		}
    	return idStr;
    }
}