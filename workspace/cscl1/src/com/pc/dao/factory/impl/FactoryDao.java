package com.pc.dao.factory.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.factory.IFactoryDao;
import com.pc.model.CL_Factory;

@Service("factoryDao")
public class FactoryDao extends BaseDao<CL_Factory, Integer> implements IFactoryDao {

	@Override
	public String getIbatisSqlMapNamespace(){
		return "CL_Factory";
	}

	@Override
	public List<CL_Factory> getAllUseFactory() {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+".getAllUseFactory");
	}
	
	

}
