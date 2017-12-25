package com.dao.address;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.address.Address;

public interface AddressDao extends IBaseDao<Address, java.lang.Integer> {
	
	/***根据客户流水号,获取收货地址*/
	public List<Address> getByCustomerId(String customerId,String userid);
	public List<Address> getByCustomerId(String customerId);
	
	public PageModel<Address> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public List<Address> getById(String where, Object[] queryParams);
}
