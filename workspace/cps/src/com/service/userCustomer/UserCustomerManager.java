package com.service.userCustomer;

import com.model.userCustomer.UserCustomer;
import com.service.IBaseManager;

public interface UserCustomerManager extends IBaseManager<UserCustomer, java.lang.Integer> {
	
	/*** 通过用户流水号获取用户客户关联对象*/
	public  UserCustomer getByUserId(String userId);
}
