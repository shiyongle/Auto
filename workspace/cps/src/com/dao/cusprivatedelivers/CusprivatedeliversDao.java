package com.dao.cusprivatedelivers;

import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.cusprivatedelivers.CusPrivateDelivers;
import com.model.deliverapply.Deliverapply;


/*
 * CPS-VMI-wangc
 */
public interface CusprivatedeliversDao extends IBaseDao<CusPrivateDelivers, java.lang.String>{
	
	public PageModel<CusPrivateDelivers> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);

}
