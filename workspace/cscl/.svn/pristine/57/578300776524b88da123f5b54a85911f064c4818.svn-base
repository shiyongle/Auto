package com.pc.dao.carLine.impl;


import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.carLine.ICarLineDao;
import com.pc.model.CL_CarLine;
import com.pc.query.carLine.CarLineQuery;

@Service("carLineDao")
public class CarLineDao extends BaseDao<CL_CarLine, Integer> implements ICarLineDao {

	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_CarLine";
	}

	@Override
	public List<CL_CarLine> getByUserId(Integer userRoleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserId", userRoleId);
	}
	
	@Override
	public List<CL_CarLine> getByUserId2(Integer userRoleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserId2", userRoleId);
	}

	@Override
	public List<Integer> findSort(Integer area, Integer specId) {
		// TODO Auto-generated method stub
		HashMap<String, Object> m = new HashMap<>();
		m.put("area", area);
		m.put("specId", specId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".findSort", m);
	}

	@Override
	public Page<CL_CarLine> findPage2(CarLineQuery query) {
		// TODO Auto-generated method stub
		return pageQuery(getIbatisSqlMapNamespace() + ".findPage2", query);
	}

}
