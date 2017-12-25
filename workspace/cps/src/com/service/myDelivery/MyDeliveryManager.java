package com.service.myDelivery;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.myDelivery.MyDelivery;
import com.service.IBaseManager;

public interface MyDeliveryManager extends IBaseManager<MyDelivery, java.lang.Integer> {
	
	public PageModel<MyDelivery> findBySql(String where1,String where2, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public PageModel<MyDelivery> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public List<MyDelivery> getLine(String where,Object[] queryParams,Map<String, String> orderby );
	
	public List<MyDelivery> execlBySql(String where1,String where2, Object[] queryParams,Map<String, String> orderby);
}
