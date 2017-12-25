package com.util;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.model.useronline.Useronline;



public class ServerContext{
//	private static OracleHelper OracleHelper;
	private static NumberHelper NumberHelper;
	private static String[] Specialstr;
	private static HashMap<String, Useronline> map = new HashMap<String, Useronline>();
	
	public static HashMap<String, Useronline> getUseronline() throws DJException {
		return map;
	}
	
	public static void setUseronline(String sessionId,Useronline useronline) throws DJException {
		if(!map.containsKey(sessionId)){
			map.put(sessionId, useronline);
		}
	}
	
	public static void delUseronline(String sessionId) throws DJException {
		if(map.containsKey(sessionId)){
			map.remove(sessionId);
		}
	}
	
	public static void delAllUseronline() throws DJException {
		map.clear();
	}
	
	public static void ServerContext() throws DJException {
//		OracleHelper=new OracleHelper();
		NumberHelper=new NumberHelper();
		Specialstr=new String[]{"--","%","*","'"};
	}
	public static String[] getSpecialstr() throws DJException {
		return Specialstr;
	}
//	public static OracleHelper getOracleHelper() throws DJException {
//		if (OracleHelper==null)
//		{
//			OracleHelper=new OracleHelper();
//		}
//		return OracleHelper;
//	}
	public static NumberHelper getNumberHelper() throws DJException {
		if (NumberHelper==null)
		{
			NumberHelper=new NumberHelper();
		}
		return NumberHelper;
	}
//	public static String GetEASid(String typestring) throws DJException {
//		String fid="";
//		PreparedStatement stmt=null;
//		try {
//			stmt=OracleHelper.GetBaseConn().prepareStatement("select newbosid('"+typestring+"') from dual");
//			ResultSet rs = stmt.executeQuery();
//			if(rs.next())
//			{
//				fid=rs.getString(1);
//			}
//			stmt.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			throw new DJException(e.getMessage());
//		}
//		return fid;
//	}
}