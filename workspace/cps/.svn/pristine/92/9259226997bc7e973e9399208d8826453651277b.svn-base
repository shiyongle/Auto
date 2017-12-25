/*
 * 网蓝
 */

package com.service.productplan;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.productplan.OrderInOutBean;
import com.model.productplan.ProductPlan;
import com.model.user.TSysUser;
import com.service.IBaseManager;



public interface ProductplanManager extends IBaseManager<ProductPlan, java.lang.String>{
	
	public PageModel<HashMap<String,Object>> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);

	public List<ProductPlan> getProductplanList(String where, Object[] queryParams,Map<String, String> orderby,boolean...history);
	
	void updateReceiveState(String fid,String userid);
	void updateUnReceiveState(String fid,TSysUser user) throws Exception;

	public String orderInOut(ProductPlan productplan, OrderInOutBean orderInOutBean);

	public HashMap<String, Object> getProductplanById(String id,boolean...t_name);

}
