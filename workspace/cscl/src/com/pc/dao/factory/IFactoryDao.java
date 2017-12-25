package com.pc.dao.factory;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_Factory;

public interface IFactoryDao extends IBaseDao<CL_Factory, Integer> {
	
	//获取所有启用的工厂
	public List<CL_Factory> getAllUseFactory();
	
}
