package com.pc.dao.rule;




import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Rule;
public interface IRuleDao extends IBaseDao<CL_Rule, java.lang.Integer>{

	/***根据车厢获取有效的规则*/
	public CL_Rule getZhengche(Integer specId);
	/***获取零担中体积有效的规则*/
	public CL_Rule getLingdanForVolume();
	/***获取零担中重量有效的规则*/
	public CL_Rule getLingdanForWeight();
	/***跟新整车规则*/
	public int updateCarLoad(CL_Rule rule);

	/***跟新零担规则*/
	public int updataLess(CL_Rule rule);
	
	/*** 获取司机计费规则*/
	public List<CL_Rule> getOneByType(Integer specId);
	
	//获取有效的同一车辆规格的规则
	public List<CL_Rule> getBySpec(Integer specId);
	
	//获取有效的同一车辆规格的规则
	public List<CL_Rule> getDriverBySpec(Integer specId);
}
