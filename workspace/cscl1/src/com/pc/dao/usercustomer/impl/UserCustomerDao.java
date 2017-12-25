package com.pc.dao.usercustomer.impl;

import java.util.List;

import org.springframework.stereotype.Service;


import com.pc.dao.aBase.impl.BaseDao;
import com.pc.dao.usercustomer.IUserCustomerDao;
import com.pc.model.CL_UserCustomer;
@Service("userCustomerDao")
public class UserCustomerDao extends BaseDao<CL_UserCustomer, java.lang.Integer> implements IUserCustomerDao {
	
	@Override
	public String getIbatisSqlMapNamespace() {
		return "CL_UserCustomer";
	}

	@Override
	public List<CL_UserCustomer> getByUrid(int urid) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectList(getIbatisSqlMapNamespace()+ ".getByUrid", urid);
	}
	
	/**得到maxID**/
	public int getMaxId(){
		return this.getSqlSession().selectOne(getIbatisSqlMapNamespace()+ ".getMaxId");
	}
}
