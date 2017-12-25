package com.pc.dao.abnormity.impl;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.abnormity.IabnormityDao;
import com.pc.model.CL_Abnormity;
@Service("abnormityDao")
public class abnormityDao extends  BaseDao<CL_Abnormity, java.lang.Integer> implements IabnormityDao{
    
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Abnormity";
	}

	@Override
	public int updateUpId(Integer id, Integer fuploadId) {
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("fuploadId", fuploadId);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateUpId",map);
	}

	@Override
	public List<Map<String, Object>> getOrderInfoByAbId(Integer fid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOrderInfoByAbId",fid);
	}

	@Override
	public int haveNewAbnormal() {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".haveNewAbnormal");
	}

	@Override
	public int updateRemark(Integer id, String fremark) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map=new HashMap<String,Object>();
		map.put("id", id);
		map.put("fremark", fremark);
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateRemark",map);
	}
	
}
