package Com.Dao.Inv;

import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;

public interface IProductInvDao extends IBaseDao {


	/**
	 * 
	 * 
	 * @param productId
	 * @param user
	 *            ，用户ID
	 * @param theSetNumber
	 *            ,调整值，可以为负数
	 * @param fwarehouseId
	 * @param fwarehouseSiteId
	 * 
	 * @date 2014-2-20 下午5:00:15 (ZJZ)
	 */
	void updateWarehouseInOut(List<HashMap<String, Object>> products, String user, int theSetNumber,
			String fwarehouseId, String fwarehouseSiteId, String fsupplerID);

	/**
	 * 
	 * 
	 * @param fproductID
	 * @param theSetNumber
	 *            ,同上
	 * @param fwarehouseId
	 * @param fwarehouseSiteId
	 * @param fsupplerID
	 * @param user
	 * 
	 * @date 2014-2-20 下午5:00:20 (ZJZ)
	 */
	void updateOrSaveStorebalance(List<HashMap<String, Object>> products, int theSetNumber,
			String fwarehouseId, String fwarehouseSiteId, String fsupplerID,
			String user);


	/**
	 * 
	 *门面，参数参照上面
	 * @param productId
	 * @param user
	 * @param theSetNumber
	 * @param fwarehouseId
	 * @param fwarehouseSiteId
	 * @param fsupplerID
	 * @param producttype 产品类型
	 * @date 2014-2-21 下午3:21:07  (ZJZ)
	 */
	void updateWarehouseInOutAndStorebalance(List<HashMap<String, Object>> products, String user,
			int theSetNumber, String fwarehouseId, String fwarehouseSiteId,
			String fsupplerID);
}
