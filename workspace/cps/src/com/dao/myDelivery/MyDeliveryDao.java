package com.dao.myDelivery;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.myDelivery.MyDelivery;

public interface MyDeliveryDao extends IBaseDao<MyDelivery, java.lang.Integer> {
	
	public PageModel<MyDelivery> findBySql(String where1, String where2,Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public PageModel<MyDelivery> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	public List<MyDelivery> getLine(String where,Object[] queryParams,Map<String, String> orderby);
	
	public List<MyDelivery> execlBySql(String where1,String where2, Object[] queryParams,Map<String, String> orderby);
}
