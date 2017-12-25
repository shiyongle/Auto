package Com.Dao.Finance;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Finance.Balancepricecustprices;

public interface IBalancepricecustpricesDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Balancepricecustprices cust);
	  public Balancepricecustprices Query(String fid);
}
