/*
 * CPS-VMI-wangc
 */

package com.service.productdef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;
import com.service.IBaseManager;

public interface ProductdefManager extends IBaseManager<Productdef, java.lang.String>{
	public PageModel<Productdef> findSupplierCardboardList(int pageNo, int pageSize,String where, Object[] queryParams);
	public PageModel<Productdef> findCommonMaterialList(int pageNo, int pageSize,String where, Object[] queryParams);
	public MaterialLimit getMaterialLmit(String fcustomerid);
	public List<CustBoardFormula> getCustBoardFormula(String fcustomerid);
	
	public PageModel<Supplier> getSupplierListByPage(int pageNo, int pageSize,String where, Object[] queryParams);
	
	public PageModel<HashMap<String, Object>> findProductlist(String where,
			Object[] queryParams, Map<String, String> orderby, int pageNo,
			int maxResult);
	
	public int execforbiddenproduct(String fidcls);
	public String saveProduct(Productdef productdef);
	public HashMap<String, Object> getProductInfo(String id);
	public String getCurrentSupplierid(String fuserid);
}
