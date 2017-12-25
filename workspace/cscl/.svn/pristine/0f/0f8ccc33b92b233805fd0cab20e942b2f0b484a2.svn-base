package com.pc.dao.rule.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.rule.IRuleDao;
import com.pc.model.CL_Rule;

@Service("ruleDao")
public class RuleDao extends BaseDao<CL_Rule,java.lang.Integer> implements IRuleDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Rule";
	}
	


	@Override
	public CL_Rule getZhengche(Integer specId) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getZhengche",specId);
	}

	@Override
	public CL_Rule getLingdanForVolume() {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getLingdanForVolume");
	}

	@Override
	public CL_Rule getLingdanForWeight() {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".getLingdanForWeight");
	}



	@Override
	public int updateCarLoad(CL_Rule rule) {
		return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateCarLoad",rule);
	}


	@Override
	public int updataLess(CL_Rule rule) {
	   return this.getSqlSession().update(getIbatisSqlMapNamespace()+".updateLessRole",rule);
	}



	@Override
	public List<CL_Rule> getOneByType(Integer specId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getOneByType",specId);
	}



	@Override
	public List<CL_Rule> getBySpec(Integer specId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getBySpec",specId);
	}



	@Override
	public List<CL_Rule> getDriverBySpec(Integer specId) {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getDriverBySpec",specId);
	}
}
