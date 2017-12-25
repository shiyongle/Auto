package Com.Dao.order;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Storebalance;
import Com.Entity.Inv.Usedstorebalance;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Deliverorder;
import Com.Entity.order.ProductPlan;

public interface IDeliverorderDao extends IBaseDao {
	public HashMap<String,Object> ExecSave(Deliverorder vmi);
	public Deliverorder Query(String fid);
	public void ExecDeliverorder(ArrayList<HashMap<String, String>> deliverorderlist, Connection conn, PreparedStatement stmt) throws Exception;
	public HashMap<String, Object> ExecCreateTruckassemble(HashMap<String, Object> params,int type) throws Exception ;
	public void ExecSave(List<Deliverorder> vmi);
	public void ExecUpdateSQL(String updatesql);
	public void ExecUpdateAllot(Deliverorder deliverorder, Storebalance storebalance);
	
	
	public HashMap<String, Object> ExecCreateTruckassemble(HashMap<String, Object> params,HashMap<String, Object> enrtyparams) throws Exception ;
	public HashMap<String, Object> ExecCreateTruckassembleSDK(HashMap<String, Object> params, HashMap<String, Object> enrtyparams) throws Exception;
	String ExecCreatTruckassembleByCondition(HttpServletRequest request,
			HttpServletResponse reponse) throws IOException, Exception;
	
	
	/**
	 * 纸板用
	 *
	 * @param fidsA
	 * @param parseInt
	 * @param userid
	 * @param fidsA2 
	 *
	 * @date 2015-2-4 上午10:12:42  
	 */
	public void ExecUpdateState(List<String> fidsA, int parseInt, String userid, List<String> fidsA2);
	
	/**
	 * 纸板下单
	 *
	 * @param deliverapplys
	 * @param pps
	 * @param deliverOrders
	 * @param usedstorebalances 
	 * @param storebalances 
	 *
	 * @date 2015-2-5 上午8:31:19  (ZJZ)
	 */
	public void ExecDoPlaceCarboardOrder(List<Deliverapply> deliverapplys,
			List<ProductPlan> pps, List<Deliverorder> deliverOrders, List<Storebalance> storebalances, List<Usedstorebalance> usedstorebalances);
	/**
	 *  配送信息关闭
	 * @param fidcls 配送id
	 * @throws Exception
	 */
	public void ExecCloseDeliverorder(String fidcls)throws  Exception;
	public void ExecReceiveBoardOrders(String fidcls, String userid);
	public void ExecUnReceiveBoardOrders(String fidcls);
	void ExecReceiveBoardOrders();
	String ExecBatchCreatTruckassembleByCondition(HttpServletRequest request,
			HttpServletResponse reponse) throws Exception;
	int getSaleDeliverEntrySeq(String saledeliverID);
	
	public HashMap<String, Object> ExecCreateTruckassembleNew(HashMap<String, Object> params) throws Exception ;
	void ExecAuditImportEAS(String fid,String fuserid);
}
