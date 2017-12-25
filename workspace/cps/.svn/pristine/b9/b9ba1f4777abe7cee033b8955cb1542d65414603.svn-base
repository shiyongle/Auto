package com.dao.productplan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.mystock.Mystock;
import com.model.productplan.ProductPlan;
import com.model.saledeliver.FTUSaledeliver;



public interface ProductplanDao extends  IBaseDao<ProductPlan, java.lang.String>{
	public PageModel<HashMap<String,Object>> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	public List<ProductPlan> getProductplanList( String where, Object[] queryParams,  Map<String, String> orderby,boolean...history);
	public void  updateReceiveState(String fid,String userid);
	public void updateUnreceiveState(HashMap<String, String> map);
}
