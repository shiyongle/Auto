package com.pc.dao.UUID.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;


import com.pc.dao.UUID.IUuidDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.model.CL_Uuid;

@Service("uuidDao")
public class UuidDao extends BaseDao<CL_Uuid, Integer> implements IUuidDao {

	@Override
	public String getIbatisSqlMapNamespace(){
		return "CL_Uuid";
	}
	
	@Override
	public List<CL_Uuid> getByUUID(String UUID) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUUID",UUID);
	}

	@Override
	public List<CL_Uuid> getByUUIDAndFid(String UUID, int fid) {
		// TODO Auto-generated method stub
		HashMap<String, Object> m = new HashMap<String, Object>();
		m.put("UUID", UUID);
		m.put("fid", fid);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUUIDAndFid", m);
	}

	
}
