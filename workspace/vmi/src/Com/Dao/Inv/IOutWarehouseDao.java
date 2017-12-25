package Com.Dao.Inv;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import Com.Base.Dao.IBaseDao;
import Com.Base.Util.DJException;
import Com.Entity.Inv.Outwarehouse;


public interface IOutWarehouseDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Outwarehouse cyc);
		public Outwarehouse Query(String fid);
		  public void saveOutWarehouse(HttpServletRequest request) throws DJException, Exception;
		  public void saveOutWarehouse(String fproductplanid,int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String forderid,String forderEntryid,String fsupplierid,String ftraitid,boolean audited);
		  public void saveOutWarehouse(String fproductplanid,int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String ftraitid,boolean audited);
		  public void saveOutWarehouseTZ(int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String fsupplierid,boolean audited);
}
