package Com.Dao.Finance;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Finance.Balancepriceprices;

public interface IBalancepricepricesDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Balancepriceprices cust);
	  public Balancepriceprices Query(String fid);
}
