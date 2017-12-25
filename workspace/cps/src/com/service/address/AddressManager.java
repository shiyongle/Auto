package com.service.address;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.address.Address;
import com.service.IBaseManager;

public interface AddressManager extends IBaseManager<Address, java.lang.Integer> {
	
	/***根据客户流水号,获取收货地址*/
	public List<Address> getByCustomerId(String customerId);
	public List<Address> getByCustomerId(String customerId,String userid);
	
	public PageModel<Address> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public List<Address> getById(String where, Object[] queryParams);
	
	public void isEnabledImpl(List<Address> ls, String isenable);
	
	public void saveImpl(Address address);
	
	public void deleteImpl(String[] ids);
	
	public void isDefaultImpl(List<Address> list,String userId);
	
	public void setDefaultImpl(String adid,String userId);
}
