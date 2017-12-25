package com.service.userCustomer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userCustomer.UserCustomerDao;
import com.model.userCustomer.UserCustomer;
import com.service.IBaseManagerImpl;
@Service("userCustomerManager")
@Transactional
public class UserCustomerManagerImpl extends IBaseManagerImpl<UserCustomer, java.lang.Integer> implements UserCustomerManager {
	
	@Autowired
	private UserCustomerDao userCustomerDao;
	@Override
	protected IBaseDao<UserCustomer, Integer> getEntityDao() {
		return this.userCustomerDao;
	}
	@Override
	public UserCustomer getByUserId(String userId) {
		return this.userCustomerDao.getByUserId(userId);
	}

	

}
