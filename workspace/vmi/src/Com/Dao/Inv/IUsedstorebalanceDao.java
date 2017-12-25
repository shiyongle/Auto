package Com.Dao.Inv;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Usedstorebalance;

public interface IUsedstorebalanceDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Usedstorebalance cyc);
	  public Usedstorebalance Query(String fid);
}
