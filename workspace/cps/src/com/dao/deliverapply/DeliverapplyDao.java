package com.dao.deliverapply;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.dao.IBaseDao;
import com.model.PageModel;
import com.model.deliverapply.Deliverapply;
import com.model.produceplan.ProducePlan;
import com.model.productdef.Productdef;

public interface DeliverapplyDao extends IBaseDao<Deliverapply, java.lang.Integer> {

	public List<Deliverapply> ExecBySql( String where, Object[] queryParams,  Map<String, String> orderby);
	
	public PageModel<Deliverapply> findBySql(String where, Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);
	
	public List<Deliverapply> getDeliverapplyInfo( String where, Object[] queryParams,  Map<String, String> orderby,String...t_name);

	public PageModel<Deliverapply> findBoardBySql(String where,Object[] queryParams,Map<String, String> orderby, int pageNo, int maxResult,String...t_name);

	
	public List<Deliverapply> getDeliverapplyBoards( String where, Object[] queryParams,  Map<String, String> orderby,String...t_name);

	public void ExecUpdateBoardStateToCreate(String fidcls) throws Exception;

	public void saveBoardSignleDeliverapply(Deliverapply deliverapply) throws Exception;
	
	public  Calendar getFirstArrivetimeDate(ProducePlan planInfo,Productdef definfo)throws Exception;

	public void saveCustBoardFormula(Deliverapply deliverapply);

	public void saveCustBoardLabel(Deliverapply deliverapply);


}
