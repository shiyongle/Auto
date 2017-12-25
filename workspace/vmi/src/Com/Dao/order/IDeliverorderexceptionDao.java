package Com.Dao.order;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.ListResult;
import Com.Entity.order.Deliverorderexception;

public interface IDeliverorderexceptionDao extends IBaseDao {

	public HashMap<String, Object> ExecSave(
			Deliverorderexception deliverorderexception);

	public Deliverorderexception Query(String fid);

	/**
	 * 获取所有
	 * 
	 * @return
	 * 
	 * @date 2014-3-18 下午3:11:42 (ZJZ)
	 */
	ListResult selectDeliverorderexceptionList(
			HttpServletRequest request);

	/**
	 * 
	 * 
	 * @return
	 * 
	 * @date 2014-3-18 下午3:12:00 (ZJZ)
	 */
	ListResult selectDeliverorderexceptionListByFid(
			HttpServletRequest request);

}
