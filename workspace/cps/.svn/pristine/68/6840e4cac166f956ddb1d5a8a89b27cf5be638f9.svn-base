package com.service.mystock;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.mystock.Mystock;
import com.service.IBaseManager;

public interface MystockManager extends IBaseManager<Mystock, java.lang.Integer> {
	
	public List<Mystock> execlBySql(String where, Object[] queryParams,Map<String, String> orderby);
	
	public PageModel<Mystock> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);

}
