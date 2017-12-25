package Com.Dao.Logistics;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import Com.Base.Dao.IBaseDao;
import Com.Entity.Logistics.LogisticsAddress;
import Com.Entity.System.Address;

public interface ILogisticsAddressDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(LogisticsAddress add);
	  public LogisticsAddress Query(String fid);
}
