package com.pc.dao.protocol;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Protocol;

public interface IprotocolDao extends IBaseDao<CL_Protocol, java.lang.Integer> {
	
	
	/***** 通过车辆类型和车辆规格查询 数据库以及用户ID是否有 数据*****/
	public List<CL_Protocol> getBySpecAndType(Integer carSpecId,Integer userRoleId,Integer type);
	 
	/**通过零担类型规则查询数据库是否有数据**/
	public List<CL_Protocol> findByfunitId(Integer funitId,Integer userRoleId);
	
	/****更新包天规则*/
	public int updateDayRule (CL_Protocol protocol);

	/****更新整车规则*/
	public int updateCarRule (CL_Protocol protocol);
	

	/****更新零担规则*/
	public int updateLessRule (CL_Protocol protocol);
	
    /**通过用户ID查所有协议*/
	public List<CL_Protocol> getByUserId(Integer userRoleId);
	
	public List<CL_Protocol> getBySpecAndTypeAndDriver(Integer carSpecId,Integer userRoleId,Integer type,Integer fdriver_type);
		
}