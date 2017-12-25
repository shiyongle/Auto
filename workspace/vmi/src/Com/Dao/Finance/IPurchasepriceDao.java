package Com.Dao.Finance;

import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Finance.Purchaseourprice;
import Com.Entity.Finance.Purchaseprice;
import Com.Entity.Finance.Purchasesupplierprice;

public interface IPurchasepriceDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Purchaseprice cust);
	  public void ExecSaveBalanceprice(Purchaseprice cust,List<Purchaseourprice> prices,List<Purchasesupplierprice> custprices);
	  public Purchaseprice Query(String fid);
}
