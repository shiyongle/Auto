package com.dao.schemedesign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.schemedesign.Productstructure;
import com.model.schemedesign.Schemedesign;
import com.model.schemedesign.SchemeDesignEntry;

/*
 * CPS-VMI-wangc
 */
public interface SchemedesignDao extends IBaseDao<Schemedesign, java.lang.String>{
	
	public PageModel<Schemedesign> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	public int updateDesigner(String fids,String designerid);
	public PageModel<HashMap<String,Object>> findByFirstdemand(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	public PageModel<HashMap<String,Object>> findBySchemedesign(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,boolean...t_name);
	public String schemeSave(Schemedesign scinfo,List<SchemeDesignEntry> scentryList,List<Productstructure> pdtlist);
	public String affirmSchemeDesignByid(String userid,String fid);
}
