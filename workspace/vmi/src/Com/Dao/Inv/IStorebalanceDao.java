package Com.Dao.Inv;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Storebalance;

public interface IStorebalanceDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Storebalance cyc);
	  public Storebalance Query(String fid);
	  public void ExecInStore(String fproductplanid,int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String forderid,String forderEntryid, String supplierid,String ftraitid);
	  public void ExecOutStore(String fproductplanid,int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid,String forderid,String forderEntryid,String ftraitid);
	  public void ExecOutStoreTZ(int amt,String FWarehouseID,String FWarehouseSiteID,String userid,String fproductid, String supplierid);
}
