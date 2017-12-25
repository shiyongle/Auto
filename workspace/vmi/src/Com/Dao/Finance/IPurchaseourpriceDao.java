package Com.Dao.Finance;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Finance.Purchaseourprice;

public interface IPurchaseourpriceDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Purchaseourprice cust);
	  public Purchaseourprice Query(String fid);
}
