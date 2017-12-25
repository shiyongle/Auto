package Com.Dao.Logistics;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import Com.Base.Dao.IBaseDao;
import Com.Entity.Logistics.LogisticsAddress;
import Com.Entity.Logistics.LogisticsCarinfo;
import Com.Entity.System.Address;

public interface ILogisticsCarDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(LogisticsCarinfo add);
	  public LogisticsCarinfo Query(String fid);
}
