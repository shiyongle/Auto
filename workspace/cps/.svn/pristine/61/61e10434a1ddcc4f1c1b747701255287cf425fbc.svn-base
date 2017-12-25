/*
 * CPS-VMI-wangc
 */

package com.service.cusprivatedelivers;

import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.cusprivatedelivers.CusPrivateDelivers;
import com.model.custproduct.TBdCustproduct;
import com.service.IBaseManager;

public interface CusprivatedeliversManager extends IBaseManager<CusPrivateDelivers, java.lang.String>{
	
	public PageModel<CusPrivateDelivers> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	public void saveCusprivatedelivers(String fids,CusPrivateDelivers cusPrivateDelivers,String userid,String fcustomerid);
	public void deleteImpl(String[] fid);
	public void updateImpl(List<CusPrivateDelivers> list)throws Exception;
	public String createDeliverapply(String[] fid,String userid);
}
