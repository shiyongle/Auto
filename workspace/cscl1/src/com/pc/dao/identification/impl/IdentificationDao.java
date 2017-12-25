package com.pc.dao.identification.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.page.PageRequest;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.identification.IidentificationDao;
import com.pc.model.CL_Identification;
import com.pc.query.identification.IdentificationQuery;

@Service("identificationDao")
public class IdentificationDao extends BaseDao<CL_Identification,java.lang.Integer> implements IidentificationDao {
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Identification";
	}
	
	public List<CL_Identification> getByUserRoleIdAndType(Integer userRoleId,Integer type) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("type", type);
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserRoleIdAndType", map);
	}
	
	public List<CL_Identification> getStatusByUserRoleId(Integer userRoleId){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getStatusByUserRoleId", userRoleId);
	}
	
	public List<CL_Identification> getByUserRoleId(Integer userRoleId){
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserRoleId", userRoleId);
	}

	public List<CL_Identification> getIdentificationsByStatus(Integer type) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getIdentificationsByStatus", type);
	}


	@Override
	public CL_Identification getByUserRoleIdAndStatus(Integer userRoleId,
			Integer status) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("status", status);
      return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getByUserRoleIdAndStatus",map);
	}

	@Override
	public int deleteByRuleId(Integer userRoleId, Integer type) {
		HashMap<String,Object> map =new HashMap<String,Object>();
		map.put("userRoleId", userRoleId);
		map.put("type", type);
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deleteByRuleId",map);
	}

	@Override
	public List<CL_Identification> getByUserRoleIdAndRz(Integer userRoleId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getByUserRoleIdAndRz",userRoleId);
	}

	@Override
	public Page<CL_Identification> findHuoPage(PageRequest pr) {
		return this.pageQuery(getIbatisSqlMapNamespace() + ".findHuoPage", pr);
	}

	@Override
	public int deleteByUserRoleId(Integer userRoleId) {
		// TODO Auto-generated method stub
		return this.getSqlSession().delete(getIbatisSqlMapNamespace()+".deleteByUserRoleId",userRoleId);
	}

}
