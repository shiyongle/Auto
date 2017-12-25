/*
 * CPS-VMI-wangc
 */

package com.service.firstproductdemand;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.firstproductdemand.Firstproductdemand;
import com.model.productdemandfile.Productdemandfile;
import com.service.IBaseManager;

public interface TordFirstproductdemandManager extends IBaseManager<Firstproductdemand, java.lang.String>{
	
	public PageModel<Firstproductdemand> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	
	public List<Firstproductdemand> execlBySql(String where, Object[] queryParams,Map<String, String> orderby,String...t_name);
	
	public void deleteImpl(String[] ids);

	public void UnAffirmSchemedesign(String param) throws Exception;

	public void closeFp(String param);

	public String finishFp(String[] param);

	
	public List<Productdemandfile> getFilesbyParentid(String pid);

	public void AffirmSchemedesign(String ffirstproductid, String ids,
			String userid);

	public void affirmPay(String id);
}
