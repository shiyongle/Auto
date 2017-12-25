package com.pc.dao.usercustomer;

import java.util.List;

import com.pc.dao.aBase.IBaseDao;
import com.pc.model.CL_UserCustomer;

public interface IUserCustomerDao  extends IBaseDao<CL_UserCustomer, java.lang.Integer>{
	public List<CL_UserCustomer> getByUrid(int urid); 
	public int getMaxId();
}
