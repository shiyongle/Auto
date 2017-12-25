package com.pc.appInterface.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class DongjingResponse {
	private boolean success=false;
	private String msgstr;
	private int total_results=0 ;
	private List<DongjingResult> Resultitems;
	private String ResultString;
	public DongjingResponse()
	{
		Resultitems=new ArrayList<DongjingResult>();
	}
	public List<DongjingResult> getResultitems() {
		return Resultitems;
	}

	public String getMsgstr() {
		return msgstr;
	}
	public boolean isSuccess() {
		return success;
	}
	public int getTotal_results() {
		return total_results;
	}
	public String getResultString() {
		return ResultString;
	}
	public void setResultdata(String resultstring) {
		ResultString = resultstring;
		 if(!ResultString.startsWith("{"))
	     {
	      	this.success=false;
	       	this.msgstr="返回信息异常";
	     }
		 else
		 {
			 createitems();
		 }
	}
	private void createitems()
	{
		Resultitems.clear();
		JSONObject jsonobject = JSONObject.fromObject(ResultString);
		success=jsonobject.getBoolean("success");
		if(jsonobject.containsKey("msg"))
		{
			msgstr=jsonobject.getString("msg");
		}
		else
		{
			msgstr="";
		}
		if(jsonobject.containsKey("total") && !jsonobject.getString("total").equals(""))
		{
			total_results=jsonobject.getInt("total");
		}
		else
		{
			total_results=0;
		}
		if(jsonobject.containsKey("data") && !jsonobject.getString("data").equals(""))
		{
			if(jsonobject.optJSONArray("data")!=null)
			{
				for(int i=0;i<jsonobject.getJSONArray("data").size();i++)
				{
					JSONObject subinfo=jsonobject.getJSONArray("data").getJSONObject(i);
					DongjingResult info=new DongjingResult();
					Set<String> set=subinfo.keySet();
					Iterator<String> it = set.iterator();
					while (it.hasNext()) {
						String key=it.next();
						info.put(key, subinfo.getString(key));
					}
					Resultitems.add(info);
				}
			}
			else
			{
				DongjingResult info=new DongjingResult();
				Set<String> set=jsonobject.getJSONObject("data").keySet();
				Iterator<String> it = set.iterator();
				while (it.hasNext()) {
					String key=it.next();
					info.put(key, jsonobject.getJSONObject("data").getString(key));
				}
				Resultitems.add(info);
				total_results=1;
			}
		}
	}
	
}
