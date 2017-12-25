package com.pc.dao.travelBus.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.travelBus.ItravelBusDao;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_TravelBus;

@Service("TravelBusDao")
public class TravelBusDao extends BaseDao<CL_TravelBus, java.lang.Integer> implements ItravelBusDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_TravelBus";
	}

	@Override
	public List<CL_TravelBus> getByUserId(Integer userRoleId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserId",userRoleId);
	}

	@Override
	public int deleteByIdAndUser(Integer id, Integer userroleId) {
		// TODO Auto-generated method stub
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("fdevice", userroleId);
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".getDevice", map);
	}

}