package Com.Dao.order;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;
import Com.Base.Util.mySimpleUtil.excelpaster.IBatchSaveObjs;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Delivers;
import Com.Entity.order.SchemeDesignEntry;

public interface IDeliverapplyDao extends IBaseDao{
	public HashMap<String, Object> ExecSave(Deliverapply vmi);

	public void ExecSave(List<Deliverapply> vmi);

	public Deliverapply Query(String fid);

	// public void ExecCanceltoCreate(List<Delivers>
	// Deliverscls,List<Deliverapply> Deliverapplycls);
	public void ExecCanceltoCreate(List<Delivers> Deliverscls, String apppid);

	/**
	 * 根据Deliverapply ID修改要货申请状态
	 *
	 * @param id
	 * @param state
	 *
	 * @date 2014-3-28 下午4:02:31  (ZJZ)
	 */
	void updateDeliverapplyStateByDeliverapply(String id, int state);

	/**
	 * 类似上面，但用的是Delivers ID
	 *
	 * @param deliversId
	 * @param state
	 *
	 * @date 2014-3-28 下午4:03:03  (ZJZ)
	 */
	void updateDeliverapplyStateByDelivers(String deliversId, int state);

	public void ExecSave(List<Deliverapply> list,
			List<SchemeDesignEntry> entryList);

	public void ExecCreateToDelivers(
			List<HashMap<String, Object>> deliverresult, String userid,
			String fcustomerid) throws Exception;

	public void DelDeliverapply(String fidcls);

	public void ExecCreatedelivers(String fproductid,
			List<HashMap<String, Object>> result, String userid, int famount) throws Exception;

	public void addStateInfo(List<HashMap<String, Object>> data);

	void updateDeliverapplyStateByDeliverapply(String id, int state,
			boolean bool);

	void updateDeliverapplyStateByDelivers(String deliversId, int state,
			boolean bool);


	void updateDeliverapplyState(String applyId, int state, boolean bool);

	public void saveBoardDeliverapply(Deliverapply deliverapply);

	public String ExcelSql(HttpServletRequest request);

	ListResult buildExcelListResult(String selectSql,
			HttpServletRequest request, IBaseDao dao);

	public int ExecUpdateBoardStateToCreate(String fidcls) throws Exception;

	public int ExecDelCustomerLabelById(String customerid, String labelName);

	public  void ExecAutoCreateDeliverapply();
	public  void ExecDeleteCreateDeliverapply(HashMap<String, String> map)throws Exception;

}
