package Com.Dao.order;

import java.util.HashMap;
import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Orderappraise;

public interface IOrderappraiseDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Orderappraise vmi);
	  public Orderappraise Query(String fid);
	  public void  Execautoorderapparises();

}
