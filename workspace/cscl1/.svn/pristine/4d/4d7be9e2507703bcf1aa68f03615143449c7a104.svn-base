package com.pc.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import com.pc.dao.order.IorderDao;
import com.pc.util.SpringContextUtils;

public class CacheUtilByCC {
	private final static String CACHEMAP = "cacheMap";
	private final static String DATESTR = "datestr";
	private static HashMap<String, Integer> cacheMap = new HashMap<>();
	private static IorderDao orderdao;
	private static String datestr="";
	
	//日期跨天时将缓中所有的编码归零，不用从数据库查  BY CC
	private static void checkdatetime()
	{
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		String nowdatestr = df.format(nowdate);
		datestr = RedisUtil.get(DATESTR);
		if(!nowdatestr.equals(datestr))
		{
			datestr=nowdatestr;
			RedisUtil.set(DATESTR, datestr);
//			Set<String> keys = RedisUtil.hkeys(CACHEMAP);
//			Set<String> keys = cacheMap.keySet();
//			Iterator<String> it = keys.iterator();
//			while (it.hasNext()) {
				RedisUtil.del(CACHEMAP);
//				cacheMap.put(it.next(), 0);
//			}
		}
	}
	public static void init()
	{
		// 初始化DAO接口，不要外部代码传递 BY CC
		if (orderdao == null) {
			orderdao = (IorderDao) SpringContextUtils.getBean("orderDao");
		}
	}

	/***
	 * BY CC
	 * @param table数据表
	 * @param startstr编码首字符
	 * @param length编码流水号长度
	 * @return startstr+"yyyyMMdd"+流水号(length指定长度)
	 */
	public static String getOrderNumber(String table, String startstr, int length) {
		//校验是否跨天   BY CC
		checkdatetime();
		int num;
		//按表及编码规则做KEY   BY CC
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		if(RedisUtil.hexists(CACHEMAP, key)){
//		if (cacheMap.containsKey(key)) {
			//有就取缓存的编码
			num = Integer.valueOf(RedisUtil.hget(CACHEMAP, key))+1;
//			num =cacheMap.get(key) + 1;
		} else {
			// TODO:需校验数据库,然后赋值重新赋值NUM;
			//如果没有，说明缓存中没有，需要从数据库中重新查找  BY CC
			Map<Object, Object> result= orderdao.getMaxNOBytable(table, nowString+"%",nowString.length()+length);
			if(result==null || result.get("number")==null)
			{
				num= 1;
			}
			else
			{
				String fnumber=result.get("number").toString();
//				num=Integer.parseInt(fnumber.substring(fnumber.lastIndexOf("0")+1, fnumber.length()));
				num= Integer.parseInt(fnumber.substring(fnumber.length()-length, fnumber.length()))+1;
			}
		}
		RedisUtil.hset(CACHEMAP, key, String.valueOf(num));
//		cacheMap.put(key, num);
		return nowString+"00000000000000000000".substring(0,(length-(num+"").length()))+num;
	}

	//APP新旧版本校验; 
	public static boolean isOldVersion(String VersionCode,int ftype) throws DJException {
		boolean isOldVersion = true;
		
		Map<Object, Object> result= orderdao.getAppVersion(VersionCode,ftype);
		
		if(result!=null && result.get("forcel")!=null && "0".equals(result.get("forcel").toString())){
			isOldVersion = false;
		}
		
		return isOldVersion;
		
	}

}
