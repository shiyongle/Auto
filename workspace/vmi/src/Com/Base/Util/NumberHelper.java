package Com.Base.Util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Com.Dao.System.ISysUserDao;

public class NumberHelper {
	private HashMap<String, Integer> Numbercls = new HashMap<>();
	private HashMap<String, Integer> TempNumbercls = new HashMap<>();
	private String datestr = "";
	private ISysUserDao ISysUDao ;

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
	private int getDataBaseNumber(String table, String nowString, int length)
	{
		if(ISysUDao==null)
		{
			ISysUDao = (ISysUserDao) SpringContextUtils
					.getBean("SysUserDao");
		}
		String sql="select max(fnumber) fnumber from "+table+"  where fnumber like :datenow and LENGTH(fnumber)="+(nowString.length()+length);
		params p = new params();
		p.put("datenow",nowString+"%" );
		List<HashMap<String, Object>> sList= ISysUDao.QueryBySql(sql, p);
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

	public String getNumber(String table, String startstr, int length,
			boolean istemp) {
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
				num=getDataBaseNumber(table, nowString, length)+1;
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
				num=getDataBaseNumber(table, nowString, length)+1;
			}
			Numbercls.put(key, num);
			return nowString+"000000".substring(0,length-(num+"").length())+num;
		}
	}
	
	public String getFtuNumber(String fsupplierid) {
		String table = "t_ftu_saledeliver";
		String startstr = "CPS";
		int length = 4;
		checkdatetime();
		String key =table+startstr+length;
		String nowString=startstr+datestr;
		int num = 0;
//		if(Numbercls.containsKey(key))
//		{
//			num=Numbercls.get(key)+1;
//		}
//		else
//		{
			if(ISysUDao==null)
			{
				ISysUDao = (ISysUserDao) SpringContextUtils
						.getBean("SysUserDao");
			}
			String sql="select max(fnumber) fnumber from "+table+"  where fnumber like :datenow and LENGTH(fnumber)="+(nowString.length()+length)+" and fsupplierid ='"+fsupplierid+"'";
			params p = new params();
			p.put("datenow",nowString+"%" );
			List<HashMap<String, Object>> sList= ISysUDao.QueryBySql(sql, p);
			if(sList.size()>0)
			{
				
				if(sList.get(0).get("fnumber")!=null)
				{
					String fnumber=sList.get(0).get("fnumber").toString();
					num = Integer.parseInt(fnumber.substring(fnumber.length()-length+1, fnumber.length()));
				}
			}else{
				num = 0;
			}
			num++;
//		}
//		Numbercls.put(key, num);
		return nowString+"-"+"000".substring(0,3-(num+"").length())+num;
	}
	
	public HashMap<String, Integer> getNumbercls() {
		return this.Numbercls;
	}
	
	public HashMap<String, Integer> getTempNumbercls() {
		return this.TempNumbercls;
	}

}
