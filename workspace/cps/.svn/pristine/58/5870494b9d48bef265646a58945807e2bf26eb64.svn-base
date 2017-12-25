package com.service.customer;

import java.util.HashMap;
import java.util.Map;

import com.model.PageModel;
import com.model.customer.Customer;
import com.service.IBaseManager;

public interface CustomerManager extends IBaseManager<Customer, java.lang.Integer> {
	
	public String getCustomerIdByUserid(String fid);
	public  HashMap<String,String> getSupplierIdByUserid(String fid);
	public PageModel<HashMap<String, Object>> findBySql(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult);
	void delCustomer(String customerid, String userid, String fsupplierid)
			throws Exception;
}
