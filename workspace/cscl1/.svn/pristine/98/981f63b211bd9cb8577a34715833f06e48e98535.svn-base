package com.pc.dao.carRanking.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.carRanking.IcarRankingDao;
import com.pc.model.CL_CarRanking;
/** 
 *  twr
 * */
@Service("carRankingDao")
public class carRankingDao extends BaseDao<CL_CarRanking,java.lang.String> implements IcarRankingDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_CarRanking";
	}

	@Override
	public List<CL_CarRanking> getAll() {
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAll");
	}
}
