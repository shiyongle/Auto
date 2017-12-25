package Com.Dao.traffic;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.traffic.Saledeliver;
import Com.Entity.traffic.Saledeliverentry;

public interface ISaledeliverDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Saledeliver Sdeliver);
	  public Saledeliver Query(String fid);
	  public HashMap<String, Object> ExecSaledeliver(HashMap<String, Object> params,int type) throws Exception ;
	  public void UpdateSaledeliverentry(Saledeliverentry entry);
}
