package com.pc.dao.carLine;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_CarLine;
import com.pc.query.carLine.CarLineQuery;

public interface ICarLineDao extends IBaseDao<CL_CarLine, Integer> {
	
	//查排队中
	public List<CL_CarLine> getByUserId(Integer userRoleId);
	
	//查所有（签到时间desc）
	public List<CL_CarLine> getByUserId2(Integer userRoleId);
	
	//按签到时间排序
	public List<Integer> findSort(Integer area, Integer specId);
	
	public Page<CL_CarLine> findPage2(CarLineQuery query);
}
