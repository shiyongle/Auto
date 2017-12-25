package com.pc.dao.carRanking;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_CarRanking;
  /*
   * twr
   */
   
public interface IcarRankingDao extends IBaseDao<CL_CarRanking,java.lang.String>{

	   //获得前三司机排名
	public List<CL_CarRanking> getAll();
}
