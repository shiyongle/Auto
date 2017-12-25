package com.dao.customer;

import java.util.HashMap;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.customer.Customer;

public interface CustomerDao extends IBaseDao<Customer, java.lang.Integer> {
	public String getCustomerByUserid(String fid);
	public HashMap<String,String> getSupplierByUserid(String fid);
	public PageModel<HashMap<String, Object>> findBySql(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult);
	void delCustomer(String customerid, String userid, String fsupplierid) throws Exception;
}
