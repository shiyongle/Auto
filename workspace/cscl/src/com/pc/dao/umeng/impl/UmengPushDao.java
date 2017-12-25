package com.pc.dao.umeng.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.umeng.IUMengPushDao;
import com.pc.model.CL_Umeng_Push;

@Service("UmengPushDao")
public class UmengPushDao extends BaseDao<CL_Umeng_Push, java.lang.Integer> implements IUMengPushDao {


	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Umeng_Push";
	}
	@Override
	public List<CL_Umeng_Push> getUserUmengRegistration(Integer fuserid)
	{
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("fuserid", fuserid);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getDevice",map);
	}
	
	@Override
	public List<CL_Umeng_Push> getUserUmengRegistration(Integer fuserid,Integer ftype)
	{
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("fuserid", fuserid);
		map.put("ftype", ftype);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getDevice",map);
	}
	@Override
	public List<CL_Umeng_Push> getUserPhone(String fuserPhone) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("fuserPhone", fuserPhone);
		map.put("ftype", null);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getUserPhone",map);
	}
	@Override
	public List<CL_Umeng_Push> getUserPhone(String fuserPhone, Integer ftype) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("fuserPhone", fuserPhone);
		map.put("ftype", ftype);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getUserPhone",map);
	}
}
