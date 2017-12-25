/*
 * CPS-VMI-wangc
 */

package com.service.schemedesign;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.schemedesign.Productstructure;
import com.model.schemedesign.Schemedesign;
import com.service.IBaseManager;

public interface SchemedesignManager extends IBaseManager<Schemedesign, java.lang.String>{
	public PageModel<Schemedesign> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);


	List<HashMap<String, HashMap<String, Object>>> getDetailWithfp(String id,String queryHistory);

//需求包详情
	public List<HashMap<String, HashMap<String, Object>>> getDetailWithScheme(String id,String queryHistory);
	/*********************************************************我的设计********************************************/
	public int updateDesigner(String fids,String designer);
	public List getDesinerList(String fsupplierid);
	public PageModel<HashMap<String, Object>> findByFirstdemand(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	public PageModel<HashMap<String, Object>> findBySchemedesign(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,boolean...t_name);
	public String schemeSave(HashMap params);
	public HashMap getScinfo(String fid);
	public String affirmSchemeDesign(String userid,String fid) throws Exception;
	public void execUnReceiveProductdemand(String fids) throws Exception;
	public void execReceiveProductdemand(String fids, String userId) throws Exception;
	public void delSchemeDesigns(String[] fid)throws Exception;
	public void delSchemeDesign(String fid) throws Exception;
	
	public void saveProductStructs(Firstproductdemand demand,List<Productstructure> productlist);
	
	public List<HashMap<String,Object>> getFirstdemandproduct(String fid);//根据需求ID 获取产品与附件
	public List<HashMap<String, Object>> getfileInfo(String fid);
	public List<HashMap<String, Object>> getSchemeEntrys(String fparentid);//根据方案ID获取分录
	
	public void auditSchemedeginPackge(HashMap map)throws  Exception;//审核方案（特性产品）
	public void saveGdSjcjnfo(List<Schemedesign> schemes)throws  Exception;;
	public String saveXqbjnfo(List<HashMap> list)throws  Exception;
	
}
