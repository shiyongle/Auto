package com.service.deliverapply;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model.PageModel;
import com.model.custproduct.TBdCustproduct;
import com.model.deliverapply.Deliverapply;
import com.model.productdef.MaterialLimit;
import com.service.IBaseManager;

public interface DeliverapplyManager extends IBaseManager<Deliverapply, java.lang.Integer> {
	
	public List<Deliverapply> ExecBySql( String where, Object[] queryParams,  Map<String, String> orderby);
	
	public PageModel<Deliverapply> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	
	/*** 下单*/
	public void saveImpl(Deliverapply deliverapply,String currentUserId,String currentCustomerId) throws ParseException;
	
	public List<Deliverapply> getDeliverapplyInfo( String where, Object[] queryParams,  Map<String, String> orderby,String...t_name);

	public void saveOrderOnlineByDeliverapply(Deliverapply apply,TBdCustproduct custproduct);
	
	//纸板查询列表分页
	public PageModel<Deliverapply> findBoardBySql(String where,Object[] queryParams, Map<String, String> orderby, int pageNo,int maxResult,String...t_name);

	public List<Deliverapply> getDeliverapplyboards( String where, Object[] queryParams,  Map<String, String> orderby,String...t_name);

	
	public void deleteBoardsImpl(String fidcls);
	
	public void deleteboxImpl(String fidcls) throws Exception;

	public void ExecUpdateBoardStateToCreate(String fidcls) throws Exception;

	public void saveBoardSignleDeliverapply(Deliverapply deliverapply) throws Exception;
	
	public String getfirstDateofMaterial(String fmaterialfid,String fsupplierid) throws Exception;
	
	public int deleteCustomerlabel(String customer,String fname);
	public int updateCustomerDescription(String customerid,String description);
	
	public void saveMaterialLimit(MaterialLimit m);
	
	public HashMap<String, Object> getDeliverapplyById(String id,String...t_name);
}
