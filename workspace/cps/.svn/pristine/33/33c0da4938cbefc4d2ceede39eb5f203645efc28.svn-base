package com.service.designschemes;

import java.util.HashMap;
import java.util.Map;

import com.model.PageModel;
import com.model.designschemes.Designschemes;
import com.service.IBaseManager;

public interface DesignSchemesManager extends IBaseManager<Designschemes, java.lang.String> {
	public PageModel<Designschemes> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	public PageModel<Designschemes> findAllschemeSql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	public void deleteScheme(String id);
	public void updateshelves(String id);
	public void updateImgDesc(HashMap<String,String> imgmap);
}
