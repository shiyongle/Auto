package com.pc.dao.protocol.impl;


import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.pc.dao.UserRole.IUserRoleDao;
import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.protocol.IprotocolDao;
import com.pc.model.CL_Protocol;
import com.pc.model.CL_UserRole;

@Service("ProtocolDao")
public class ProtocolDao extends BaseDao<CL_Protocol, java.lang.Integer> implements IprotocolDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Protocol";
	}

	@Override
	public List<CL_Protocol> getBySpecAndType(Integer carSpecId,Integer userRoleId,Integer type) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("carSpecId", carSpecId);
		map.put("userRoleId", userRoleId);
		map.put("type", type);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getBySpecAndType",map);
	}

	@Override
	public List<CL_Protocol> findByfunitId(Integer funitId, Integer userRoleId) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("funitId", funitId);
		map.put("userRoleId", userRoleId);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".findByfunitId",map);
	}

	@Override
	public int updateDayRule(CL_Protocol protocol) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateDayRule",protocol);
	   }

	@Override
	public List<CL_Protocol> getByUserId(Integer userRoleId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserId",userRoleId);
	}

	@Override
	public int updateCarRule(CL_Protocol protocol) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateCarRule",protocol);
	}

	@Override
	public int updateLessRule(CL_Protocol protocol) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateLessRule",protocol);
	}
	
	@Override
	public List<CL_Protocol> getBySpecAndTypeAndDriver(Integer carSpecId,Integer userRoleId,Integer type,Integer fdriver_type) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("carSpecId", carSpecId);
		map.put("userRoleId", userRoleId);
		map.put("type", type);
		map.put("fdriver_type", fdriver_type);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getBySpecAndTypeAndDriver",map);
	}

//	@Override
//	public int newSave(CL_Protocol protocol) {
//		CL_UserRole urinfo = userRoleDao.getById(protocol.getUserRoleId());
//		urinfo.setProtocol(true);
//		userRoleDao.save(urinfo);
//		return this.save(protocol);
//	}
//	

}