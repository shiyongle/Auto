package com.pc.dao.distance.impl;


import java.math.BigDecimal;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.distance.IDistanceDao;
import com.pc.model.CL_Distance;

@Service("distanceDao")
public class DistanceDao extends BaseDao<CL_Distance,java.lang.Integer> implements IDistanceDao {
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Distance";
	}

	@Override
	public BigDecimal getMileage(Integer fcustomer_id, Integer faddressDel_id,
			Integer faddressRec_id) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("fcustomer_id", fcustomer_id);
		m.put("faddressDel_id", faddressDel_id);
		m.put("faddressRec_id", faddressRec_id);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getMileage",m);
	}

	@Override
	public int isExist(Integer fcustomer_id, Integer faddressDel_id,
			Integer faddressRec_id) {
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("fcustomer_id", fcustomer_id);
		m.put("faddressDel_id", faddressDel_id);
		m.put("faddressRec_id", faddressRec_id);
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".isExist",m);
	}
	

}
