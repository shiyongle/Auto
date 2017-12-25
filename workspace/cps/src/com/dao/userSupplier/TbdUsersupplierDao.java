package com.dao.userSupplier;

import com.dao.IBaseDao;
import com.model.userCustomer.UserCustomer;
import com.model.userSupplier.UserSupplier;

public interface TbdUsersupplierDao extends IBaseDao<UserSupplier, java.lang.String>{

	UserSupplier getByUserId(String userId);
	

}
