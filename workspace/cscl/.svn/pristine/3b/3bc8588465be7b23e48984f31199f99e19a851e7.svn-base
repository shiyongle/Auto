package com.pc.dao.address.impl;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.address.IAddressDao;
import com.pc.model.CL_Address;

/*
 * CPS-VMI-wangc
 */

@Service("addressDao")
public class AddressDao extends BaseDao<CL_Address,java.lang.Integer> implements IAddressDao{
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_Address";
	}

	@Override
	public int isExist(int id) {
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+".isExist", id);
	}
	

}
