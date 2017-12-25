package Com.Dao.Finance;

import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Finance.Balanceprice;
import Com.Entity.Finance.Balancepricecustprices;
import Com.Entity.Finance.Balancepriceprices;

public interface IBalancepriceDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Balanceprice cust);
	  public void ExecSaveBalanceprice(Balanceprice cust,List<Balancepriceprices> prices,List<Balancepricecustprices> custprices);
	  public Balanceprice Query(String fid);
}
