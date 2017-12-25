package com.service.customer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IBaseDao;
import com.dao.customer.CustomerDao;
import com.model.PageModel;
import com.model.customer.Customer;
import com.service.IBaseManagerImpl;
@Service("customerManager")
@Transactional(rollbackFor = Exception.class)
public class CustomerManagerImpl extends IBaseManagerImpl<Customer, java.lang.Integer> implements CustomerManager {

	@Autowired
	private CustomerDao customerDao;
	@Override
	protected IBaseDao<Customer, Integer> getEntityDao() {
		return this.customerDao;
	}
	@Override
	public String getCustomerIdByUserid(String fid){
		return customerDao.getCustomerByUserid(fid);
	}
	@Override
	public PageModel<HashMap<String, Object>> findBySql(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult) {
		// TODO Auto-generated method stub
		return customerDao.findBySql(where, queryParams, orderby, pageNo, maxResult);
	}
	@Override
	public void delCustomer(String customerid,String userid,String fsupplierid) throws Exception{
		 customerDao.delCustomer(customerid, userid, fsupplierid);
	}
	@Override
	public  HashMap<String,String> getSupplierIdByUserid(String fid) {
		// TODO Auto-generated method stub
		return customerDao.getSupplierByUserid(fid);
	}
}
