package com.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.dao.IBaseDao;
import com.service.IBaseManager;



public class NumberHelper {
	protected IBaseManager baseManager;
	protected IBaseDao baseDao;
	private HashMap<String, Integer> Numbercls = new HashMap<>();
	private HashMap<String, Integer> TempNumbercls = new HashMap<>();
	private String datestr = "";
	
	public NumberHelper() throws DJException {
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		datestr = df.format(nowdate);
	}
	private void checkdatetime()
	{
		Date nowdate = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");
		String nowdatestr = df.format(nowdate);
		if(!nowdatestr.equals(datestr))
		{
			datestr=nowdatestr;
			Set<String> keys = Numbercls.keySet();
			Iterator<String> it = keys.iterator();
			while (it.hasNext()) {
				Numbercls.put(it.next(), 0);
			}
			Set<String> tempkeys = TempNumbercls.keySet();
			Iterator<String> tempit = tempkeys.iterator();
			while (tempit.hasNext()) {
				TempNumbercls.put(tempit.next(), 0);
			}
		}
	}

	private int getDataBaseNumber(IBaseManager baseManager,String table, String nowString, int length)
	{
		String sql="select max(fnumber) fnumber from "+table+"  where fnumber like :datenow and LENGTH(fnumber)="+(nowString.length()+length);
		Params p = new Params();
		p.put("datenow",nowString+"%" );
		List<HashMap<String, Object>> sList= baseManager.QueryBySql(sql, p);
		if(sList.size()>0)
		{
			
			if(sList.get(0).get("fnumber")!=null)
			{
				String fnumber=sList.get(0).get("fnumber").toString();
				return Integer.parseInt(fnumber.substring(fnumber.length()-length, fnumber.length()));
			}
		}
		return 0;
	}
	
	private int getDataBaseNumber(IBaseDao baseDao,String table, String nowString, int length)
	{
		String sql="select max(fnumber) fnumber from "+table+"  where fnumber like :datenow and LENGTH(fnumber)="+(nowString.length()+length);
		Params p = new Params();
		p.put("datenow",nowString+"%" );
		List<HashMap<String, Object>> sList= baseManager.QueryBySql(sql, p);
		if(sList.size()>0)
		{
			
			if(sList.get(0).get("fnumber")!=null)
			{
				String fnumber=sList.get(0).get("fnumber").toString();
				return Integer.parseInt(fnumber.substring(fnumber.length()-length, fnumber.length()));
			}
		}
		return 0;
	}
	
	
	public String getNumber(IBaseDao baseDao,String table, String startstr, int length,
			boolean istemp) {
		this.baseManager = baseManager;
		this.baseDao = baseDao;
		checkdatetime();
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		int num;
		if (istemp) {
			if(TempNumbercls.containsKey(key))
			{
				num=TempNumbercls.get(key)+1;
			}
			else
			{
				num=getDataBaseNumber(baseManager,table, nowString, length)+1;
			}
			TempNumbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
		else
		{
			if(Numbercls.containsKey(key))
			{
				num=Numbercls.get(key)+1;
			}
			else
			{
				num=getDataBaseNumber(baseDao,table, nowString, length)+1;
			}
			Numbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
	}
	
	public String getNumber(IBaseManager baseManager,String table, String startstr, int length,
			boolean istemp) {
		this.baseManager = baseManager;
		checkdatetime();
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		int num;
		if (istemp) {
			if(TempNumbercls.containsKey(key))
			{
				num=TempNumbercls.get(key)+1;
			}
			else
			{
				num=getDataBaseNumber(baseManager,table, nowString, length)+1;
			}
			TempNumbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
		else
		{
			if(Numbercls.containsKey(key))
			{
				num=Numbercls.get(key)+1;
			}
			else
			{
				num=getDataBaseNumber(baseManager,table, nowString, length)+1;
			}
			Numbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
	}
	
	public HashMap<String, Integer> getNumbercls() {
		return this.Numbercls;
	}
	
	public HashMap<String, Integer> getTempNumbercls() {
		return this.TempNumbercls;
	}

}
