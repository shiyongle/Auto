package com.dao.firstproductdemand;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.firstproductdemand.Firstproductdemand;

/*
 * CPS-VMI-wangc
 */
public interface TordFirstproductdemandDao extends IBaseDao<Firstproductdemand, java.lang.String>{
	
	public PageModel<Firstproductdemand> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	
	public List<Firstproductdemand> execlBySql(String where, Object[] queryParams,Map<String, String> orderby,String...t_name);
}
