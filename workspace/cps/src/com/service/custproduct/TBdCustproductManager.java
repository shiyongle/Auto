package com.service.custproduct;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.supplier.Supplier;
import com.service.IBaseManager;

public interface TBdCustproductManager extends IBaseManager<TBdCustproduct, java.lang.String> {
	
	public PageModel<TBdCustproduct> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult);
	/*** 删除*/
	public void deleteImpl(String fid);
	/*** 通过用户id,客户产品id返回客户产品对象*/
	public List<TBdCustproduct> getById(String userId,String cusproductId);
	/*** 获取供应商*/
	public List<Supplier> getBySupplier(String userId);
	/** *根据需求ID查询所有客户产品列表及附件 */
	public List<HashMap<String, HashMap<String, Object>>> getProductWithfp(String id);
	public List<HashMap<String, Object>> getCustproductStock(String fid,String supplierid);
}
