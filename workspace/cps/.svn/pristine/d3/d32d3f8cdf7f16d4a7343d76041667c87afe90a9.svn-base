package com.service.userSupplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.userSupplier.TbdUsersupplierDao;
import com.model.userSupplier.UserSupplier;
import com.service.IBaseManagerImpl;

@Service("tbdUsersupplierManager")
@Transactional
public class TbdUsersupplierManagerImpl extends IBaseManagerImpl<UserSupplier,java.lang.String> implements TbdUsersupplierManager{
	@Autowired
	private TbdUsersupplierDao tbdUsersupplierDao;

	public IBaseDao<UserSupplier, java.lang.String> getEntityDao() {
		return this.tbdUsersupplierDao;
	}
	@Override
	public UserSupplier getByUserId(String userId){
		return tbdUsersupplierDao.getByUserId(userId);
	}
	@Override
	public String getSupplierId(String userId){
		UserSupplier userSupplier = getByUserId(userId);
		if(userSupplier != null){
			return userSupplier.getFsupplierid();
		}else{
			return null;
		}
	}
}
