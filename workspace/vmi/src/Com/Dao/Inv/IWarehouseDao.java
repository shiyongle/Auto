package Com.Dao.Inv;

import java.util.HashMap;

import Com.Base.Dao.IBaseDao;
import Com.Entity.Inv.Warehouse;

public interface IWarehouseDao extends IBaseDao {
	  public HashMap<String,Object> ExecSave(Warehouse cyc);
		public Warehouse Query(String fid);
}
