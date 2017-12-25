package com.dao.userCustomer;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.userCustomer.UserCustomer;
@Repository("userCustomerDao")
public class UserCustomerDaoImpl extends IBaseDaoImpl<UserCustomer, java.lang.Integer> implements UserCustomerDao {
	
	/*** 通过用户流水号获取用户客户关联对象*/
	@Override
	public UserCustomer getByUserId(String userId) {
		String hql = "from UserCustomer where fuserid=? ";
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, userId);  
	    @SuppressWarnings("unchecked")
	    List<UserCustomer> list = query.list(); 
	    if(list != null && list.size() > 0){
			return list.get(0);
		}
	    return null;  
	}


}
