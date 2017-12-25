package com.dao.orderState;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.dao.IBaseDaoImpl;
import com.model.PageModel;
import com.model.mystock.Mystock;
import com.model.orderState.OrderState;
import com.model.productdemandfile.Productdemandfile;
@Repository("orderstateDao")
public class OrderStateDaoImpl extends IBaseDaoImpl<OrderState, java.lang.Integer> implements OrderStateDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderState> getOrderStateInfo(String fid){
		String hql = " from OrderState where fdeliverapplyid= ? and fstate IN(3,4,5,6) order by fstate "; 
		Query query = this.getSessionFactory().getCurrentSession().createQuery(hql);  
	    query.setString(0, fid);  
	    List<OrderState> list1 = query.list(); 
		return list1;
	}
	
}
