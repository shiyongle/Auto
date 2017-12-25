package com.dao.custproduct;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.supplier.Supplier;

public interface TBdCustproductDao extends IBaseDao<TBdCustproduct, java.lang.String> {
    
	public PageModel<TBdCustproduct> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	
	/*** 通过用户id,客户产品id返回客户产品对象*/
	public List<TBdCustproduct> getById(String userId,String cusproductId);
	
	public List<Supplier> getBySupplier(String userId);
	public List<HashMap<String,Object>> getCustproductStock(String fid,String supplierid);
}
