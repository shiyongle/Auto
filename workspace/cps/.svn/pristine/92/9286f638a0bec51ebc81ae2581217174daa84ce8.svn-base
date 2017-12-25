package com.dao.userSupplier;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.userSupplier.UserSupplier;


@Repository("tbdUsersupplierDao")
public class TbdUsersupplierDaoImpl extends IBaseDaoImpl<UserSupplier,java.lang.String> implements TbdUsersupplierDao{
	/*** 通过用户流水号获取用户制造商关联对象*/
	@Override
	public UserSupplier getByUserId(String userId) {
		String hql = "from UserSupplier where fuserid=? ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, userId);  
	    @SuppressWarnings("unchecked")
	    List<UserSupplier> list = query.list(); 
	    if(list.size() == 1){
			return list.get(0);
		}
	    return null;  
	}

}
