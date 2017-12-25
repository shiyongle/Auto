package com.dao.productdef;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.productdef.CustBoardFormula;
import com.model.productdef.MaterialLimit;
import com.model.productdef.Productdef;
import com.model.supplier.Supplier;

/*
 * CPS-VMI-wangc
 */
public interface ProductdefDao extends IBaseDao<Productdef, java.lang.String>{
	/*** 分页且带有查询条件的查询方法*/
	public PageModel<Productdef> findSupplierCardboardList(int pageNo, int pageSize,String where, Object[] queryParams);
	public PageModel<Productdef> findCommonMaterialList(int pageNo, int pageSize,String where, Object[] queryParams);
	
	public MaterialLimit getMaterilaLimitEntry(String fcustomerid);
	public List<CustBoardFormula> getCustBoardFormulaEntry(String fcustomerid);
	/**制造商带分页**/
	public PageModel<Supplier> getBySupplierWithPage(final int pageNo,final int pageSize, final String where, final Object[] queryParams);


	/******************产品档案列表界面*************************/
	public PageModel<HashMap<String, Object>> findProductlist(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult);
	public boolean saveListToTable(String fcustomerId,List<String> list,String userid);

}
