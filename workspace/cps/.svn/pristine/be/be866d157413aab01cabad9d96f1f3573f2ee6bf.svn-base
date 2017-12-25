package com.dao.mystock;

import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.mystock.Mystock;

public interface MystockDao extends IBaseDao<Mystock, java.lang.Integer> {
	
	public List<Mystock> execlBySql(String where, Object[] queryParams,Map<String, String> orderby);
	
	public PageModel<Mystock> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
}
