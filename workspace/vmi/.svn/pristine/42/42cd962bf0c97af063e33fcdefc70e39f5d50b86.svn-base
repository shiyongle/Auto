package Com.Dao.order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import Com.Base.Dao.IBaseDao;
import Com.Entity.order.Deliverapply;
import Com.Entity.order.Mystock;
import Com.Entity.order.Saleorder;
import Com.Entity.order.SchemeDesignEntry;

public interface IMystockDao extends IBaseDao {

	void saveMyStockInBulk(List<Mystock> myStocks, String userid);
	Mystock Query(String fid);
	public HashMap<String, Object> ExecMyStockorder(ArrayList<Saleorder> solist,
			String ordersql, String deliverid) throws Exception;
	public void ExecSave(List<Deliverapply> list,List<SchemeDesignEntry> entryList);
}

